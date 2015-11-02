package hr.vsite.hive.ticks;

import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

import hr.vsite.hive.sensors.Sensor;

public class TickFilter {

	public Instant getCreatedAfter() { return createdAfter; }
	public void setCreatedAfter(Instant createdAfter) { this.createdAfter = createdAfter; }

	public Instant getCreatedBefore() { return createdBefore; }
	public void setCreatedBefore(Instant createdBefore) { this.createdBefore = createdBefore; }
	
	public Instant getReceivedAfter() { return receivedAfter; }
	public void setReceivedAfter(Instant receivedAfter) { this.receivedAfter = receivedAfter; }
	
	public Instant getReceivedBefore() { return receivedBefore; }
	public void setReceivedBefore(Instant receivedBefore) { this.receivedBefore = receivedBefore; }
	
	public Integer getMetaOrdinalFrom() { return metaOrdinalFrom; }
	public void setMetaOrdinalFrom(Integer metaOrdinalFrom) { this.metaOrdinalFrom = metaOrdinalFrom; }
	
	public Integer getMetaOrdinalTo() { return metaOrdinalTo; }
	public void setMetaOrdinalTo(Integer metaOrdinalTo) { this.metaOrdinalTo = metaOrdinalTo; }
	
	public Integer getSensorOrdinalFrom() { return sensorOrdinalFrom; }
	public void setSensorOrdinalFrom(Integer sensorOrdinalFrom) { this.sensorOrdinalFrom = sensorOrdinalFrom; }
	
	public Integer getSensorOrdinalTo() { return sensorOrdinalTo; }
	public void setSensorOrdinalTo(Integer sensorOrdinalTo) { this.sensorOrdinalTo = sensorOrdinalTo; }
	
	public Set<Sensor> getSensors() { return sensors; }
	public void setSensors(Set<Sensor> sensors) { this.sensors = sensors; }
	public void addSensor(Sensor sensor) {
		if (sensors == null)
			sensors = new HashSet<Sensor>();
		sensors.add(sensor);
	}

	public Set<Tick.Type> getTypes() { return types; }
	public void setTypes(Set<Tick.Type> types) { this.types = types; }
	public void addType(Tick.Type type) {
		if (types == null)
			types = new HashSet<Tick.Type>();
		types.add(type);
	}

	private Instant createdAfter;
	private Instant createdBefore;
	private Instant receivedAfter;
	private Instant receivedBefore;
	private Integer metaOrdinalFrom;
	private Integer metaOrdinalTo;
	private Integer sensorOrdinalFrom;
	private Integer sensorOrdinalTo;
	private Set<Sensor> sensors;
	private Set<Tick.Type> types;
	
}
