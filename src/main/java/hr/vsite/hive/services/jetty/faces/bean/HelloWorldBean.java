package hr.vsite.hive.services.jetty.faces.bean;

import javax.faces.bean.ManagedBean;
import javax.inject.Inject;

import hr.vsite.hive.HiveStatus;

@ManagedBean
public class HelloWorldBean {
	public HelloWorldBean() {
		System.out.println("HelloWorldBean started!");
	}

	@Inject
	public void setHiveStatus(HiveStatus hiveStatus) {
		this.hiveStatus = hiveStatus;
	}

	public String getHiveVersion() {
		return hiveStatus.getVersion();
	}

	public String getMessage() {
		return "World ticks since 1.1.1970. #" + (System.currentTimeMillis() / 1000);
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
   
	private HiveStatus hiveStatus;
	private int counter;
}
