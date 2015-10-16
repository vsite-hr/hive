package hr.vsite.hive;

import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Guice;
import com.google.inject.Injector;

import hr.vsite.hive.dao.DaoModule;
import hr.vsite.hive.services.JettyService;
import hr.vsite.hive.services.Service;

public class Hive {

	public static Hive get() {
		if (instance == null)
			instance = new Hive();
		return instance;
	}

	private Hive() {}

	public void	init() {

		long startTime = System.currentTimeMillis();

		injector = Guice.createInjector(
			new DaoModule(),
			new HiveModule()
		);
        
		props = injector.getInstance(HiveProperties.class);
		
		log.info("**************************************************");
		log.info("Welcome to Hive {}...", props.getVersion());
		log.info("**************************************************");

		// TODO use discovery (use injector.findBindingsByType maybe?)
		serviceClasses = Arrays.asList(JettyService.class);
		
        log.info("Hive {} initialized in {} seconds", props.getVersion(), (System.currentTimeMillis() - startTime) / 1000);

	}
	
	void start() {

    	long startTime = System.currentTimeMillis();

		log.info("Hive {} starting...", props.getVersion());

		for (Class<? extends Service> serviceClass : serviceClasses) {
			Service service = injector.getInstance(serviceClass);
			if (service.isEnabled()) {
				try {
					service.start();
				} catch (Exception e) {
					log.error("Unable to start service {}", serviceClass.getName(), e);
				}
			}
		}
    	
        log.info("Hive {} started in {} seconds", props.getVersion(), (System.currentTimeMillis() - startTime) / 1000);
    	
	}
	
	void stop() {

		long startTime = System.currentTimeMillis();
		
		log.info("Hive {} stopping...", props.getVersion());

		for (Class<? extends Service> serviceClass : serviceClasses) {
			Service service = injector.getInstance(serviceClass);
			if (service.isEnabled()) {
				try {
					service.stop();
				} catch (Exception e) {
					log.error("Unable to stop service {}", serviceClass.getName(), e);
				}
			}
		}
		
        log.info("Hive {} stopped in {} seconds", props.getVersion(), (System.currentTimeMillis() - startTime) / 1000);
		
	}
	
	public void destroy() {

		log.info("Hive {} is initializing shutdown...", props.getVersion());

		// TODO

		log.info("Hive {} successfully shut down - bye!", props.getVersion());
		
	}

	private static final Logger log = LoggerFactory.getLogger(Hive.class);
	private static Hive instance = null;

	private Injector injector = null;
	private HiveProperties props = null;
	private List<Class<? extends Service>> serviceClasses;
	
}
