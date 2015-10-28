package hr.vsite.hive.services.jetty.rest.v1.resources;

import java.time.Instant;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import javax.inject.Inject;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import hr.vsite.hive.sensors.Sensor;
import hr.vsite.hive.sensors.SensorManager;
import hr.vsite.hive.services.jetty.rest.v1.jaxb.JAXBTick;
import hr.vsite.hive.services.jetty.rest.v1.jaxb.JAXBValueTick;
import hr.vsite.hive.ticks.Tick;
import hr.vsite.hive.ticks.TickFilter;
import hr.vsite.hive.ticks.TickManager;
import hr.vsite.hive.ticks.ValueTick;
import hr.vsite.hive.ticks.ValueTickFilter;

@Path("tick")
public class TickResource {

	@Inject
	public TickResource(TickManager tickManager, SensorManager sensorManager) {
		this.tickManager = tickManager;
		this.sensorManager = sensorManager;
	}
	
	@GET
	@Path("")
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public List<JAXBTick> list(
		@QueryParam("createdAfter") Instant createdAfter,
		@QueryParam("createdBefore") Instant createdBefore,
		@QueryParam("receivedAfter") Instant receivedAfter,
		@QueryParam("receivedBefore") Instant receivedBefore,
		@QueryParam("metaOrdinalFrom") Integer metaOrdinalFrom,
		@QueryParam("metaOrdinalTo") Integer metaOrdinalTo,
		@QueryParam("sensorOrdinalFrom") Integer sensorOrdinalFrom,
		@QueryParam("sensorOrdinalTo") Integer sensorOrdinalTo,
		@QueryParam("sensor") Set<Sensor> sensors,
		@QueryParam("type") Set<Tick.Type> types,
		@DefaultValue("10") @QueryParam("count") int count,
		@DefaultValue("0") @QueryParam("offset") int offset
	) {
		TickFilter filter = new TickFilter();
		filter.setCreatedAfter(createdAfter);
		filter.setCreatedBefore(createdBefore);
		filter.setReceivedAfter(receivedAfter);
		filter.setReceivedBefore(receivedBefore);
		filter.setMetaOrdinalFrom(metaOrdinalFrom);
		filter.setMetaOrdinalTo(metaOrdinalTo);
		filter.setSensorOrdinalFrom(sensorOrdinalFrom);
		filter.setSensorOrdinalTo(sensorOrdinalTo);
		filter.setSensors(sensors != null && !sensors.isEmpty() ? sensors : null);
		filter.setTypes(types != null && !types.isEmpty() ? types : null);
		return JAXBTick.wrap(tickManager.list(filter, count, offset));
	}
   
	@GET
	@Path("value")
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public List<JAXBValueTick> listValue(
		@QueryParam("createdAfter") Instant createdAfter,
		@QueryParam("createdBefore") Instant createdBefore,
		@QueryParam("receivedAfter") Instant receivedAfter,
		@QueryParam("receivedBefore") Instant receivedBefore,
		@QueryParam("metaOrdinalFrom") Integer metaOrdinalFrom,
		@QueryParam("metaOrdinalTo") Integer metaOrdinalTo,
		@QueryParam("sensorOrdinalFrom") Integer sensorOrdinalFrom,
		@QueryParam("sensorOrdinalTo") Integer sensorOrdinalTo,
		@QueryParam("sensor") Set<Sensor> sensors,
		@QueryParam("valueFrom") Integer valueFrom,
		@QueryParam("valueTo") Integer valueTo,
		@DefaultValue("10") @QueryParam("count") int count,
		@DefaultValue("0") @QueryParam("offset") int offset
	) {
		ValueTickFilter filter = new ValueTickFilter();
		filter.setCreatedAfter(createdAfter);
		filter.setCreatedBefore(createdBefore);
		filter.setReceivedAfter(receivedAfter);
		filter.setReceivedBefore(receivedBefore);
		filter.setMetaOrdinalFrom(metaOrdinalFrom);
		filter.setMetaOrdinalTo(metaOrdinalTo);
		filter.setSensorOrdinalFrom(sensorOrdinalFrom);
		filter.setSensorOrdinalTo(sensorOrdinalTo);
		filter.setSensors(sensors != null && !sensors.isEmpty() ? sensors : null);
		filter.setTypes(Collections.singleton(Tick.Type.Value));
		filter.setValueFrom(valueFrom);
		filter.setValueTo(valueTo);
		return JAXBValueTick.wrapValue(tickManager.list(filter, count, offset));
	}

	@PUT
	@Path("value")
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public JAXBValueTick createValue(
		@QueryParam("time") Instant createdTime,
		@QueryParam("ordinal") Integer sensorOrdinal,
		@QueryParam("id") UUID id,
		@QueryParam("device_id") String deviceId,
		@QueryParam("value") Integer value
	) {
		
		Sensor sensor;
		if (id != null) {
			sensor = sensorManager.findById(id);
			if (sensor == null)
				throw new BadRequestException("Unknown sensor ID \"" + id + "\"");
		} else if (deviceId != null) {
			sensor = sensorManager.findByDeviceId(deviceId);
			if (sensor == null) {
				// first tick from new sensor, create it
				sensor = new Sensor();
				sensor.setDeviceId(deviceId);
				sensorManager.create(sensor);
			}
		} else
			throw new BadRequestException("Missing (device) ID");

		if (value == null)
			throw new BadRequestException("Missing value");
		
		ValueTick tick = new ValueTick();
		tick.setCreatedTime(createdTime);
		tick.setSensorOrdinal(sensorOrdinal);
		tick.setSensor(sensor);
		tick.setValue(value);
		
		tickManager.createValue(tick);
		
		return new JAXBValueTick(tick);
		
	}

	private final TickManager tickManager;
	private final SensorManager sensorManager;
    
}