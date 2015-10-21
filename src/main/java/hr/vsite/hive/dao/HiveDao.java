package hr.vsite.hive.dao;

public interface HiveDao {

	void commit();
	
	void rollback();
	
	void archive(int daysBack);
	
}
