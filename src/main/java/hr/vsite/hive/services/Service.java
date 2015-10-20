package hr.vsite.hive.services;

public interface Service {

	public enum State {
		Uninitialized,
		Running,
		Stopped,
		Destroyed
	}
	
	/**
	 * Should this service start or not?
	 */
	boolean isEnabled();

	/**
	 * Current state of this service
	 */
	Service.State getState();

	/**
	 * Initialize service. This method blocks until the service has completely initialized.
	 * Call only once!
	 */
	void init() throws Exception;
	
	/**
	 * Starts service. This method blocks until the service has completely started.
	 */
	void start() throws Exception;

	/**
	 * Stops service. This method blocks until the service has completely shut down.
	 */
	void stop();

	/**
	 * Destroys service. This method blocks until the service has completely released all its resources.
	 * Call only once!
	 * After this call service can not be used again.
	 */
	void destroy() throws Exception;
	
}
