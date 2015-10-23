package hr.vsite.hive.services.jetty.rest;

import javax.inject.Inject;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;

import org.jboss.resteasy.plugins.guice.ModuleProcessor;
import org.jboss.resteasy.plugins.server.servlet.Filter30Dispatcher;
import org.jboss.resteasy.spi.Registry;
import org.jboss.resteasy.spi.ResteasyProviderFactory;

import com.google.inject.Injector;

import hr.vsite.hive.services.jetty.rest.resource.ResourceModule;

/**
 * Substitute for org.jboss.resteasy.plugins.guice.GuiceResteasyBootstrapServletContextListener,
 * because listeners can't get app-wide Guice Injector (at least not if instantiated from web.xml what is what we use)
 */
public class GuiceRestEasyFilterDispatcher extends Filter30Dispatcher {

	@Inject
	GuiceRestEasyFilterDispatcher(Injector parentInjector) {
		this.parentInjector = parentInjector;
	}
	
	@Override
	public void init(FilterConfig config) throws ServletException {

		super.init(config);

		Registry registry = getDispatcher().getRegistry();
		ResteasyProviderFactory providerFactory = getDispatcher().getProviderFactory();

		ModuleProcessor processor = new ModuleProcessor(registry, providerFactory);

		Injector injector = parentInjector.createChildInjector(new ResourceModule());

		processor.processInjector(injector);

		while (injector.getParent() != null) {
			injector = injector.getParent();
			processor.processInjector(injector);
		}      

	}
	
	private final Injector parentInjector;
	
}
