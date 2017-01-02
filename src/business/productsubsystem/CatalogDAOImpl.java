package business.productsubsystem;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import business.externalinterfaces.Catalog;
import business.externalinterfaces.CatalogDAO;

@Repository
public class CatalogDAOImpl implements CatalogDAO {
	public CatalogDAOImpl() {

	}

	private static final Logger LOG = Logger.getLogger(CatalogDAOImpl.class.getCanonicalName());

	private final String QUERY = "SELECT * FROM CatalogType";

	public String getQuery() {
		return QUERY;
	}

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

	class MyRowMapper implements RowMapper<Entry> {
		public Entry mapRow(ResultSet resultSet, int rownum) throws SQLException {
			Entry entry = new Entry();
			entry.catalogid = resultSet.getString("catalogid");
			entry.catalogname = resultSet.getString("catalogname");
			return entry;
		}
	}

	@Transactional(value = "txManagerProducts", propagation = Propagation.REQUIRES_NEW, readOnly = true)
	@Override
	public List<Catalog> getCatalogs() {

		List<Catalog> catalogList = new ArrayList<>();
		try {
			List<Entry> list = jdbcTemplate.query(QUERY, new MyRowMapper());
			for (Entry entry : list) {
				catalogList.add(new CatalogImpl(Integer.parseInt(entry.catalogid), entry.catalogname));
			}
		} catch (DataAccessException e) { // this is a subclass of
											// RuntimeException used by Spring
			LOG.warning("Rolling back transaction for getCatalogTypes() with query = " + QUERY);
			LOG.warning("Error details:\n" + e.getMessage());
		}
		System.out.println(catalogList.size());
		return catalogList;
	}

	static class Entry {
		String catalogid;
		String catalogname;
	}

	private final String DELETE_QUERY = "Delete from CatalogType Where catalogid=?";

	@Transactional(value = "txManagerProducts", propagation = Propagation.REQUIRES_NEW)
	@Override
	public void deleteCatalog(int catalogId) {
		// TODO Auto-generated method stub
		try {
			jdbcTemplate.update(DELETE_QUERY, catalogId);
		} catch (DataAccessException e) { // this is a subclass of
			// RuntimeException used by Spring
			LOG.warning("Rolling back transaction for deleteCatalog() with query = " + DELETE_QUERY);
			LOG.warning("Error details:\n" + e.getMessage());
		}
	}

}
