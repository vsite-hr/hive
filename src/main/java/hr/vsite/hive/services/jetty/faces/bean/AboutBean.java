package hr.vsite.hive.services.jetty.faces.bean;

import javax.faces.bean.ManagedBean;
import javax.inject.Inject;

import hr.vsite.hive.HiveConfiguration;
import hr.vsite.hive.HiveStatus;

@ManagedBean
public class AboutBean {

	public String getSupervisor() {
		return HiveConfiguration.get().getString("hive.SupervisorAddress");
	}

	@Inject
	public void setHiveStatus(HiveStatus hiveStatus) {
		this.hiveStatus = hiveStatus;
	}

	public String getHiveVersion() {
		return hiveStatus.getVersion();
	}

	private HiveStatus hiveStatus;
}
