package hr.vsite.hive.services.jetty.rest.v1;

import java.util.Collections;
import java.util.Map;
import java.util.Set;

import javax.ws.rs.core.Application;

/**
 * Place to configure JAX-RS.
 */
public class RestApplicationV1 extends Application {

	/**
	 * <b>DO NOT</b> register resource classes here because that will circumvent Guice injections.
	 * Instead, register them in {@link hr.vsite.hive.services.jetty.rest.v1.ResourceModuleV1}
	 */
	@Override
	public Set<Class<?>> getClasses() {
		return Collections.emptySet();
	}

	@Override
	public Map<String, Object> getProperties() {
		return Collections.emptyMap();
	}

}