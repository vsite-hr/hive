package hr.vsite.hive;

public class HiveStatus {

	HiveStatus() {
		this.version = HiveProperties.get().getVersion();
	}

	public String getVersion() { return version; }

	private final String version;
	
}
