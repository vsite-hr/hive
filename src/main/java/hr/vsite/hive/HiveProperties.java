package hr.vsite.hive;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Main Hive properties.
 * Since it is used in configuring Guice DI, it can not be itself managed by Guice.
 */
public class HiveProperties extends Properties {

	public static HiveProperties get() {
		if (instance == null)
			instance = new HiveProperties();
		return instance;
	}
	
	private HiveProperties() {

		super();

    	try (InputStream propertiesStream = ClassLoader.getSystemResourceAsStream("hive.properties")) {
			load(propertiesStream);
			version = getProperty("version");
		} catch (IOException e) {
			throw new RuntimeException("Could not load properties", e);
		}

	}

	public String getVersion() { return version; }

	private static final long serialVersionUID = 1L;
	private static HiveProperties instance = null;
	
	private final String version;

}
