package hr.vsite.hive.services.jetty.rest.v1.param;

import java.util.UUID;

import javax.inject.Inject;
import javax.ws.rs.ext.ParamConverter;

import org.apache.commons.lang3.StringUtils;

import hr.vsite.hive.sensors.Sensor;
import hr.vsite.hive.sensors.SensorManager;

public class SensorParamConverter implements ParamConverter<Sensor> {

	@Inject
	SensorParamConverter(SensorManager sensorManager) {
		this.sensorManager = sensorManager;
	}
	
	@Override
	public Sensor fromString(String id) {

		if (StringUtils.isBlank(id))
			return null;
		
		Sensor sensor = sensorManager.findById(UUID.fromString(id));
		if (sensor == null)
			throw new IllegalArgumentException(id);
		
		return sensor;

	}

	@Override
	public String toString(Sensor sensor) {
		if (sensor == null)
			return null;
		return sensor.getId().toString();
	}

	private final SensorManager sensorManager;
	
}
