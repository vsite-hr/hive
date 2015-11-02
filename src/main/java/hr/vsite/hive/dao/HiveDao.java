package hr.vsite.hive.dao;

import java.util.List;
import java.util.UUID;

import hr.vsite.hive.sensors.Sensor;
import hr.vsite.hive.sensors.SensorFilter;
import hr.vsite.hive.ticks.CounterTick;
import hr.vsite.hive.ticks.Tick;
import hr.vsite.hive.ticks.TickFilter;
import hr.vsite.hive.ticks.ToggleTick;
import hr.vsite.hive.ticks.ValueTick;
import hr.vsite.hive.ticks.ValueTickFilter;

public interface HiveDao extends AutoCloseable {

	void commit();
	
	void rollback();
	
	void archive(int daysBack);
	
	void persist(Sensor sensor);

	void delete(Sensor sensor);

	Sensor findSensorById(UUID id);
	
	Sensor findSensorByDeviceId(String deviceId);
	
	List<Sensor> listSensors(SensorFilter filter, int count, int offset);

	void persist(ValueTick tick);

	List<Tick> listTicks(TickFilter filter, int count, int offset);

	void persist(CounterTick tick);

	List<ValueTick> listTicks(ValueTickFilter filter, int count, int offset);

	void persist(ToggleTick tick);

}
