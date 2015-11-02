package hr.vsite.hive.services.javafx;

import com.google.inject.AbstractModule;

import hr.vsite.hive.services.javafx.scenes.SensorsScene;

public class FxModule extends AbstractModule {

	@Override
	protected void configure() {

		bind(SensorsScene.class);
		
	}

	
}
