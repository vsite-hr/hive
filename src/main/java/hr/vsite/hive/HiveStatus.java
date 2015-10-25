package hr.vsite.hive;

import javax.inject.Inject;

public class HiveStatus {

	@Inject
	HiveStatus(HiveProperties props) {
		this.version = props.getVersion();
	}

	public String getVersion() { return version; }

	private final String version;
	
}
