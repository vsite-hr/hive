package hr.vsite.hive.services.jetty.faces.bean;

import javax.faces.bean.ManagedBean;
import javax.inject.Inject;

import hr.vsite.hive.HiveConfiguration;

@ManagedBean
public class HelloWorldBean {
	public HelloWorldBean() {
		System.out.println("HelloWorldBean started!");
	}

	@Inject
	public void setConfig(HiveConfiguration conf) {
		this.conf = conf;
	}

	public String getMessage() {
		return "World tickes since 1.1.1970. #" + (System.currentTimeMillis() / 1000);
	}


	public String getSupervisor() {
		return conf.getString("hive.SupervisorAddress");
	}

    public int getCounter() {
        return counter;
    }
 
    public void incrementCounter() {
        counter++;
    }
    
    public void updateTicks() {
    	// NOOP
    }
   
	private HiveConfiguration conf;
	private int counter;
}
