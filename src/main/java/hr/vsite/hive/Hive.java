package hr.vsite.hive;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Hive {

	public static Hive get() {
		if (instance == null)
			instance = new Hive();
		return instance;
	}

	private Hive() {}

	public void	init() {

    	long startTime = System.currentTimeMillis();

        log.info("**************************************************");
        log.info("Welcome to Hive {}...", HiveProperties.get().getVersion());
        log.info("**************************************************");

        HiveConfiguration.get();
        
        log.info("Hive {} initialized in {} seconds", HiveProperties.get().getVersion(), (System.currentTimeMillis() - startTime) / 1000);

	}
	
	void start() {

    	long startTime = System.currentTimeMillis();

		log.info("Hive {} starting...", HiveProperties.get().getVersion());

		// TODO
    	
        log.info("Hive {} started in {} seconds", HiveProperties.get().getVersion(), (System.currentTimeMillis() - startTime) / 1000);
    	
	}
	
	void stop() {

		long startTime = System.currentTimeMillis();
		
		log.info("Hive {} stopping...", HiveProperties.get().getVersion());

		// TODO
		
        log.info("Hive {} stopped in {} seconds", HiveProperties.get().getVersion(), (System.currentTimeMillis() - startTime) / 1000);
		
	}
	
	public void destroy() {

		log.info("Hive {} is initializing shutdown...", HiveProperties.get().getVersion());

		// TODO
		
		log.info("Hive {} successfully shut down - bye!", HiveProperties.get().getVersion());
		
	}

	private static final Logger log = LoggerFactory.getLogger(Hive.class);
	private static Hive instance = null;

}
