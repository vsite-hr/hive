package hr.vsite.hive.services;

import com.google.inject.AbstractModule;

public class ServiceModule extends AbstractModule {

	@Override 
	protected void configure() {
		bind(JettyService.class).asEagerSingleton();
	}

}
