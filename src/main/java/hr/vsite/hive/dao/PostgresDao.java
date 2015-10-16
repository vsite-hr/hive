package hr.vsite.hive.dao;

import java.sql.Connection;

import javax.inject.Inject;

/**
 * PostgreSQL interface.
 */
public class PostgresDao extends JdbcDao {

	@Inject
	PostgresDao(Connection conn) {
		super(conn);
	}

	// TODO

}
