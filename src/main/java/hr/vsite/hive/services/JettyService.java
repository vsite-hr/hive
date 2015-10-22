package hr.vsite.hive.services;

import java.net.InetSocketAddress;

import javax.inject.Inject;
import javax.inject.Singleton;

import org.eclipse.jetty.server.Server;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import hr.vsite.hive.HiveConfiguration;

@Singleton
public class JettyService extends AbstractService {

	public static final String Key = "jetty";

	@Inject
	public JettyService(HiveConfiguration conf) {
		super(conf, Key);
	}

	@Override
	public void doInit() throws Exception {

		String host = getConf().getHost();
		int port = getConf().getInt(getConfKey("Port"));
		server = new Server(new InetSocketAddress(host, port));

		log.info("Jetty service initialized at {}:{}", host, port);
		
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
