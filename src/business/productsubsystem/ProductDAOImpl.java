package business.productsubsystem;

import java.util.logging.Logger;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import business.externalinterfaces.ProductDAO;

@Repository
public class ProductDAOImpl implements ProductDAO{

	
	private static final Logger LOG = Logger.getLogger(CatalogDAOImpl.class.getCanonicalName());


	private final String DELETE_QUERY = "Delete from Product Where productid=?";


	private JdbcTemplate jdbcTemplate;

	// @Inject
	@Autowired
	// @Named("dataSourceProducts")
	@Qualifier("dataSourceProducts")
	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
		// System.out.println("jdbcTemplate is null? " + (jdbcTemplate ==
		// null));
	}
	
	@Transactional(value = "txManagerProducts", propagation = Propagation.REQUIRES_NEW)
	@Override
	public void deleteProduct(int productId) {
		// TODO Auto-generated method stub
		try {
			jdbcTemplate.update(DELETE_QUERY, productId);
		} catch (DataAccessException e) { // this is a subclass of
			// RuntimeException used by Spring
			LOG.warning("Rolling back transaction for deleteProduct() with query = " + DELETE_QUERY);
			LOG.warning("Error details:\n" + e.getMessage());
		}
	}

}
