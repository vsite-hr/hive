package hr.vsite.hive.services.jetty;

import javax.inject.Singleton;

import org.jboss.resteasy.plugins.server.servlet.HttpServlet30Dispatcher;

import com.google.inject.servlet.ServletModule;

import hr.vsite.hive.services.jetty.rest.v1.GuiceFilterDispatcherV1;

public class HiveServletModule extends ServletModule {

	@Override
	protected void configureServlets() {
		
		// TODO annotate instance with @Named for different API versions
		bind(HttpServlet30Dispatcher.class).in(Singleton.class);
		serve("/api/1/*").with(HttpServlet30Dispatcher.class);

		bind(GuiceFilterDispatcherV1.class).in(Singleton.class);
		filter("/api/1/*").through(GuiceFilterDispatcherV1.class);
		
	}

}
