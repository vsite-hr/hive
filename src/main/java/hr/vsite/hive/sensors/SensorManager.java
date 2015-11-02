package hr.vsite.hive.sensors;

import java.util.List;
import java.util.UUID;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import hr.vsite.hive.HiveEventBus;
import hr.vsite.hive.dao.HiveDao;

public class SensorManager {

	@Inject
	public SensorManager(HiveDao dao, HiveEventBus eventBus) {
		this.dao = dao;
		this.eventBus = eventBus;
	}

	public void create(Sensor sensor) {
		dao.persist(sensor);
		eventBus.post(new SensorCreatedEvent(this, sensor));
		log.info("Created {}", sensor);
	}

	public void modify(Sensor sensor) {
		dao.persist(sensor);
		eventBus.post(new SensorModifiedEvent(this, sensor));
		log.info("Modified {}", sensor);
	}

	public void delete(Sensor sensor) {
		dao.delete(sensor);
		eventBus.post(new SensorDeletedEvent(this, sensor));
		log.info("Deleted {}", sensor);
	}

	public Sensor findById(UUID id) {
		return dao.findSensorById(id);
	}
	
	public Sensor findByDeviceId(String deviceId) {
		return dao.findSensorByDeviceId(deviceId);
	}

	public List<Sensor> list(SensorFilter filter, int count, int offset) {
		return dao.listSensors(filter, count, offset);
	}

	private static final Logger log = LoggerFactory.getLogger(SensorManager.class);

	private final HiveDao dao;
	private final HiveEventBus eventBus;
	
}
