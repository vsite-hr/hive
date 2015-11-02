package hr.vsite.hive;

import javax.inject.Inject;

import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import hr.vsite.hive.dao.HiveDao;

@DisallowConcurrentExecution
public class Garbageman implements Job {

	@Inject
	Garbageman(HiveDao dao) {
		daysBack = HiveConfiguration.get().getInt("hive.garbageman.DaysBack");
		this.dao = dao;
	}

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {

		log.info("Garbageman starts, throwing away stuff older than {} days", daysBack);

		long startTime = System.currentTimeMillis();
		
		dao.archive(daysBack);
		
		dao.commit();

		log.info("Garbageman ends in {} seconds", (System.currentTimeMillis() - startTime) / 1000);
		
	}
	
    private static final Logger log = LoggerFactory.getLogger(Garbageman.class);

    private final int daysBack;
    private final HiveDao dao;

}
