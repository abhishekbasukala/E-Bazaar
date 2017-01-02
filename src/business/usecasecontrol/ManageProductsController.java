package business.usecasecontrol;

import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import middleware.exceptions.DatabaseException;
import business.exceptions.BackendException;
import business.externalinterfaces.Product;
import business.externalinterfaces.ProductSubsystem;
import business.productsubsystem.ProductSubsystemFacade;

@Component
public class ManageProductsController {
	//INSTANCE;
	@Autowired
	ProductSubsystem productSubsystem;
	
	private static final Logger LOG = Logger.getLogger(ManageProductsController.class.getName());

	public List<Product> getProductsList(String catalog) throws BackendException {
		ProductSubsystem pss = new ProductSubsystemFacade();
		LOG.warning("ManageProductsController method getProductsList has not been implemented");
		// return pss.getProductList(catalog);
		return null;
	}

	public int saveNewCatalog(String catName) throws BackendException {
		ProductSubsystem pss = new ProductSubsystemFacade();
		return pss.saveNewCatalog(catName);
	}

	public void saveNewProduct(Product product) throws BackendException {
		ProductSubsystem pss = new ProductSubsystemFacade();
		pss.saveNewProduct(product);
	}

	public void deleteProduct(int productId) {
		productSubsystem.deleteProduct(productId);
	}

	public Product getProductById(Integer productId) throws BackendException {
		ProductSubsystem pss = new ProductSubsystemFacade();
		return pss.getProductFromId(productId);
	}

	public void deleteCatalog(int catalogId) {
		
		productSubsystem.deleteCatalog(catalogId);
	}

}
