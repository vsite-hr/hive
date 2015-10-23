package hr.vsite.hive.services.jetty.rest.resource;

import com.google.inject.AbstractModule;

/**
 * Place to register all JAX-RS resources
 */
public class ResourceModule extends AbstractModule {

	@Override 
	protected void configure() {
		bind(HiveResource.class);
	}

}
