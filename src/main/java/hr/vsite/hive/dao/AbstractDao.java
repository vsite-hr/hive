package hr.vsite.hive.dao;

import org.apache.commons.lang.NotImplementedException;

public abstract class AbstractDao implements HiveDao, AutoCloseable {

	AbstractDao() {}
	
	@Override
	public void commit() {
		throw new NotImplementedException();
	}

	@Override
	public void rollback() {
		throw new NotImplementedException();
	}
	
}
