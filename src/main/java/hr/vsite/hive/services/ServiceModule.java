package hr.vsite.hive.services;

import com.google.inject.AbstractModule;

import hr.vsite.hive.services.jetty.JettyService;

public class ServiceModule extends AbstractModule {

	@Override 
	protected void configure() {
		bind(JettyService.class);
	}

}
