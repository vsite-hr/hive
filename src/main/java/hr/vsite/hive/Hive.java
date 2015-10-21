package hr.vsite.hive;

import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.Job;
import org.quartz.JobBuilder;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.TriggerBuilder;
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

	public void	init() throws Exception {

		injector = Guice.createInjector(
			new DaoModule(),
			new HiveModule(),
			new ServiceModule()
		);
        
		props = injector.getInstance(HiveProperties.class);
		conf = injector.getInstance(HiveConfiguration.class);
		eventBus = injector.getInstance(HiveEventBus.class);
		scheduler = injector.getInstance(Scheduler.class);
		
		log.info("**************************************************");
		log.info("Welcome to Hive {}...", props.getVersion());
		log.info("**************************************************");

		schedule(Garbageman.class, "hive.garbageman.Schedule");
		
		eventBus.post(new HiveInitEvent(this));
		
	}
	
	public void start() throws Exception {
		
		log.info("Hive {} starting...", props.getVersion());
		
		scheduler.start();
		
		eventBus.post(new HiveStartEvent(this));
		
	}
	
	public void stop() {
		
		log.info("Hive {} stopping...", props.getVersion());
		
		try {
			scheduler.standby();
		} catch (SchedulerException e) {
			log.error("Unable to stop scheduler", e);
		}
		
		eventBus.post(new HiveStopEvent(this));
		
	}
	
	public void destroy() {

		log.info("Hive {} is initializing shutdown...", props.getVersion());
		
		try {
			scheduler.shutdown();
		} catch (SchedulerException e) {
			log.error("Unable to stop scheduler", e);
		}
		
		eventBus.post(new HiveDestroyEvent(this));
		eventBus.shutdown();
		
		log.info("Hive {} successfully shut down - bye!", props.getVersion());
		
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

	private Injector injector = null;
	private HiveProperties props = null;
	private HiveConfiguration conf = null;
	private HiveEventBus eventBus = null;
	private Scheduler scheduler = null;
	
}
