package business.usecasecontrol;

import java.util.List;
import java.util.logging.Logger;

import org.springframework.stereotype.Component;

import presentation.data.CartItemPres;
import presentation.util.Util;
import business.BusinessConstants;
import business.SessionCache;
import business.customersubsystem.RulesPayment;
import business.exceptions.BackendException;
import business.exceptions.BusinessException;
import business.exceptions.RuleException;
import business.externalinterfaces.Address;
import business.externalinterfaces.CreditCard;
import business.externalinterfaces.CustomerProfile;
import business.externalinterfaces.CustomerSubsystem;
import business.externalinterfaces.Rules;
import business.externalinterfaces.ShoppingCartSubsystem;
import business.shoppingcartsubsystem.ShoppingCartSubsystemFacade;
import business.util.DataUtil;

@Component
public class CheckoutController {
	//INSTANCE;

	private static final Logger LOG = Logger.getLogger(CheckoutController.class.getPackage().getName());

	public void runShoppingCartRules(List<CartItemPres> cartItems) throws RuleException, BusinessException {
		ShoppingCartSubsystemFacade.INSTANCE.runShippingRules(ShoppingCartSubsystemFacade
				.createShoppingCart(Util.cartItemPresToCartItemList(cartItems)));
	}

	public List<Address> getShippingAddresses(CustomerProfile custProf) throws BackendException {
		LOG.warning("Method CheckoutController.getShippingAddresses has been implemented");
		CustomerSubsystem customerSubsystem = DataUtil.readCustFromCache();
		return customerSubsystem.getAllAddresses();
	}

	public Address getDefaultShippingAddress(CustomerProfile custProf) throws BackendException {
		CustomerSubsystem customerSubsystem = DataUtil.readCustFromCache();
		return customerSubsystem.getDefaultShippingAddress();
	}

	public Address getDefaultBillingAddress(CustomerProfile custProf) throws BackendException {
		CustomerSubsystem customerSubsystem = DataUtil.readCustFromCache();
		return customerSubsystem.getDefaultBillingAddress();
	}

	public CreditCard getDefaultCreditCardInfo(CustomerProfile custProf) throws BackendException {
		CustomerSubsystem customerSubsystem = DataUtil.readCustFromCache();
		return customerSubsystem.getDefaultPaymentInfo();
	}

	public void runPaymentRules(Address addr, CreditCard cc) throws RuleException, BusinessException {
		Rules transferObject = new RulesPayment(addr, cc);
		transferObject.runRules();
	}

	public Address runAddressRules(Address addr) throws RuleException, BusinessException {
		CustomerSubsystem cust = (CustomerSubsystem) SessionCache.getInstance().get(BusinessConstants.CUSTOMER);
		return cust.runAddressRules(addr);
	}

	/** Asks the ShoppingCart Subsystem to run final order rules */
	public void runFinalOrderRules(ShoppingCartSubsystem scss) throws RuleException, BusinessException {
		// implement
		ShoppingCartSubsystemFacade.INSTANCE.runFinalOrderRules(scss.getLiveCart());
	}

	/**
	 * Asks Customer Subsystem to check credit card against Credit Verification
	 * System
	 */
	public void verifyCreditCard() throws BusinessException {
		// implement
	}

	public void saveNewAddress(Address addr) throws BackendException {
		CustomerSubsystem customerSubsystem = DataUtil.readCustFromCache();
		customerSubsystem.saveNewAddress(addr);
	}

	/** Asks Customer Subsystem to submit final order */
	public void submitFinalOrder() throws BackendException {
		CustomerSubsystem customerSubsystem = DataUtil.readCustFromCache();
		customerSubsystem.submitOrder();
	}

	public void clearLiveCart() {
		ShoppingCartSubsystemFacade.INSTANCE.clearLiveCart();
	}

	public void setShippingAddress(Address userShipAdd) {
		CustomerSubsystem cust = (CustomerSubsystem) SessionCache.getInstance().get(BusinessConstants.CUSTOMER);
		cust.setShippingAddressInCart(userShipAdd);
	}

	public void setBillingAddress(Address userBillAdd) {
		CustomerSubsystem cust = (CustomerSubsystem) SessionCache.getInstance().get(BusinessConstants.CUSTOMER);
		cust.setBillingAddressInCart(userBillAdd);
	}

	public void setPaymentInfoInCart(CreditCard cc) {
		CustomerSubsystem cust = (CustomerSubsystem) SessionCache.getInstance().get(BusinessConstants.CUSTOMER);
		cust.setPaymentInfoInCart(cc);
	}

	public void deleteAddress(int addressId) throws BackendException {
		CustomerSubsystem cust = (CustomerSubsystem) SessionCache.getInstance().get(BusinessConstants.CUSTOMER);
		cust.deleteAddress(addressId);
	}

}
