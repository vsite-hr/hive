package hr.vsite.hive;

import javax.inject.Singleton;

import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.impl.StdSchedulerFactory;
import org.quartz.spi.JobFactory;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;

public class HiveModule extends AbstractModule {

	@Override 
	protected void configure() {
		bind(HiveProperties.class);
		bind(HiveConfiguration.class);
		bind(HiveEventBus.class);
		bind(JobFactory.class).to(GuiceJobFactory.class);
		bind(Garbageman.class);
		bind(HiveStatus.class);
	}

	@Provides
	@Singleton
	Scheduler provideScheduler(JobFactory jobFactory) throws SchedulerException {
		Scheduler scheduler = new StdSchedulerFactory("quartz.properties").getScheduler();
    	scheduler.setJobFactory(jobFactory);
		return scheduler;
	}

}
