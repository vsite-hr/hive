package hr.vsite.hive.dao;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Properties;

import javax.inject.Singleton;
import javax.sql.DataSource;

import org.apache.commons.dbcp2.BasicDataSourceFactory;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;

public class DaoModule extends AbstractModule {

	@Override 
	protected void configure() {
		bind(HiveDao.class).to(NullDao.class);	// TODO make configurable
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
	
}
