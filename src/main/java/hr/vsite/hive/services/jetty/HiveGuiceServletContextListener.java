package hr.vsite.hive.services.jetty;

import com.google.inject.Injector;
import com.google.inject.servlet.GuiceServletContextListener;

import hr.vsite.hive.Hive;

/**
 * Creates Guice Injector that will manage all servlets and filters.
 */
public class HiveGuiceServletContextListener extends GuiceServletContextListener {

	@Override
	protected Injector getInjector() {
		return Hive.get().getInjector().createChildInjector(new HiveServletModule());
	}

}
