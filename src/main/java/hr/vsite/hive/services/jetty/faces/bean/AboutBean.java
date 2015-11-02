package hr.vsite.hive.services.jetty.faces.bean;

import javax.faces.bean.ManagedBean;
import javax.inject.Inject;

import hr.vsite.hive.HiveConfiguration;

@ManagedBean
public class AboutBean {

	@Inject
	public void setConfig(HiveConfiguration conf) {
		this.conf = conf;
	}

	public String getSupervisor() {
		return conf.getString("hive.SupervisorAddress");
	}
   
	private HiveConfiguration conf;
}
