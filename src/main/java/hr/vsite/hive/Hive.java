package hr.vsite.hive;

import java.util.HashSet;
import java.util.Set;

import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.Job;
import org.quartz.JobBuilder;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.TriggerBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import hr.vsite.hive.services.Service;
import hr.vsite.hive.services.jetty.JettyService;

public class Hive {

	public static Hive get() {
		if (instance == null)
			instance = new Hive();
		return instance;
	}

	private Hive() {}

	public void	init() throws Exception {
        
		props = HiveInjector.get().getInstance(HiveProperties.class);
		conf = HiveInjector.get().getInstance(HiveConfiguration.class);
		eventBus = HiveInjector.get().getInstance(HiveEventBus.class);
		scheduler = HiveInjector.get().getInstance(Scheduler.class);
		
		log.info("**************************************************");
		log.info("Welcome to Hive {}...", props.getVersion());
		log.info("**************************************************");

		// TODO use discovery (use injector.findBindingsByType maybe?)
		serviceClasses = new HashSet<Class<? extends Service>>();
		serviceClasses.add(JettyService.class);
		
		initServices();
		
		schedule(Garbageman.class, "hive.garbageman.Schedule");
		
		eventBus.post(new HiveInitEvent(this));
		
	}
	
	public void start() throws Exception {
		
		log.info("Hive {} starting...", props.getVersion());
		
		startServices();
		
		scheduler.start();
		
		eventBus.post(new HiveStartEvent(this));
		
	}
	
	public void stop() {
		
		log.info("Hive {} stopping...", props.getVersion());

		stopServices();

		try {
			scheduler.standby();
		} catch (SchedulerException e) {
			log.error("Unable to stop scheduler", e);
		}
		
		eventBus.post(new HiveStopEvent(this));
		
	}
	
	public void destroy() {

		log.info("Hive {} is initializing shutdown...", props.getVersion());
		
		destroyServices();

		try {
			scheduler.shutdown();
		} catch (SchedulerException e) {
			log.error("Unable to stop scheduler", e);
		}
		
		eventBus.post(new HiveDestroyEvent(this));
		eventBus.shutdown();
		
		log.info("Hive {} successfully shut down - bye!", props.getVersion());
		
	}

	private void initServices() {
		for (Class<? extends Service> serviceClass : serviceClasses) {
			Service service = HiveInjector.get().getInstance(serviceClass);
			if (service.isEnabled()) {
				try {
					service.init();
				} catch (Exception e) {
					log.error("Unable to init service {}", serviceClass.getName(), e);
				}
			}
		}
	}
	
	private void startServices() {
		for (Class<? extends Service> serviceClass : serviceClasses) {
			Service service = HiveInjector.get().getInstance(serviceClass);
			if (service.isEnabled() && service.getState() == Service.State.Stopped) {
				try {
					service.start();
				} catch (Exception e) {
					log.error("Unable to start service {}", serviceClass.getName(), e);
				}
			}
		}
	}
	
	private void stopServices() {
		for (Class<? extends Service> serviceClass : serviceClasses) {
			Service service = HiveInjector.get().getInstance(serviceClass);
			if (service.isEnabled() && service.getState() == Service.State.Running) {
				try {
					service.stop();
				} catch (Exception e) {
					log.error("Unable to stop service {}", serviceClass.getName(), e);
				}
			}
		}
	}
	
	private void destroyServices() {
		for (Class<? extends Service> serviceClass : serviceClasses) {
			Service service = HiveInjector.get().getInstance(serviceClass);
			if (service.isEnabled() && service.getState() == Service.State.Stopped) {
				try {
					service.destroy();
				} catch (Exception e) {
					log.error("Unable to destroy service {}", serviceClass.getName(), e);
				}
			}
		}
	}
	
	private void schedule(Class<? extends Job> clazz, String scheduleKey) throws SchedulerException {

		CronTrigger trigger;
		
		scheduler.scheduleJob(
			JobBuilder.newJob(Garbageman.class)
				.build(),
			trigger = TriggerBuilder.newTrigger()
				.withSchedule(CronScheduleBuilder.cronSchedule(conf.getString(scheduleKey)))
				.build()
		);

		log.info("{} scheduled at {}", clazz.getSimpleName(), trigger.getCronExpression());
		
	}
	
	private static final Logger log = LoggerFactory.getLogger(Hive.class);
	private static Hive instance = null;

	private HiveProperties props = null;
	private HiveConfiguration conf = null;
	private HiveEventBus eventBus = null;
	private Scheduler scheduler = null;
	private Set<Class<? extends Service>> serviceClasses;
	
}
