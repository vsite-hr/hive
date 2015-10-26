package hr.vsite.hive.services.jetty;

import javax.inject.Singleton;

import org.jboss.resteasy.plugins.server.servlet.HttpServlet30Dispatcher;

import com.google.inject.Key;
import com.google.inject.name.Names;
import com.google.inject.servlet.ServletModule;

import hr.vsite.hive.services.jetty.rest.v1.GuiceFilterDispatcherV1;

public class HiveServletModule extends ServletModule {

	@Override
	protected void configureServlets() {
		
		// one servlet for all API verisons, so use Named annotation to make a difference
		bind(HttpServlet30Dispatcher.class).annotatedWith(Names.named("V1")).to(HttpServlet30Dispatcher.class).in(Singleton.class);
		serve("/api/1/*").with(Key.get(HttpServlet30Dispatcher.class, Names.named("V1")));

		bind(GuiceFilterDispatcherV1.class).in(Singleton.class);
		filter("/api/1/*").through(GuiceFilterDispatcherV1.class);

	}

}
