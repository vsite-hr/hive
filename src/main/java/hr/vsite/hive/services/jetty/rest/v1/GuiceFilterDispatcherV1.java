package hr.vsite.hive.services.jetty.rest.v1;

import javax.inject.Inject;

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
	
}
