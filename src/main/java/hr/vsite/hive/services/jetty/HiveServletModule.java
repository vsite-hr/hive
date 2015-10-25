package hr.vsite.hive.services.jetty;

import javax.inject.Singleton;

import com.google.inject.servlet.ServletModule;

import hr.vsite.hive.services.jetty.rest.v1.GuiceFilterDispatcherV1;
import hr.vsite.hive.services.jetty.rest.v1.HttpServletDispatcherV1;

public class HiveServletModule extends ServletModule {

	@Override
	protected void configureServlets() {
		
		bind(HttpServletDispatcherV1.class).in(Singleton.class);
		serve("/api/1/*").with(HttpServletDispatcherV1.class);

		bind(GuiceFilterDispatcherV1.class).in(Singleton.class);
		filter("/api/1/*").through(GuiceFilterDispatcherV1.class);
		
	}

}
