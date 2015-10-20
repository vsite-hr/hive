package hr.vsite.hive;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Guice;
import com.google.inject.Injector;

import hr.vsite.hive.dao.DaoModule;
import hr.vsite.hive.services.ServiceModule;

public class Hive {

	public static Hive get() {
		if (instance == null)
			instance = new Hive();
		return instance;
	}

	private Hive() {}

	public void	init() {

		injector = Guice.createInjector(
			new DaoModule(),
			new HiveModule(),
			new ServiceModule()
		);
        
		props = injector.getInstance(HiveProperties.class);
		eventBus = injector.getInstance(HiveEventBus.class);
		
		log.info("**************************************************");
		log.info("Welcome to Hive {}...", props.getVersion());
		log.info("**************************************************");

		eventBus.post(new HiveInitEvent(this));
		
	}
	
	void start() {
		log.info("Hive {} starting...", props.getVersion());
		eventBus.post(new HiveStartEvent(this));
	}
	
	void stop() {
		log.info("Hive {} stopping...", props.getVersion());
		eventBus.post(new HiveStopEvent(this));
	}
	
	public void destroy() {
		log.info("Hive {} is initializing shutdown...", props.getVersion());
		eventBus.post(new HiveDestroyEvent(this));
		eventBus.shutdown();
		log.info("Hive {} successfully shut down - bye!", props.getVersion());
	}

	private static final Logger log = LoggerFactory.getLogger(Hive.class);
	private static Hive instance = null;

	private Injector injector = null;
	private HiveProperties props = null;
	private HiveEventBus eventBus = null;
	
}
