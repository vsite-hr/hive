package hr.vsite.hive;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.eventbus.AsyncEventBus;
import com.google.common.eventbus.SubscriberExceptionContext;
import com.google.common.eventbus.SubscriberExceptionHandler;

@Singleton
public class HiveEventBus extends AsyncEventBus {

	HiveEventBus() {
		this(Executors.newWorkStealingPool(), new SubscriberExceptionHandler() {
			@Override
			public void handleException(Throwable exception, SubscriberExceptionContext context) {
				log.error("Error in event handler ({})", context, exception);
			}
		});
	}

	HiveEventBus(ExecutorService executor, SubscriberExceptionHandler handler) {
		super(executor, handler);
		this.executor = executor;
	}

	public void shutdown() {
		executor.shutdown();
		try {
			executor.awaitTermination(10, TimeUnit.SECONDS);
		} catch (InterruptedException e) {
			log.error("Can't terminate event bus", e);
		}
	}
	
	private final ExecutorService executor;

	private static final Logger log = LoggerFactory.getLogger(HiveEventBus.class);

}
