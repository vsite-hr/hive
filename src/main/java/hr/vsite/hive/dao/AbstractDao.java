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

public abstract class AbstractDao implements HiveDao {

	AbstractDao() {}
	
	@Override
	public void close() throws Exception {}

	@Override
	public void commit() {}

	@Override
	public void rollback() {}

	@Override
	public void archive(int daysBack) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void persist(Sensor sensor) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void delete(Sensor sensor) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Sensor findSensorById(UUID id) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Sensor findSensorByDeviceId(String deviceId) {
		throw new UnsupportedOperationException();
	}

	@Override
	public List<Sensor> listSensors(SensorFilter filter, int count, int offset) {
		throw new UnsupportedOperationException();
	}

	@Override
	public List<Tick> listTicks(TickFilter filter, int count, int offset) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void persist(ValueTick tick) {
		throw new UnsupportedOperationException();
	}

	@Override
	public List<ValueTick> listTicks(ValueTickFilter filter, int count, int offset) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void persist(CounterTick tick) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void persist(ToggleTick tick) {
		throw new UnsupportedOperationException();
	}

}
