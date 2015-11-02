package hr.vsite.hive.services.jetty.rest.v1.resources;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import hr.vsite.hive.sensors.Sensor;
import hr.vsite.hive.sensors.SensorFilter;
import hr.vsite.hive.sensors.SensorManager;
import hr.vsite.hive.services.jetty.rest.v1.jaxb.JAXBSensor;

@Path("sensor")
public class SensorResource {

	@Inject
	public SensorResource(SensorManager sensorManager) {
		this.sensorManager = sensorManager;
	}
	
	@GET
	@Path("")
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public List<JAXBSensor> list(
		@QueryParam("name") String name,
		@DefaultValue("10") @QueryParam("count") int count,
		@DefaultValue("0") @QueryParam("offset") int offset
	) {
		SensorFilter filter = new SensorFilter();
		filter.setName(name);
		return JAXBSensor.wrap(sensorManager.list(filter, count, offset));
	}
   
	@GET
	@Path("{id}")
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public JAXBSensor sensor(@PathParam("id") Sensor sensor) {
		return new JAXBSensor(sensor);
	}

	private final SensorManager sensorManager;
    
}