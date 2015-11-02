package hr.vsite.hive.dao;

import java.sql.Array;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

import javax.inject.Inject;

import hr.vsite.hive.sensors.Sensor;
import hr.vsite.hive.sensors.SensorFilter;
import hr.vsite.hive.ticks.CounterTick;
import hr.vsite.hive.ticks.Tick;
import hr.vsite.hive.ticks.TickFilter;
import hr.vsite.hive.ticks.ToggleTick;
import hr.vsite.hive.ticks.ValueTick;
import hr.vsite.hive.ticks.ValueTickFilter;

/**
 * PostgreSQL interface.
 */
public class PostgresDao extends JdbcDao {

	@Inject
	PostgresDao(Connection conn) {
		super(conn);
	}

	@Override
	public void archive(int daysBack) {
		// TODO
		throw new UnsupportedOperationException();
	}

	@Override
	public void persist(Sensor sensor) {
		if (sensor.getId() == null)
			create(sensor);
		else
			modify(sensor);
	}

	private void create(Sensor sensor) {
	    try (PreparedStatement statement = getConn().prepareStatement("INSERT INTO sensors (sensor_device_id, sensor_name) VALUES (?, ?)", Statement.RETURN_GENERATED_KEYS)) {
		    statement.setString(1, sensor.getDeviceId());
		    statement.setString(2, sensor.getName());
	        statement.execute();
	        try (ResultSet resultSet = statement.getGeneratedKeys()) {
		        if (!resultSet.next())
		        	throw new RuntimeException("Unable to insert new sensor into db!");
	    		sensor.setId(UUID.class.cast(resultSet.getObject("sensor_id")));
	    		sensor.setLastModified(resultSet.getTimestamp("sensor_last_modified").toInstant());
	        }
		} catch (SQLException e) {
			throw new RuntimeException("Unable to create new sensor", e);
		}
	}
	
	private void modify(Sensor sensor) {
	    try (PreparedStatement statement = getConn().prepareStatement("UPDATE sensors SET sensor_name = ? WHERE sensor_id = ?", Statement.RETURN_GENERATED_KEYS)) {
		    statement.setString(1, sensor.getName());
	        statement.execute();
	        try (ResultSet resultSet = statement.getGeneratedKeys()) {
		        if (!resultSet.next())
		        	throw new RuntimeException("Unable to update sensor " + sensor);
	    		sensor.setLastModified(resultSet.getTimestamp("sensor_last_modified").toInstant());
	        }
		} catch (SQLException e) {
			throw new RuntimeException("Unable to modify sensor " + sensor, e);
		}
	}
	
	private Sensor sensorFromResultSet(ResultSet resultSet) {
		Sensor sensor = new Sensor();
		try {
			sensor.setId(UUID.class.cast(resultSet.getObject("sensor_id")));
			sensor.setDeviceId(resultSet.getString("sensor_device_id"));
			sensor.setName(resultSet.getString("sensor_name"));
    		sensor.setLastModified(resultSet.getTimestamp("sensor_last_modified").toInstant());
		} catch (SQLException e) {
			throw new RuntimeException("Unable to resolve sensor from result set", e);
		}
		return sensor;
	}
	
	@Override
	public void delete(Sensor sensor) {
		// TODO
		throw new UnsupportedOperationException();
	}

	@Override
	public Sensor findSensorById(UUID id) {
	    try (PreparedStatement statement = getConn().prepareStatement("SELECT * FROM sensors WHERE sensor_id = ?")) {
		    statement.setObject(1, id);
	        statement.execute();
	        try (ResultSet resultSet = statement.getResultSet()) {
	        	return resultSet.next() ? sensorFromResultSet(resultSet) : null;
	        }
		} catch (SQLException e) {
			throw new RuntimeException("Unable to find sensor with id " + id, e);
		}
	}
	
	@Override
	public Sensor findSensorByDeviceId(String deviceId) {
	    try (PreparedStatement statement = getConn().prepareStatement("SELECT * FROM sensors WHERE sensor_device_id = ?")) {
		    statement.setString(1, deviceId);
	        statement.execute();
	        try (ResultSet resultSet = statement.getResultSet()) {
	        	return resultSet.next() ? sensorFromResultSet(resultSet) : null;
	        }
		} catch (SQLException e) {
			throw new RuntimeException("Unable to find sensor with device id " + deviceId, e);
		}
	}
	
	@Override
	public List<Sensor> listSensors(SensorFilter filter, int count, int offset) {
		List<Sensor> sensors = new ArrayList<Sensor>(count);
		StringBuilder queryBuilder = new StringBuilder(1000);
		queryBuilder.append("SELECT * FROM sensors WHERE true");
		if (filter.getName() != null)
			queryBuilder.append(" AND lower(sensor_name) LIKE '%' || lower(?) || '%'");
		queryBuilder.append(" LIMIT ? OFFSET ?");
	    try (PreparedStatement statement = getConn().prepareStatement(queryBuilder.toString())) {
			int index = 0;
			if (filter.getName() != null)
			    statement.setString(++index, filter.getName());
		    statement.setInt(++index, count);
		    statement.setInt(++index, offset);
	        statement.execute();
	        try (ResultSet resultSet = statement.getResultSet()) {
	        	while(resultSet.next())
	        		sensors.add(sensorFromResultSet(resultSet));
	        }
		} catch (SQLException e) {
			throw new RuntimeException("Unable to list sensors", e);
		}
	    return sensors;
	}

	@Override
	public void persist(ValueTick tick) {
	    try (PreparedStatement statement = getConn().prepareStatement("INSERT INTO ticks_value (tick_created_time, tick_sensor_ordinal, sensor_id, tick_type, tick_value) VALUES (?, ?, ?, ?::tick_type, ?)", Statement.RETURN_GENERATED_KEYS)) {
		    statement.setTimestamp(1, tick.getCreatedTime() != null ? Timestamp.from(tick.getCreatedTime()) : null);
		    if (tick.getSensorOrdinal() == null)
		    	statement.setNull(2, Types.INTEGER);
		    else
		    	statement.setInt(2, tick.getSensorOrdinal());
	    	statement.setObject(3, tick.getSensor().getId());
	    	statement.setString(4, tick.getType().toString());
	    	statement.setInt(5, tick.getValue());
	        statement.execute();
	        try (ResultSet resultSet = statement.getGeneratedKeys()) {
		        if (!resultSet.next())
		        	throw new RuntimeException("Unable to insert new value tick into db!");
	    		tick.setId(UUID.class.cast(resultSet.getObject("tick_id")));
	    		tick.setReceivedTime(resultSet.getTimestamp("tick_received_time").toInstant());
	    		Integer metaOrdinal = resultSet.getInt("tick_meta_ordinal");
	    		if (!resultSet.wasNull())
	    			tick.setMetaOrdinal(metaOrdinal);
	        }
		} catch (SQLException e) {
			throw new RuntimeException("Unable to create new value tick", e);
		}
	}

	@Override
	public void persist(CounterTick tick) {
		// TODO
		throw new UnsupportedOperationException();
	}

	@Override
	public void persist(ToggleTick tick) {
		// TODO
		throw new UnsupportedOperationException();
	}

	@Override
	public List<Tick> listTicks(TickFilter filter, int count, int offset) {
		List<Tick> ticks = new ArrayList<Tick>(count);
		StringBuilder queryBuilder = new StringBuilder(1000);
		queryBuilder.append("SELECT * FROM ticks WHERE true");
		appendWhere(filter, queryBuilder);
		queryBuilder.append(" LIMIT ? OFFSET ?");
	    try (PreparedStatement statement = getConn().prepareStatement(queryBuilder.toString())) {
			int index = appendParams(filter, statement);
		    statement.setInt(++index, count);
		    statement.setInt(++index, offset);
	        statement.execute();
	        try (ResultSet resultSet = statement.getResultSet()) {
	        	while(resultSet.next())
	        		ticks.add(tickFromResultSet(resultSet));
	        }
		} catch (SQLException e) {
			throw new RuntimeException("Unable to list ticks", e);
		}
	    return ticks;
	}

	@Override
	public List<ValueTick> listTicks(ValueTickFilter filter, int count, int offset) {
		List<ValueTick> ticks = new ArrayList<ValueTick>(count);
		StringBuilder queryBuilder = new StringBuilder(1000);
		queryBuilder.append("SELECT * FROM ticks_value WHERE true");
		appendWhere(filter, queryBuilder);
		if (filter.getValueFrom() != null)
			queryBuilder.append(" AND tick_value >= ?");
		if (filter.getValueTo() != null)
			queryBuilder.append(" AND tick_value < ?");
		queryBuilder.append(" LIMIT ? OFFSET ?");
	    try (PreparedStatement statement = getConn().prepareStatement(queryBuilder.toString())) {
			int index = appendParams(filter, statement);
			if (filter.getValueFrom() != null)
			    statement.setInt(++index, filter.getValueFrom());
			if (filter.getValueTo() != null)
			    statement.setInt(++index, filter.getValueTo());
		    statement.setInt(++index, count);
		    statement.setInt(++index, offset);
	        statement.execute();
	        try (ResultSet resultSet = statement.getResultSet()) {
	        	while(resultSet.next())
	        		ticks.add(valueTickFromResultSet(resultSet));
	        }
		} catch (SQLException e) {
			throw new RuntimeException("Unable to list value ticks", e);
		}
	    return ticks;
	}
	
	private void appendWhere(TickFilter filter, StringBuilder queryBuilder) {
		if (filter.getCreatedAfter() != null)
			queryBuilder.append(" AND tick_created_time >= ?");
		if (filter.getCreatedBefore() != null)
			queryBuilder.append(" AND tick_created_time < ?");
		if (filter.getReceivedAfter() != null)
			queryBuilder.append(" AND tick_received_time >= ?");
		if (filter.getReceivedBefore() != null)
			queryBuilder.append(" AND tick_received_time < ?");
		if (filter.getMetaOrdinalFrom() != null)
			queryBuilder.append(" AND tick_meta_ordinal >= ?");
		if (filter.getMetaOrdinalTo() != null)
			queryBuilder.append(" AND tick_meta_ordinal < ?");
		if (filter.getSensorOrdinalFrom() != null)
			queryBuilder.append(" AND tick_sensor_ordinal >= ?");
		if (filter.getSensorOrdinalTo() != null)
			queryBuilder.append(" AND tick_sensor_ordinal < ?");
		if (filter.getSensors() != null)
			queryBuilder.append(" AND sensor_id = ANY(?)");
		if (filter.getTypes() != null)
			queryBuilder.append(" AND tick_type = ANY(?)");
	}

	private int appendParams(TickFilter filter, PreparedStatement statement) throws SQLException {
		int index = 0;
		if (filter.getCreatedAfter() != null)
		    statement.setTimestamp(++index, Timestamp.from(filter.getCreatedAfter()));
		if (filter.getCreatedBefore() != null)
		    statement.setTimestamp(++index, Timestamp.from(filter.getCreatedBefore()));
		if (filter.getReceivedAfter() != null)
		    statement.setTimestamp(++index, Timestamp.from(filter.getReceivedAfter()));
		if (filter.getReceivedBefore() != null)
		    statement.setTimestamp(++index, Timestamp.from(filter.getReceivedBefore()));
		if (filter.getMetaOrdinalFrom() != null)
		    statement.setInt(++index, filter.getMetaOrdinalFrom());
		if (filter.getMetaOrdinalTo() != null)
		    statement.setInt(++index, filter.getMetaOrdinalTo());
		if (filter.getSensorOrdinalFrom() != null)
		    statement.setInt(++index, filter.getSensorOrdinalFrom());
		if (filter.getSensorOrdinalTo() != null)
		    statement.setInt(++index, filter.getSensorOrdinalTo());
		if (filter.getSensors() != null)
		    statement.setArray(++index, sensorsArray(filter.getSensors()));
		if (filter.getTypes() != null)
		    statement.setArray(++index, getConn().createArrayOf("tick_type", new ArrayList<Tick.Type>(filter.getTypes()).toArray()));
		return index;
	}
	
	private Array sensorsArray(Collection<Sensor> sensors) throws SQLException {
		UUID[] ids = new UUID[sensors.size()];
		int index = 0;
		for (Sensor sensor : sensors)
			ids[index++] = sensor.getId();
		return getConn().createArrayOf("uuid", ids);
	}

	private Tick tickFromResultSet(ResultSet resultSet) {
		Tick tick = new Tick();
		try {
			tickFromResultSet(tick, resultSet);
		} catch (SQLException e) {
			throw new RuntimeException("Unable to resolve sensor from result set", e);
		}
		return tick;
	}

	private ValueTick valueTickFromResultSet(ResultSet resultSet) {
		ValueTick tick = new ValueTick();
		try {
			tickFromResultSet(tick, resultSet);
			tick.setValue(resultSet.getInt("tick_value"));
		} catch (SQLException e) {
			throw new RuntimeException("Unable to resolve sensor from result set", e);
		}
		return tick;
	}

	private void tickFromResultSet(Tick tick, ResultSet resultSet) throws SQLException {
		tick.setId(UUID.class.cast(resultSet.getObject("tick_id")));
		Timestamp createdTime = resultSet.getTimestamp("tick_created_time");
		if (!resultSet.wasNull())
			tick.setCreatedTime(createdTime.toInstant());
		tick.setReceivedTime(resultSet.getTimestamp("tick_received_time").toInstant());
		int metaOrdinal = resultSet.getInt("tick_meta_ordinal");
		if (!resultSet.wasNull())
			tick.setMetaOrdinal(metaOrdinal);
		int sensorOrdinal = resultSet.getInt("tick_sensor_ordinal");
		if (!resultSet.wasNull())
			tick.setSensorOrdinal(sensorOrdinal);
		tick.setSensor(findSensorById(UUID.class.cast(resultSet.getObject("sensor_id"))));
		tick.setType(Tick.Type.valueOf(resultSet.getString("tick_type")));
	}
	
}
