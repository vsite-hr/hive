package hr.vsite.hive;

import org.apache.commons.daemon.Daemon;
import org.apache.commons.daemon.DaemonContext;
import org.apache.commons.daemon.DaemonInitException;

/**
 * Daemon class, managed by procrun/jsvc.
 * See: http://commons.apache.org/proper/commons-daemon/
 */
public class HiveDaemon implements Daemon {

	@Override
	public void init(DaemonContext context) throws DaemonInitException, Exception {
		if (instance != null)
			throw new IllegalStateException("Hive already initialized!");
		instance = Hive.get();
		instance.init();
	}

	@Override
	public void start() throws Exception {
		if (instance == null)
			throw new IllegalStateException("Hive not initialized!");
		instance.start();
	}

	@Override
	public void stop() throws Exception {
		if (instance == null)
			throw new IllegalStateException("Hive not initialized!");
		instance.stop();
	}

	@Override
	public void destroy() {
		if (instance == null)
			throw new IllegalStateException("Hive not initialized!");
		instance.destroy();
	}

	private static Hive instance = null;
	
}
