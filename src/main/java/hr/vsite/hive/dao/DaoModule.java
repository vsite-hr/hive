package hr.vsite.hive.dao;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

import javax.inject.Singleton;
import javax.sql.DataSource;

import org.apache.commons.dbcp2.BasicDataSourceFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;

import hr.vsite.hive.HiveConfiguration;

public class DaoModule extends AbstractModule {

	@Override 
	protected void configure() {

		String daoClassName = HiveConfiguration.get().getString("hive.dao.Class");
		Class<? extends HiveDao> daoClass;
		try {
			daoClass = Class.forName(daoClassName).asSubclass(HiveDao.class);
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Can't locad DAO class \"" + daoClassName + "\"", e);
		}
		bind(HiveDao.class).to(daoClass).asEagerSingleton();
		log.info("Using {} as DAO", daoClassName);
		
	}

	// TODO use ThrowingProviders (https://github.com/google/guice/wiki/ThrowingProviders)
	@Provides
	@Singleton
	DataSource provideDataSource() throws Exception {
		Properties props = new Properties();
		try (InputStream istream = Thread.currentThread().getContextClassLoader().getResourceAsStream("jdbc.properties")) {
			if (istream == null)
				throw new FileNotFoundException("Could not find JDBC properties");
			props.load(istream);
		}
		return BasicDataSourceFactory.createDataSource(props);
	}
	
	@Provides
	Connection provideConnection(DataSource dataSource) throws SQLException {
		return dataSource.getConnection();
	}

	private static final Logger log = LoggerFactory.getLogger(DaoModule.class);

}
