package hr.vsite.hive.dao;

/**
 * Void DAO implementation, does not store or return anything.
 * 
 * Useful for benchmarking other parts of app.
 * 
 */
public class NullDao extends AbstractDao {

	NullDao() {}

	@Override
	public void commit() {}

	@Override
	public void rollback() {}

	@Override
	public void close() throws Exception {}

	
}
