package hr.vsite.hive.ticks;

/**
 * Tick type for ON/OFF values.
 */
public class ToggleTick extends Tick {

	public static enum ToggleType {
		On,
		Off,
		Undefined
	}
	
	@Override
	public Tick.Type getType() { return Tick.Type.Toggle; }
	@Override
	public void setType(Type type) {
		if (type != getType())
			throw new IllegalArgumentException();
	}
	
	/** ToggleType's toggled value */
	public ToggleType getValue() { return value; }
	public void setValue(ToggleType value) { this.value = value; }

	@Override
	public String toString() {
		return toStringBuilder()
			.append("value", value)
			.toString();
	}
	
	private ToggleType value;
	
}
