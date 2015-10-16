package hr.vsite.hive.dao;

import java.sql.Connection;

import javax.inject.Inject;

/**
 * H2 interface.
 */
public class H2Dao extends JdbcDao {

	@Inject
	H2Dao(Connection conn) {
		super(conn);
	}

}
