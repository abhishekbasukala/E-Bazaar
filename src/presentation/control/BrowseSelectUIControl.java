package presentation.control;

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

import presentation.data.BrowseSelectData;
import presentation.data.CartItemPres;
import presentation.data.CatalogPres;
import presentation.data.ManageProductsData;
import presentation.data.ProductPres;
import business.exceptions.BackendException;
import business.exceptions.BusinessException;
import business.exceptions.RuleException;
import business.externalinterfaces.Catalog;
import business.externalinterfaces.CatalogDAO;
import business.externalinterfaces.Product;
import business.shoppingcartsubsystem.ShoppingCartSubsystemFacade;
import business.usecasecontrol.BrowseAndSelectController;

@Controller
public class BrowseSelectUIControl {
@Autowired
private CatalogDAO catolog;

@Autowired
ManageProductsData manageProductsData;

@Autowired
BrowseAndSelectController browseAndSelectController;

@Autowired
BrowseSelectData browseSelectData;

	@RequestMapping("/")
	public String viewCatalogsHandler(ModelMap modelMap) {
		List<CatalogPres> catalogs = null;
		try {
			//List<Catalog> cat=catolog.getCatalogs();
			//System.out.println(cat.size());
			catalogs = browseSelectData.getCatalogList();
		} catch (BackendException e) {
			e.printStackTrace();
		}
		modelMap.addAttribute("catalogs", catalogs);
		return "catalog";
	}

	@RequestMapping(value = "/viewProductList/catalogId/{id}/catalogName/{name}", method = RequestMethod.GET)
	public String viewProductListHandler(@PathVariable int id, @PathVariable String name, ModelMap modelMap) {
		System.out.println("id: " + id + ", name: " + name);
		List<ProductPres> products = null;
		try {
			products = browseSelectData
					.getProductList(manageProductsData.createCatalogPres(id, name));
		} catch (BackendException e) {
			e.printStackTrace();
		}
		modelMap.addAttribute("products", products);
		modelMap.addAttribute("catalogName", name);
		return "product";
	}

	@RequestMapping("/viewProductDetail")
	public String viewProductDetailHandler(@RequestParam("id") int id, ModelMap modelMap) {
		System.out.println("viewProductsHandler2 - id: " + id);
		ProductPres prodPres = null;
		try {
			prodPres = manageProductsData.createProductPresByName(id);
		} catch (BackendException e) {
			e.printStackTrace();
		}
		modelMap.addAttribute("product", prodPres);
		return "productdetail";
	}

	@RequestMapping(value = "/addToCart", method = RequestMethod.GET)
	public String specifyQuantity(ModelMap modelMap) {
		modelMap.addAttribute("quantity", 1);
		return "specifyquantity";
	}

	@RequestMapping(value = "/addToCart", method = RequestMethod.POST)
	public String addToCartHandler(@RequestParam Map<String, String> allRequestParams, ModelMap modelMap,
			HttpSession session) {
		System.out.println("addToCartHandler.....");
		String name = allRequestParams.get("prodName");
		double unitPrice = Double.valueOf(allRequestParams.get("unitPrice"));
		CartItemPres cartPres = browseSelectData.cartItemPresFromData(name, unitPrice, 1);
		session.setAttribute("cartPres", cartPres);
		modelMap.addAttribute("quantity", 1);
		return "specifyquantity";
	}

	@RequestMapping(value = "/addQuantity", method = RequestMethod.POST)
	public String specifyQuantity(@RequestParam Map<String, String> allRequestParams, ModelMap modelMap,
			HttpSession session) {
		String quantityRequested = (allRequestParams.get("quantity"));
		String message = "";
		Product product;
		CartItemPres cartItemPres = (CartItemPres) session.getAttribute("cartPres");
		boolean checkRule = true;
		try {
			product = browseSelectData.getProductForProductName(cartItemPres.getItemName());
			browseAndSelectController.runQuantityRules(product, quantityRequested);
		} catch (RuleException | BackendException e) {
			checkRule = false;
			message = e.getMessage();
		} catch (BusinessException e) {
			checkRule = false;
			message = e.getMessage();
		}
		if (checkRule) {
			browseSelectData.addToCart2(cartItemPres);
			cartItemPres.setQuantity(Integer.valueOf(quantityRequested));
			List<CartItemPres> cartItems = browseSelectData.getCartData2();
			modelMap.addAttribute("cartItems", cartItems);
			return "redirect:/cart";
		} else {
			modelMap.addAttribute("message", message);
			return "specifyquantity";
		}
	}
	
	@RequestMapping(value = "/cart")
	public String getCart(ModelMap modelMap) throws BackendException {
		System.out.println("Retrieving cart.....");
		List<CartItemPres> cartItems = browseSelectData.getCartData2();
		modelMap.addAttribute("cartItems", cartItems);
		double totalPrice = 0.0;
		if (cartItems != null) {
			for (CartItemPres cartItemPres : cartItems) {
				totalPrice += cartItemPres.getTotalPrice();
			}
		}
		modelMap.addAttribute("cartItems", cartItems);
		modelMap.addAttribute("totalPrice", totalPrice);
		return "cart";
	}

	@RequestMapping(value = "customer/savecart")
	public String addToCartHandler() {
		System.out.println("Saving Cart.....");
		try {
			ShoppingCartSubsystemFacade.INSTANCE.saveCart();
		} catch (BackendException e) {
			e.printStackTrace();
		}
		return "redirect:/cart";
	}

	@RequestMapping(value = "customer/retrievesavedcart")
	public String retrieveSavedCartHandler(ModelMap modelMap) throws BackendException {
		System.out.println("Retrieving saved cart.....");
		List<CartItemPres> cartItems = null;
		try {
			cartItems = browseSelectData.retrieveSavedcart();
			double totalPrice = 0.0;
			if (cartItems != null) {
				for (CartItemPres cartItemPres : cartItems) {
					totalPrice += cartItemPres.getTotalPrice();
				}
			}
			modelMap.addAttribute("cartItems", cartItems);
			modelMap.addAttribute("totalPrice", totalPrice);
			return "cart";
		} catch (BackendException e) {
			modelMap.addAttribute("message", e.getMessage());
			return "catalog";
		}
	}

	@RequestMapping(value = "/shoppingCart", method = RequestMethod.GET)
	public String loadShoppingCart(ModelMap modelMap) {
		List<CartItemPres> cartItems = browseSelectData.getCartData2();
		modelMap.addAttribute("cartItems", cartItems);
		return "cart";
	}

	@RequestMapping(value = "/deleteItem/{cartItemPres}", method = RequestMethod.GET)
	public String deleteCartItem(@PathVariable String cartItemPres, ModelMap modelMap) {
		System.out.println("cartItem pres = " + cartItemPres);
		List<CartItemPres> cartItems = browseSelectData.deleteAndUpdateCart(cartItemPres);
		modelMap.addAttribute("cartItems", cartItems);
		return "redirect:/cart";
	}

}
