package hr.vsite.hive.ticks;

public class CounterTickEvent extends TickEvent<CounterTick> {

	public CounterTickEvent(Object source, CounterTick tick) {
		super(source, tick);
	}
	
	private static final long serialVersionUID = 1L;

}
