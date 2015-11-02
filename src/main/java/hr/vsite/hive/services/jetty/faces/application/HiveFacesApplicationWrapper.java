package hr.vsite.hive.services.jetty.faces.application;

import javax.el.ELResolver;
import javax.faces.application.Application;
import javax.faces.application.ApplicationWrapper;

import hr.vsite.hive.services.jetty.faces.el.HiveGuiceELResolverWrapper;

public class HiveFacesApplicationWrapper extends ApplicationWrapper {

	public HiveFacesApplicationWrapper(Application wrapped) {
		this.wrapped = wrapped;
	}

	@Override
	public Application getWrapped() {
		return wrapped;
	}

	public ELResolver getELResolver() {
		return new HiveGuiceELResolverWrapper(getWrapped().getELResolver());
	}

	private Application wrapped;
}
