package hr.vsite.hive;

import javax.inject.Inject;
import javax.inject.Singleton;

import org.quartz.Job;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.spi.JobFactory;
import org.quartz.spi.TriggerFiredBundle;

import com.google.inject.Injector;

/**
 * Based on: http://blog.timmattison.com/archives/2014/08/05/using-guice-dependency-injection-with-quartz-schedulding/
 */
@Singleton
class GuiceJobFactory implements JobFactory {

	@Inject
	public GuiceJobFactory(Injector guice) {
		this.guice = guice;
	}

	@Override
	public Job newJob(TriggerFiredBundle triggerFiredBundle, Scheduler scheduler) throws SchedulerException {

		JobDetail jobDetail = triggerFiredBundle.getJobDetail();
		Class<? extends Job> jobClass = jobDetail.getJobClass();

		return Job.class.cast(guice.getInstance(jobClass));
		
	}

	private final Injector guice;

}