package hr.vsite.hive.dao;

import java.util.Collections;
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

/**
 * Void DAO implementation, does not store or return anything.
 * 
 * Useful for benchmarking other parts of app.
 * 
 */
public class NullDao extends AbstractDao {

	NullDao() {}

	@Override
	public void commit() {}

	@Override
	public void rollback() {}

	@Override
	public void close() throws Exception {}

	@Override
	public void archive(int daysBack) {}

	@Override
	public void persist(Sensor sensor) {}

	@Override
	public void delete(Sensor sensor) {}

	@Override
	public Sensor findSensorById(UUID id) { return null; }

	@Override
	public Sensor findSensorByDeviceId(String deviceId) { return null; }

	@Override
	public List<Sensor> listSensors(SensorFilter filter, int count, int offset) { return Collections.emptyList(); }
	
	@Override
	public void persist(ValueTick tick) {}

	@Override
	public void persist(CounterTick tick) {}

	@Override
	public void persist(ToggleTick tick) {}

	@Override
	public List<Tick> listTicks(TickFilter filter, int count, int offset) { return Collections.emptyList(); }

	@Override
	public List<ValueTick> listTicks(ValueTickFilter filter, int count, int offset) { return Collections.emptyList(); }

}
