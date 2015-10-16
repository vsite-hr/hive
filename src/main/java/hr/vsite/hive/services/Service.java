package hr.vsite.hive.services;

public interface Service {

	/**
	 * Should this service start or not?
	 */
	boolean isEnabled();

	/**
	 * Is this service running or not?
	 */
	boolean isRunning();

	/**
	 * Starts the service. This method blocks until the service has completely started.
	 */
	void start() throws Exception;
	
	/**
	 * Stops the service. This method blocks until the service has completely shut down.
	 */
	void stop();

}
