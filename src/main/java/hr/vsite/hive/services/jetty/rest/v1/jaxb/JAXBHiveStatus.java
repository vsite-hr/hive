package hr.vsite.hive.services.jetty.rest.v1.jaxb;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import hr.vsite.hive.HiveStatus;

@XmlRootElement(name = "status") 
@XmlAccessorType(XmlAccessType.NONE)
public class JAXBHiveStatus {

	JAXBHiveStatus() { throw new UnsupportedOperationException(); }

	public JAXBHiveStatus(HiveStatus status) {
		this.status = status;
	}

	@XmlElement
	public String getVersion() { return status.getVersion(); }
	public void setVersion(String version) { throw new UnsupportedOperationException(); }

	private final HiveStatus status;
	
}
