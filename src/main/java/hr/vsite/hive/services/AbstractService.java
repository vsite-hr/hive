package hr.vsite.hive.services;

import hr.vsite.hive.HiveConfiguration;

public abstract class AbstractService implements Service {

	AbstractService(HiveConfiguration conf, String serviceKey) {
		enabled = conf.getBoolean("hive.services." + serviceKey + ".Enabled");
	}
	
	@Override
	public boolean isEnabled() { return enabled; }

	@Override
	public boolean isRunning() { return running; }

	@Override
	public void start() throws Exception {
		if (running)
			return;
		doStart();
		running = true;
	}

	@Override
	public void stop() {
		if (!running)
			return;
		doStop();
		running = false;
	}

	protected abstract void doStart() throws Exception;
	protected abstract void doStop();
	
	private final boolean enabled;

	private boolean running = false;

}
