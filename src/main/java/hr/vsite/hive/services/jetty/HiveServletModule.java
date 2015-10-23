package hr.vsite.hive.services.jetty;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Singleton;

import org.jboss.resteasy.plugins.server.servlet.HttpServlet30Dispatcher;

import com.google.inject.servlet.ServletModule;

import hr.vsite.hive.services.jetty.rest.GuiceRestEasyFilterDispatcher;
import hr.vsite.hive.services.jetty.rest.RestApplication;

public class HiveServletModule extends ServletModule {

	@Override
	protected void configureServlets() {
		
		bind(HttpServlet30Dispatcher.class).in(Singleton.class);

		Map<String, String> httpServlet30DispatcherParams = new HashMap<String, String>();
		httpServlet30DispatcherParams.put("javax.ws.rs.Application", RestApplication.class.getName());
		httpServlet30DispatcherParams.put("resteasy.logger.type", "SLF4J");
		serve("/api/*").with(HttpServlet30Dispatcher.class, httpServlet30DispatcherParams);

		bind(GuiceRestEasyFilterDispatcher.class).in(Singleton.class);

		filter("/api/*").through(GuiceRestEasyFilterDispatcher.class);
		
	}

}
