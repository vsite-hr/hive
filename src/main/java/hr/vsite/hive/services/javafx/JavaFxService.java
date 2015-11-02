package hr.vsite.hive.services.javafx;

import javax.inject.Singleton;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import hr.vsite.hive.services.AbstractService;

import javafx.application.Application;
import javafx.application.Platform;

@Singleton
public class JavaFxService extends AbstractService {

	public static final String Key = "javafx";

	public JavaFxService() {
		super(Key);
	}

	@Override
	public void doInit() throws Exception {

		Platform.setImplicitExit(false);

		appThread = new Thread(new Runnable() {
			@Override
			public void run() {
				Application.launch(FxApplication.class);
			}
		});

		log.info("JavaFX service initialized");
		
	}

	@Override
	public void doStart() throws Exception {
		appThread.start();
		log.info("JavaFX service started");
	}

	@Override
	public void doStop() {
		Platform.exit();
		log.info("JavaFX service stopped");
	}

	@Override
	public void doDestroy() {
		log.info("JavaFX service destroyed");
	}

	private static final Logger log = LoggerFactory.getLogger(JavaFxService.class);

	private Thread appThread = null;
	
}
