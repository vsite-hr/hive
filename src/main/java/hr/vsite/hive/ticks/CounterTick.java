package hr.vsite.hive.ticks;

/**
 * Tick type for counting.
 * Does not store any value.
 */
public class CounterTick extends Tick {

	@Override
	public Tick.Type getType() { return Tick.Type.Counter; }
	@Override
	public void setType(Type type) {
		if (type != getType())
			throw new IllegalArgumentException();
	}

	@Override
	public String toString() {
		return toStringBuilder()
			.toString();
	}

}
