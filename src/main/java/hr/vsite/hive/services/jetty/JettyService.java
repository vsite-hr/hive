package hr.vsite.hive.services.jetty;

import java.io.InputStream;

import javax.inject.Inject;
import javax.inject.Singleton;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.xml.XmlConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import hr.vsite.hive.HiveConfiguration;
import hr.vsite.hive.services.AbstractService;

@Singleton
public class JettyService extends AbstractService {

	public static final String Key = "jetty";

	@Inject
	public JettyService(HiveConfiguration conf) {
		super(conf, Key);
	}

	@Override
	public void doInit() throws Exception {

		try (InputStream istream = ClassLoader.getSystemResourceAsStream("jetty.xml")) {
			XmlConfiguration jettyConf = new XmlConfiguration(istream);
			server = Server.class.cast(jettyConf.configure());
		}

		// TODO set servlet context param "resteasy.logger.type" to "SLF4J"
		
		log.info("Jetty service initialized");
		
	}

	@Override
	public void doStart() throws Exception {
		server.start();
		log.info("Jetty service started");
	}

	@Override
	public void doStop() {
		try {
			server.stop();
			log.info("Jetty service stopped");
		} catch (Exception e) {
			log.error("Error stopping jetty", e);
		}
	}

	@Override
	public void doDestroy() {
		server.destroy();
		log.info("Jetty service destroyed");
	}

	private static final Logger log = LoggerFactory.getLogger(JettyService.class);

	private Server server = null;
	
}
