package hr.vsite.hive.services.jetty.rest.v1;

import com.google.inject.AbstractModule;

import hr.vsite.hive.services.jetty.rest.v1.providers.ObjectMapperProvider;
import hr.vsite.hive.services.jetty.rest.v1.resources.RootResource;

/**
 * Place to register all JAX-RS resources for API v1
 */
public class ResourceModuleV1 extends AbstractModule {

	@Override 
	protected void configure() {

		// providers
		bind(ObjectMapperProvider.class);
		
		// resources
		bind(RootResource.class);
		
	}

}
