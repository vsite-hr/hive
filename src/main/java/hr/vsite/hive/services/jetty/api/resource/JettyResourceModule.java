package hr.vsite.hive.services.jetty.api.resource;

import com.google.inject.AbstractModule;

public class JettyResourceModule extends AbstractModule {

	@Override 
	protected void configure() {
		bind(HiveResource.class);
	}

}
