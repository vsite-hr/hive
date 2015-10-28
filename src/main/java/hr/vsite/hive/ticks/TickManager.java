package hr.vsite.hive.ticks;

import java.util.List;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import hr.vsite.hive.HiveEventBus;
import hr.vsite.hive.dao.HiveDao;

public class TickManager {

	@Inject
	public TickManager(HiveDao dao, HiveEventBus eventBus) {
		this.dao = dao;
		this.eventBus = eventBus;
	}

	public void createValue(ValueTick tick) {
		dao.persist(tick);
		eventBus.post(new ValueTickEvent(this, tick));
		log.debug("Created {}", tick);
	}

	public List<Tick> list(TickFilter filter, int count, int offset) {
		return dao.listTicks(filter, count, offset);
	}

	public List<ValueTick> list(ValueTickFilter filter, int count, int offset) {
		return dao.listTicks(filter, count, offset);
	}

	private static final Logger log = LoggerFactory.getLogger(TickManager.class);

	private final HiveDao dao;
	private final HiveEventBus eventBus;
	
}
