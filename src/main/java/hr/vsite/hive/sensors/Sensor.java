package hr.vsite.hive.sensors;

import java.time.Instant;

import java.util.UUID;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class Sensor implements Comparable<Sensor> {

	/** Hive's ID of this Sensor */
	public UUID getId() { return id; }
	public void setId(UUID id) { this.id = id; }
	
	/** Sensor's own ID */
	public String getDeviceId() { return deviceId; }
	public void setDeviceId(String deviceId) { this.deviceId = deviceId; }
	
	/** Sensor's name */
	public String getName() { return name; }
	public void setName(String name) { this.name = name; }

	/** Last modification time */
	public Instant getLastModified() { return lastModified; }
	public void setLastModified(Instant lastModified) { this.lastModified = lastModified; }
	
	@Override
	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
			.append("id", id)
			.append("deviceId", deviceId)
			.append("name", name)
			.toString();
	}

	@Override
	public int compareTo(Sensor o) {
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
		Sensor other = (Sensor) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	private UUID id;
	private String deviceId;
	private String name;
	private Instant lastModified;

}
