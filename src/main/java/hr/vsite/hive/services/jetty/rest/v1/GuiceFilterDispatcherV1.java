package hr.vsite.hive.services.jetty.rest.v1;

import javax.inject.Inject;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;

import com.google.inject.Injector;
import com.google.inject.Module;

import hr.vsite.hive.services.jetty.rest.AbstractGuiceFilterDispatcher;

public class GuiceFilterDispatcherV1 extends AbstractGuiceFilterDispatcher {

	@Inject
	GuiceFilterDispatcherV1(Injector parentInjector) {
		super(parentInjector);
	}
	
	protected Module getModule() {
		return new ResourceModuleV1();
	}

	@Override
	public void init(FilterConfig config) throws ServletException {
		config.getServletContext().setInitParameter("resteasy.servlet.mapping.prefix", "/api/1");
		config.getServletContext().setInitParameter("javax.ws.rs.Application", RestApplicationV1.class.getName());
		config.getServletContext().setInitParameter("resteasy.logger.type", "SLF4J");
		super.init(config);
	}
	
}
