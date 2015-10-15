package hr.vsite.hive;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

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

    	Properties properties = new Properties();
    	try (InputStream propertiesStream = ClassLoader.getSystemResourceAsStream("hive.properties")) {
			properties.load(propertiesStream);
			version = properties.getProperty("version");
		} catch (IOException e) {
			throw new RuntimeException("Could not load properties", e);
		}
    	
        log.info("**************************************************");
        log.info("Welcome to Hive {}...", version);
        log.info("**************************************************");

        HiveConfiguration.get();
        
        log.info("Hive {} initialized in {} seconds", version, (System.currentTimeMillis() - startTime) / 1000);

	}
	
	void start() {

    	long startTime = System.currentTimeMillis();

		log.info("Hive {} starting...", version);

		// TODO
    	
        log.info("Hive {} started in {} seconds", version, (System.currentTimeMillis() - startTime) / 1000);
    	
	}
	
	void stop() {

		long startTime = System.currentTimeMillis();
		
		log.info("Hive {} stopping...", version);

		// TODO
		
        log.info("Hive {} stopped in {} seconds", version, (System.currentTimeMillis() - startTime) / 1000);
		
	}
	
	public void destroy() {

		log.info("Hive {} is initializing shutdown...", version);

		// TODO
		
		log.info("Hive {} successfully shut down - bye!", version);
		
	}

	private static final Logger log = LoggerFactory.getLogger(Hive.class);
	private static Hive instance = null;

	private String version = "0.TODO";	// TODO

}
