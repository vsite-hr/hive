package hr.vsite.hive;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.inject.Singleton;

@Singleton
public class HiveProperties extends Properties {

	HiveProperties() {

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

	private final String version;

}
