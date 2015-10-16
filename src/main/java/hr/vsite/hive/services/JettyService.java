package hr.vsite.hive.services;

import javax.inject.Inject;
import javax.inject.Singleton;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import hr.vsite.hive.HiveConfiguration;

@Singleton
public class JettyService extends AbstractService {

	@Inject
	public JettyService(HiveConfiguration conf) {
		super(conf, "jetty");
	}

	@Override
	public void doStart() throws Exception {
		log.info("Jetty service started");
	}

	@Override
	public void doStop() {
		log.info("Jetty service stopped");
	}

    private static final Logger log = LoggerFactory.getLogger(JettyService.class);

}
