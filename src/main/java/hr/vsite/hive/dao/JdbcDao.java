package hr.vsite.hive.dao;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Base class for all JDBC (SQL) DAOs. Manages connection and transactions.
 */
public abstract class JdbcDao extends AbstractDao {

	JdbcDao(Connection conn) {
		this.conn = conn;
	}

	public Connection getConn() { return conn; }

	@Override
	public void commit() {
		try {
			conn.commit();
		} catch (SQLException e) {
			throw new RuntimeException("Error commiting tx", e);
		}
	}

	@Override
	public void rollback() {
		try {
			conn.rollback();
		} catch (SQLException e) {
			throw new RuntimeException("Error rollbacking tx", e);
		}
	}

	@Override
	public void close() throws Exception {
		conn.close();
	}

	private final Connection conn;

}
