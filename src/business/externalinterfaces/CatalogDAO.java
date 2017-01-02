package business.externalinterfaces;

import java.util.List;

public interface CatalogDAO {

	public List<Catalog> getCatalogs();

	public void deleteCatalog(int catalogId);
}
