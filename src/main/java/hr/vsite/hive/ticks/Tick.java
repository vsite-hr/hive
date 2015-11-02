package hr.vsite.hive.ticks;

import java.time.Instant;
import java.util.UUID;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import hr.vsite.hive.sensors.Sensor;

/**
 * Base class for all Ticks, providing common properties.
 */
public class Tick implements Comparable<Tick> {

	public static enum Type {
		Value,
		Counter,
		Toggle,
		Geo,
		Log
		//Image	// TODO brainstorm this further (some sort of binary blob with content type?)
	}
	
	/** Hive's ID of this Tick */
	public UUID getId() { return id; }
	public void setId(UUID id) { this.id = id; }
	
	/** Instant when this Tick was created. Determined by sensor, may be <code>null</code>. */
	public Instant getCreatedTime() { return createdTime; }
	public void setCreatedTime(Instant createdTime) { this.createdTime = createdTime; }
	
	/** Instant when this Tick was received by Hive. */
	public Instant getReceivedTime() { return receivedTime; }
	public void setReceivedTime(Instant receivedTime) { this.receivedTime = receivedTime; }
	
	/** Major part of MetaOrdinal/SensorOrdinal pair that together define order of thicks received from Sensor.
	 * MetaOrdinal never turns around. */
	public Integer getMetaOrdinal() { return metaOrdinal; }
	public void setMetaOrdinal(Integer metaOrdinal) { this.metaOrdinal = metaOrdinal; }
	
	/** Minor part of MetaOrdinal/SensorOrdinal pair that together define order of thicks received from Sensor.
	 * SensorOrdinal turns around to zero at sensor-specific values. */
	public Integer getSensorOrdinal() { return sensorOrdinal; }
	public void setSensorOrdinal(Integer sensorOrdinal) { this.sensorOrdinal = sensorOrdinal; }

	/** {@link Sensor} that produced this Thick */
	public Sensor getSensor() { return sensor; }
	public void setSensor(Sensor sensor) { this.sensor = sensor; }

	/** Type of tick */
	public Tick.Type getType() { return type; }
	public void setType(Tick.Type type) { this.type = type; }
	
	protected ToStringBuilder toStringBuilder() {
		return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
			.append("id", id)
			;
	}
	
	@Override
	public int compareTo(Tick o) {
		return id.compareTo(o.getId());
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Tick other = (Tick) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	private UUID id;
	private Instant createdTime;
	private Instant receivedTime;
	private Integer metaOrdinal;
	private Integer sensorOrdinal;
	private Sensor sensor;
	private Tick.Type type;

}
