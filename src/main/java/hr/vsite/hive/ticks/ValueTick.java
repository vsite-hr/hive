package hr.vsite.hive.ticks;

/**
 * Tick type for numerical values.
 */
public class ValueTick extends Tick {

	@Override
	public Tick.Type getType() { return Tick.Type.Value; }
	@Override
	public void setType(Type type) {
		if (type != getType())
			throw new IllegalArgumentException();
	}
	
	/** Tick's numerical value */
	public int getValue() { return value; }
	public void setValue(int value) { this.value = value; }

	@Override
	public String toString() {
		return toStringBuilder()
			.append("value", value)
			.toString();
	}
	
	private int value;

}
