package hr.vsite.hive.services.javafx;

import com.google.inject.Injector;

import hr.vsite.hive.HiveInjector;

/**
 * Java FX Guice Injector.
 */
public class FxInjector {

	public static Injector get() {
		if (instance == null)
			instance = new FxInjector();
		return instance.getInjector();
	}
	
	private FxInjector() {
		injector = HiveInjector.get().createChildInjector(
			new FxModule()
		);
	}

	public Injector getInjector() { return injector; }

	private static FxInjector instance = null;
	private final Injector injector;

}
