package hr.vsite.hive.sensors;

import java.util.EventObject;

public class SensorCreatedEvent extends EventObject {

	public SensorCreatedEvent(Object source, Sensor sensor) {
		super(source);
		this.sensor = sensor;
	}
	
	public Sensor getSensor() { return sensor; }

	private static final long serialVersionUID = 1L;

	private final Sensor sensor;
	
}
