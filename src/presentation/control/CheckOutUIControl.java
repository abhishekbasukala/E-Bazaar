package presentation.control;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import presentation.data.AddressPres;
import presentation.data.BrowseSelectData;
import presentation.data.CartItemPres;
import presentation.data.CheckoutData;
import presentation.data.CreditCardPres;
import presentation.data.CustomerPres;
import presentation.gui.GuiConstants;
import business.exceptions.BackendException;
import business.exceptions.BusinessException;
import business.exceptions.RuleException;
import business.externalinterfaces.Address;
import business.externalinterfaces.CreditCard;
import business.shoppingcartsubsystem.ShoppingCartSubsystemFacade;
import business.usecasecontrol.CheckoutController;
import business.util.DataUtil;

@Controller
@RequestMapping("/customer")
public class CheckOutUIControl {
	
	@Autowired
	CheckoutController checkoutController;
	
	@Autowired
	CheckoutData checkoutData;
	
	@Autowired
	BrowseSelectData browseSelectData;
	@RequestMapping(value = "/shippingbilling", method = RequestMethod.GET)
	public String getShippingAndBillingAddress(ModelMap modelMap) {
		List<CartItemPres> cartItems =browseSelectData.getCartData2();
		try {
			checkoutController.runShoppingCartRules(cartItems);

			CheckoutData data = checkoutData;
			data.loadBillAddresses();
			data.loadShipAddresses();
			data.loadDefaultBillAddress();
			data.loadDefaultPaymentInfo();
			data.loadDefaultShipAddress();
			Address defaultShipAddress = data.getDefaultShippingData();
			AddressPres shipAddress = new AddressPres();
			AddressPres billAddress = new AddressPres();
			Address defaultBillAddress = data.getDefaultBillingData();
			billAddress.setAddress(defaultBillAddress);
			shipAddress.setAddress(defaultShipAddress);
			modelMap.addAttribute("shippingAddress", shipAddress);
			modelMap.addAttribute("billingAddress", billAddress);
		} catch (RuleException e) {
			modelMap.addAttribute("cartItems", cartItems);
			modelMap.addAttribute("message", e.getMessage());
			return "cart";
		} catch (BusinessException e) {
			modelMap.addAttribute("cartItems", cartItems);
			modelMap.addAttribute("message", e.getMessage());
			return "cart";
		}

		return "shippingandbilling";

	}

	@RequestMapping(value = "/selectbilladdress", method = RequestMethod.GET)
	public String selectbillAddress(ModelMap modelMap) {
		CheckoutData data = checkoutData;
		try {
			List<Address> addressSelected = new ArrayList<>();
			List<CustomerPres> addressList = data.getCustomerBillAddresses();
			for (CustomerPres customerPres : addressList) {
				addressSelected.add(customerPres.getAddress());
			}
			modelMap.addAttribute("addressList", addressSelected);
		} catch (BackendException e) {
			e.printStackTrace();
		}
		modelMap.addAttribute("title", "bill");
		return "selectaddress";
	}

	@RequestMapping(value = "/selectshipaddress", method = RequestMethod.GET)
	public String selectshipAddress(ModelMap modelMap) {
		CheckoutData data = checkoutData;
		try {
			List<Address> addressSelected = new ArrayList<>();
			List<CustomerPres> addressList = data.getCustomerShipAddresses();
			for (CustomerPres customerPres : addressList) {
				addressSelected.add(customerPres.getAddress());
			}
			modelMap.addAttribute("addressList", addressSelected);

		} catch (BackendException e) {
			e.printStackTrace();
		}
		// modelMap.addAttribute("addressList",DefaultData.ADDRESSES_ON_FILE);
		modelMap.addAttribute("title", "ship");
		return "selectaddress";
	}

	@RequestMapping(value = "/payment", method = RequestMethod.GET)
	public String proceedToPayment(ModelMap modelMap) {
		CheckoutData data = checkoutData;
		try {
			CreditCardPres cc = data.getDefaultPaymentInfo();
			modelMap.addAttribute("ccard", cc);
			return "payment";
		} catch (BackendException e) {
			modelMap.addAttribute("message", e.getMessage());
			return "payment";
		}
	}

	@RequestMapping(value = "/selectaddress/{title}/{id}", method = RequestMethod.GET)
	public String selectAddress(@PathVariable("title") String title, @PathVariable("id") String id, ModelMap modelMap) {
		CheckoutData data = checkoutData;

		Address selectedAddress;
		AddressPres shipAddress = new AddressPres();
		AddressPres billAddress = new AddressPres();
		try {
			selectedAddress = DataUtil.readCustFromCache().getAllAddresses().stream()
					.filter(a -> (a.getId()) == Integer.parseInt(id)).findFirst().get();
			if (title.matches("bill")) {

				Address defaultShipAddress = data.getDefaultShippingData();
				shipAddress.setAddress(defaultShipAddress);
				billAddress.setAddress(selectedAddress);

			} else {
				Address defaultBillAddress;
				defaultBillAddress = data.getDefaultBillingData();
				billAddress.setAddress(defaultBillAddress);
				shipAddress.setAddress(selectedAddress);

			}
		} catch (NumberFormatException e) {
			modelMap.addAttribute("message", e.getMessage());
		} catch (BackendException e) {
			modelMap.addAttribute("message", e.getMessage());

		}
		modelMap.addAttribute("addressFields", GuiConstants.DISPLAY_ADDRESS_FIELDS);
		modelMap.addAttribute("shippingAddress", shipAddress);
		modelMap.addAttribute("billingAddress", billAddress);
		return "shippingandbilling";
	}

	@RequestMapping(value = "/deleteAddress/{title}/{id}", method = RequestMethod.GET)
	public String deleteAddress(@PathVariable("title") String title, @PathVariable("id") String id, ModelMap modelMap) {
		CheckoutData data =checkoutData;
		try {
			Address selectedAddress = DataUtil.readCustFromCache().getAllAddresses().get(0);
			data.deleteCustomerAddressesAndUpdate(title, selectedAddress);
			data.loadBillAddresses();
			data.loadShipAddresses();
			data.loadDefaultBillAddress();
			data.loadDefaultPaymentInfo();
			data.loadDefaultShipAddress();
		} catch (BackendException e) {
			e.printStackTrace();
		}
		String url = "select" + title + "address";
		return "redirect:/customer/" + url;
	}

	/** Returns the user-filled Billing Address data */
	private Address createAddress(String street, String city, String state, String zip, boolean isShip) {
		return checkoutData.createAddress(street, city, state, zip, isShip, !isShip);
	}

	@RequestMapping(value = "/payment", method = RequestMethod.POST)
	public String verifyAddress(@RequestParam Map<String, String> allRequestParams, ModelMap modelMap, HttpSession session) {
		String shipstreet = allRequestParams.get("shipStreet");
		String shipState = allRequestParams.get("shipState");
		String shipCity = allRequestParams.get("shipCity");
		String shipZip = allRequestParams.get("shipZip");
		Address userShipAddress = createAddress(shipstreet, shipCity, shipState, shipZip, true);
		String billStreet = allRequestParams.get("billStreet");
		String billCity = allRequestParams.get("billCity");
		String billState = allRequestParams.get("billState");
		String billZip = allRequestParams.get("billZip");
		Address userBillingAddress = createAddress(billStreet, billCity, billState, billZip, false);
		session.setAttribute("userBillingAddress", userBillingAddress);
		String saveBill = null, saveShip = null;
		saveBill = allRequestParams.get("saveBillingAdd");
		saveShip = allRequestParams.get("saveShippingAdd");

		boolean checkRules = true;
		Address myShippingAddress = null;
		Address myBillingAddress = null;
		String message = "";

		try {
			myShippingAddress = checkoutController.runAddressRules(userShipAddress);
		} catch (RuleException e) {
			checkRules = false;
			message = e.getMessage();
		} catch (BusinessException e) {
			checkRules = false;
			message = e.getMessage();
		}

		if (checkRules) {
			try {
				myBillingAddress = checkoutController.runAddressRules(userBillingAddress);
			} catch (RuleException e) {
				checkRules = false;
				message = e.getMessage();
			} catch (BusinessException e) {
				checkRules = false;
				message = e.getMessage();
			}

		} else {
			modelMap.addAttribute("shippingAddress", userShipAddress);
			modelMap.addAttribute("billingAddress", userBillingAddress);
			modelMap.addAttribute("message", message);
			return "shippingandbilling";
		}
		if (checkRules) {
			if (myShippingAddress != null) {
				try {
					if (saveShip != null) {
						if (saveShip.matches("saveShippingAdd")) {
							checkoutController.saveNewAddress(myShippingAddress);
						}
					}

				} catch (BackendException e) {
					message = e.getMessage();
					modelMap.addAttribute("shippingAddress", userShipAddress);
					modelMap.addAttribute("billingAddress", userBillingAddress);
					modelMap.addAttribute("message", message);
					return "shippingandbilling";
				}
			} else {

				modelMap.addAttribute("shippingAddress", userShipAddress);
				modelMap.addAttribute("billingAddress", userBillingAddress);
				modelMap.addAttribute("message", message);
				return "shippingandbilling";

			}
			if (myBillingAddress != null) {
				try {
					if (saveBill != null) {
						if (saveBill.matches("saveBillingAdd")) {
							checkoutController.saveNewAddress(myBillingAddress);
						}
					}
				} catch (BackendException e) {
					message = e.getMessage();
					modelMap.addAttribute("shippingAddress", userShipAddress);
					modelMap.addAttribute("billingAddress", userBillingAddress);
					modelMap.addAttribute("message", message);
					return "shippingandbilling";
				}
			} else {
				modelMap.addAttribute("shippingAddress", userShipAddress);
				modelMap.addAttribute("billingAddress", userBillingAddress);
				modelMap.addAttribute("message", message);
				return "shippingandbilling";
			}

		} else {
			modelMap.addAttribute("shippingAddress", userShipAddress);
			modelMap.addAttribute("billingAddress", userBillingAddress);
			modelMap.addAttribute("message", message);
			return "shippingandbilling";
		}
		checkoutController.setShippingAddress(userShipAddress);
		checkoutController.setBillingAddress(userBillingAddress);
		return "redirect:/customer/payment";

	}

	@RequestMapping(value = "/terms", method = RequestMethod.POST)
	public String proceedToTermsAndCondition(@RequestParam Map<String, String> allRequestParams, ModelMap modelMap, HttpSession session) {
		String name = allRequestParams.get("name");
		String ccType = allRequestParams.get("ccType");
		String ccNumber = allRequestParams.get("ccNumber");
		String ccExpDate = allRequestParams.get("ccExpDate");
		CreditCard cc = checkoutData.createCreditCard(name, ccExpDate, ccNumber, ccType);
		boolean rulesok = true;
		String message = "";
		
		try {
			Address userBillingAddress = (Address) session.getAttribute("userBillingAddress");
			checkoutController.runPaymentRules(userBillingAddress, cc);
		} catch (RuleException e) {
			rulesok = false;
			message = e.getMessage();
		} catch (BusinessException e) {
			rulesok = false;
			message = e.getMessage();
		}
		if (rulesok) {
			checkoutController.setPaymentInfoInCart(cc);
			return "redirect:/customer/terms";
		} else {
			modelMap.addAttribute("message", message);
			CreditCardPres ccPress = new CreditCardPres(cc);
			modelMap.addAttribute("ccard", ccPress);
			return "payment";
		}

	}

	@RequestMapping(value = "terms", method = RequestMethod.GET)
	public String proceedToTermsAndConditionget(ModelMap modelMap) {
		modelMap.addAttribute("terms", GuiConstants.TERMS_MESSAGE);
		return "termsandcondition";
	}

	@RequestMapping(value = "finalorder", method = RequestMethod.GET)
	public String proceedToFinalOrder(ModelMap modelMap) {
		List<CartItemPres> cartItems = browseSelectData.getCartData2();
		modelMap.addAttribute("cartItems", cartItems);
		return "finalorder";
	}

	@RequestMapping(value = "finalorder", method = RequestMethod.POST)
	public String submitFinalOrder(@RequestParam Map<String, String> allRequestParams, ModelMap modelMap) {
		boolean checkRules = true;
		String message = "";
		try {
			checkoutController.runFinalOrderRules(ShoppingCartSubsystemFacade.INSTANCE);
		} catch (RuleException e) {
			checkRules = false;
			message = e.getMessage();
		} catch (BusinessException e) {
			checkRules = false;
			message = e.getMessage();
		}
		if (checkRules) {
			try {
				checkoutController.submitFinalOrder();
			} catch (BackendException e) {
				List<CartItemPres> cartItems = browseSelectData.getCartData2();
				modelMap.addAttribute("cartItems", cartItems);
				modelMap.addAttribute("message", e.getMessage());
				return "finalorder";
			}
			browseSelectData.clearCart();
			return "redirect:/customer/successful";
		} else {
			List<CartItemPres> cartItems = browseSelectData.getCartData2();
			modelMap.addAttribute("cartItems", cartItems);
			modelMap.addAttribute("message", message);
			return "finalorder";
		}

	}

	@RequestMapping(value = "successful", method = RequestMethod.GET)
	public String successScreen(ModelMap modelMap) {
		modelMap.addAttribute("message", GuiConstants.SUCCESS_MESSAGE);
		return "successful";
	}

}
