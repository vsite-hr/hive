package hr.vsite.hive.services.jetty.rest.v1.jaxb;

import java.time.Instant;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import hr.vsite.hive.ticks.Tick;

@XmlRootElement(name = "tick") 
@XmlAccessorType(XmlAccessType.NONE)
@XmlType(propOrder={
	"id",
	"createdTime",
	"receivedTime",
	"metaOrdinal",
	"sensorOrdinal",
	"sensor",
	"type"
})
public class JAXBTick {

	public static List<JAXBTick> wrap(List<Tick> ticks) {
		List<JAXBTick> jaxbTicks = new ArrayList<JAXBTick>(ticks.size());
		for (Tick tick : ticks)
			jaxbTicks.add(new JAXBTick(tick));
		return jaxbTicks;
	}
	
	public JAXBTick() { throw new UnsupportedOperationException(); }

	public JAXBTick(Tick tick) {
		this.tick = tick;
	}

	@XmlAttribute
	public UUID getId() { return tick.getId(); }
	
	@XmlElement
	@XmlJavaTypeAdapter(InstantAdapter.class)
	public Instant getCreatedTime() { return tick.getCreatedTime(); }
	
	@XmlElement
	@XmlJavaTypeAdapter(InstantAdapter.class)
	public Instant getReceivedTime() { return tick.getReceivedTime(); }
	
	@XmlElement
	public Integer getMetaOrdinal() { return tick.getMetaOrdinal(); }
	
	@XmlElement
	public Integer getSensorOrdinal() { return tick.getSensorOrdinal(); }

	@XmlElement
	public JAXBSensor getSensor() { return new JAXBSensor(tick.getSensor()); }

	@XmlElement
	public Tick.Type getType() { return tick.getType(); }

	private final Tick tick;
	
}
