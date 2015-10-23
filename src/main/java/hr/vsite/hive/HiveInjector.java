package hr.vsite.hive;

import com.google.inject.Guice;
import com.google.inject.Injector;

import hr.vsite.hive.dao.DaoModule;
import hr.vsite.hive.services.ServiceModule;

/**
 * Top level Guice Injector.
 */
public class HiveInjector {

	public static Injector get() {
		if (instance == null)
			instance = new HiveInjector();
		return instance.getInjector();
	}
	
	private HiveInjector() {
		injector = Guice.createInjector(
			new DaoModule(),
			new HiveModule(),
			new ServiceModule()
		);
	}

	public Injector getInjector() { return injector; }

	private static HiveInjector instance = null;
	private final Injector injector;

}
