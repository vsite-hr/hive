package hr.vsite.hive.services.jetty.rest;

import javax.servlet.FilterConfig;
import javax.servlet.ServletException;

import org.jboss.resteasy.plugins.guice.ModuleProcessor;
import org.jboss.resteasy.plugins.server.servlet.Filter30Dispatcher;
import org.jboss.resteasy.spi.Registry;
import org.jboss.resteasy.spi.ResteasyProviderFactory;

import com.google.inject.Injector;
import com.google.inject.Module;

/**
 * <p>
 * Substitute for org.jboss.resteasy.plugins.guice.GuiceResteasyBootstrapServletContextListener,
 * because listeners can't get app-wide Guice Injector (at least not if instantiated from web.xml what is what we use)
 * </p>
 * <p>
 * To separate injection between different API versions, subclass this class
 * and provide modules that define resources for given API version.
 * </p>
 */
public abstract class AbstractGuiceFilterDispatcher extends Filter30Dispatcher {

	protected AbstractGuiceFilterDispatcher(Injector parentInjector) {
		this.parentInjector = parentInjector;
	}
	
	@Override
	public void init(FilterConfig config) throws ServletException {

		super.init(config);

		Registry registry = getDispatcher().getRegistry();
		ResteasyProviderFactory providerFactory = getDispatcher().getProviderFactory();

		ModuleProcessor processor = new ModuleProcessor(registry, providerFactory);

		Injector injector = parentInjector.createChildInjector(getModule());

		processor.processInjector(injector);

		while (injector.getParent() != null) {
			injector = injector.getParent();
			processor.processInjector(injector);
		}      

	}
	
	protected abstract Module getModule();
	
	private final Injector parentInjector;
	
}
