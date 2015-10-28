package hr.vsite.hive.services.jetty.rest.v1.jaxb;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import hr.vsite.hive.ticks.ValueTick;

@XmlRootElement(name = "valueTick") 
@XmlAccessorType(XmlAccessType.NONE)
//@XmlType(propOrder={	// TODO should concatenate on JAXBTick properties
//	"value"
//})
public class JAXBValueTick extends JAXBTick {

	public static List<JAXBValueTick> wrapValue(List<ValueTick> ticks) {
		List<JAXBValueTick> jaxbTicks = new ArrayList<JAXBValueTick>(ticks.size());
		for (ValueTick tick : ticks)
			jaxbTicks.add(new JAXBValueTick(tick));
		return jaxbTicks;
	}
	
	public JAXBValueTick() { throw new UnsupportedOperationException(); }

	public JAXBValueTick(ValueTick tick) {
		super(tick);
		this.tick = tick;
	}

	@XmlElement
	public int getValue() { return tick.getValue(); }

	private final ValueTick tick;
	
}
