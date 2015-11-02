package hr.vsite.hive.ticks;

import java.util.EventObject;

public abstract class TickEvent<T extends Tick> extends EventObject {

	public TickEvent(Object source, T tick) {
		super(source);
		this.tick = tick;
	}
	
	public T getTick() { return tick; }

	private static final long serialVersionUID = 1L;

	private final T tick;
	
}
