package hr.vsite.hive;

import com.google.inject.AbstractModule;

public class HiveModule extends AbstractModule {

	@Override 
	protected void configure() {
		bind(HiveProperties.class).asEagerSingleton();
		bind(HiveConfiguration.class).asEagerSingleton();
	}

}
