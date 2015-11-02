package hr.vsite.hive.services.jetty.rest.v1.jaxb;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import hr.vsite.hive.sensors.Sensor;

@XmlRootElement(name = "sensor")
@XmlAccessorType(XmlAccessType.NONE)
@XmlType(propOrder={
	"id",
	"deviceId",
	"name"
})
public class JAXBSensor {

	public static List<JAXBSensor> wrap(List<Sensor> sensors) {
		List<JAXBSensor> jaxbSensors = new ArrayList<JAXBSensor>(sensors.size());
		for (Sensor sensor : sensors)
			jaxbSensors.add(new JAXBSensor(sensor));
		return jaxbSensors;
	}
	
	public JAXBSensor() { throw new UnsupportedOperationException(); }

	public JAXBSensor(Sensor sensor) {
		this.sensor = sensor;
	}

	@XmlAttribute
	public UUID getId() { return sensor.getId(); }
	
	@XmlElement
	public String getDeviceId() { return sensor.getDeviceId(); }
	
	@XmlElement
	public String getName() { return sensor.getName(); }

	private final Sensor sensor;
	
}
