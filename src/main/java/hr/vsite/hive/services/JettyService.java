package hr.vsite.hive.services;

import javax.inject.Inject;
import javax.inject.Singleton;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import hr.vsite.hive.HiveConfiguration;
import hr.vsite.hive.HiveEventBus;

@Singleton
public class JettyService extends AbstractService {

	@Inject
	public JettyService(HiveConfiguration conf, HiveEventBus eventBus) {
		super(conf, "jetty");
		eventBus.register(this);
	}

	@Override
	public void doInit() throws Exception {
		log.info("Jetty service initialized");
	}

	@Override
	public void doStart() throws Exception {
		log.info("Jetty service started");
	}

	@Override
	public void doStop() {
		log.info("Jetty service stopped");
	}

	@Override
	public void doDestroy() {
		log.info("Jetty service destroyed");
	}

	private static final Logger log = LoggerFactory.getLogger(JettyService.class);

}
