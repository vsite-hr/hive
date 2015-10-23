package hr.vsite.hive.services.jetty.rest;

import java.util.Collections;
import java.util.Set;

import javax.ws.rs.core.Application;

/**
 * Place to configure JAX-RS.
 */
public class RestApplication extends Application {

	/**
	 * <b>DO NOT</b> register resource classes here because that will circumvent Guice injections.
	 * Instead, register them in {@link hr.vsite.hive.services.jetty.rest.resource.ResourceModule}
	 */
	@Override
	public Set<Class<?>> getClasses() {
		return Collections.emptySet();
	}
	
}