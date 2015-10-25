package hr.vsite.hive.services.jetty.rest.v1.jaxb;

import javax.xml.bind.annotation.XmlRootElement;

import hr.vsite.hive.HiveStatus;

@XmlRootElement(name = "status") 
public class JAXBHiveStatus {

	JAXBHiveStatus() { throw new UnsupportedOperationException(); }

	public JAXBHiveStatus(HiveStatus status) {
		this.status = status;
	}

	public String getVersion() { return status.getVersion(); }
	public void setVersion(String version) { throw new UnsupportedOperationException(); }

	private final HiveStatus status;
	
}
