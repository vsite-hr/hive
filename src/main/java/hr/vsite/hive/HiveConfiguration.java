package hr.vsite.hive;

import java.nio.file.FileSystems;

import java.nio.file.Path;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.XMLConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Main Hive configuration.
 * Since it is used in configuring Guice DI, it can not be itself managed by Guice.
 */
public class HiveConfiguration extends XMLConfiguration {

	public static HiveConfiguration get() {
		if (instance == null)
			instance = new HiveConfiguration();
		return instance;
	}
	
	private HiveConfiguration() {
    	
		super();
        
		try {
			setThrowExceptionOnMissing(true);
			setDelimiterParsingDisabled(true);
			load("hive.xml");
            log.info("Configuration loaded from {}", getFile().getAbsolutePath());
        } catch (ConfigurationException e) {
            throw new RuntimeException("Could not load configuration", e);
        }
		
		dataPath = FileSystems.getDefault().getPath(getString("hive.DataPath"));
		log.info("Data path is {}", dataPath);

		supervisorAddress = getString("hive.SupervisorAddress");
		log.info("Supervisor address is {} (hopefully we won't interrupt him/her a lot :) )", supervisorAddress);


	}

    public Path getDataPath() { return dataPath; }
    public Path getSubDataPath(String subfolder) { return dataPath.resolve(subfolder); }
	public String getSupervisorAddress() { return supervisorAddress; }
	
    private static final Logger log = LoggerFactory.getLogger(HiveConfiguration.class);
	private static final long serialVersionUID = 1L;
	private static HiveConfiguration instance = null;
	
    private final Path dataPath;
    private final String supervisorAddress;

}
