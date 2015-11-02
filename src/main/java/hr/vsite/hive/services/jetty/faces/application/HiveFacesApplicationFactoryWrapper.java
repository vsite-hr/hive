package hr.vsite.hive.services.jetty.faces.application;

import javax.faces.application.Application;
import javax.faces.application.ApplicationFactory;

public class HiveFacesApplicationFactoryWrapper extends ApplicationFactory {

	public HiveFacesApplicationFactoryWrapper(ApplicationFactory factory) {
		this.factory = factory;
	}

	@Override
	public Application getApplication() {
        Application application = factory.getApplication();
        return new HiveFacesApplicationWrapper(application);
	}

	@Override
	public void setApplication(Application application) {
		factory.setApplication(application);
	}

	private ApplicationFactory factory;
}
