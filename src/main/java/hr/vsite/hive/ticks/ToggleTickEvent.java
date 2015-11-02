package hr.vsite.hive.ticks;

public class ToggleTickEvent extends TickEvent<ToggleTick> {

	public ToggleTickEvent(Object source, ToggleTick tick) {
		super(source, tick);
	}
	
	private static final long serialVersionUID = 1L;

}
