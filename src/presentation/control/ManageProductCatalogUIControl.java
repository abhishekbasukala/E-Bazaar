package presentation.control;

import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import presentation.data.BrowseSelectData;
import presentation.data.CatalogPres;
import presentation.data.ManageProductsData;
import presentation.data.ProductPres;
import presentation.util.Util;
import business.exceptions.BackendException;
import business.externalinterfaces.Catalog;
import business.externalinterfaces.Product;
import business.externalinterfaces.ProductSubsystem;
import business.productsubsystem.ProductSubsystemFacade;
import business.usecasecontrol.ManageProductsController;

@Controller
@RequestMapping("/admin")
public class ManageProductCatalogUIControl {

	@Autowired
	BrowseSelectData browseSelectData;

	@Autowired
	ManageProductsData manageProductsData;

	@Autowired
	ManageProductsController manageProductsController;

	private static final Logger LOG = Logger.getLogger(ManageProductCatalogUIControl.class.getPackage().getName());

	@RequestMapping(value = "/cataloglist")
	public String viewCatalogsHandler(ModelMap modelMap) {
		List<CatalogPres> catalogs = null;
		try {
			catalogs = browseSelectData.getCatalogList();
		} catch (BackendException e) {
			LOG.severe(e.getMessage());
			modelMap.addAttribute("errorMessage", e.getMessage());
		}
		modelMap.addAttribute("catalogs", catalogs);
		return "admin/cataloglist";
	}

	@RequestMapping(value = "/addnewcatalog", method = RequestMethod.GET)
	public String newcatalogHandler(ModelMap modelMap) {
		LOG.info("Adding New Catalog.....");
		return "admin/newcatalog";
	}

	@RequestMapping(value = "/addnewproduct", method = RequestMethod.GET)
	public String addNewProductForm(@RequestParam("id") String catid, @RequestParam("value") String catName,
			ModelMap modelMap) {
		LOG.info("Add New Product Form.....");
		modelMap.addAttribute("catId", catid);
		modelMap.addAttribute("catName", catName);
		return "admin/newproduct";
	}

	@RequestMapping(value = "/savecatalog", method = RequestMethod.POST)
	public String addNewCatalog(@RequestParam Map<String, String> params, ModelMap modelMap) {
		LOG.info("Save new catalog...");
		try {
			ProductSubsystem pss = new ProductSubsystemFacade();
			pss.saveNewCatalog(params.get("catName"));
		} catch (BackendException e) {
			e.printStackTrace();
		}
		return "redirect:/admin/cataloglist";
	}

	@RequestMapping(value = "/saveproduct", method = RequestMethod.POST)
	public String addNewProduct(@RequestParam Map<String, String> params, ModelMap modelMap) throws BackendException {
		LOG.info("Save new product...");
		String catalogId = params.get("catalogId");
		String catalogName = params.get("catalogName");
		String productName = params.get("productName");
		String manufactureDate = params.get("manufactureDate");
		String quantity = params.get("quantity");
		String unitPrice = params.get("unitPrice");
		String description = params.get("description");

		Catalog catalog = ProductSubsystemFacade.createCatalog(Integer.parseInt(catalogId), catalogName);
		Product product = ProductSubsystemFacade.createProduct(catalog, 0, productName, Integer.parseInt(quantity),
				Integer.parseInt(unitPrice), Util.localDateForString(manufactureDate), description);
		manageProductsController.saveNewProduct(product);
		return "redirect:/admin/productlist?catId=" + catalogId + "&name=" + catalogName;
	}

	@RequestMapping(value = "/productlist")
	public String viewProductListHandler(@RequestParam(value = "catId", required = false) String id,
			@RequestParam(value = "name", required = false) String name, ModelMap modelMap) {
		LOG.info("Retrieving product list...");
		List<CatalogPres> catalogs = null;
		List<ProductPres> products = null;

		try {
			catalogs = browseSelectData.getCatalogList();
			if (id == null) {
				products = browseSelectData.getProductList(catalogs.get(0));
				id = catalogs.get(0).getId() + "";
				name = catalogs.get(0).getName();
			} else {
				Catalog cat = ProductSubsystemFacade.createCatalog(Integer.parseInt(id), "");
				CatalogPres catPress = new CatalogPres();
				catPress.setCatalog(cat);
				products = browseSelectData.getProductList(catPress);

			}
		} catch (BackendException e) {
			e.printStackTrace();
			modelMap.addAttribute("message", e.getMessage());
		}

		modelMap.addAttribute("products", products);
		modelMap.addAttribute("catalogs", catalogs);
		modelMap.addAttribute("selectedId", id);
		modelMap.addAttribute("name", name);

		return "admin/manageproduct";
	}

	@RequestMapping(value = "/deletecatalog/{id}", method = RequestMethod.GET)
	public String deleteCatalog(@PathVariable("id") int catalogId) {
		System.out.println("This is catalog Id= " + catalogId);
		manageProductsData.deleteCatalog(catalogId);
		return "redirect:/admin/cataloglist";
	}

	@RequestMapping(value = "/deleteproduct/{id}", method = RequestMethod.GET)
	public String deleteProduct(@PathVariable("id") int productId) {
		System.out.println("Product id= "+productId);
		manageProductsData.deleteProduct(productId);
		return "redirect:/admin/productlist";
	}
}
