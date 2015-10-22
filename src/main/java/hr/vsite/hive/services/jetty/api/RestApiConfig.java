package hr.vsite.hive.services.jetty.api;

import java.util.Set;

import javax.ws.rs.core.Application;

import com.google.common.collect.Sets;

import hr.vsite.hive.services.jetty.api.resource.HiveResource;

public class RestApiConfig extends Application {

	public RestApiConfig() {
		
		super();

		classes = Sets.newHashSet(
			HiveResource.class
		);

	}

	@Override
	public Set<Class<?>> getClasses() { return classes; }

	private final Set<Class<?>> classes;

}