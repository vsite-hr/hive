package hr.vsite.hive.ticks;

public class ValueTickFilter extends TickFilter {

	public Integer getValueFrom() { return valueFrom; }
	public void setValueFrom(Integer valueFrom) { this.valueFrom = valueFrom; }
	
	public Integer getValueTo() { return valueTo; }
	public void setValueTo(Integer valueTo) { this.valueTo = valueTo; }

	private Integer valueFrom;
	private Integer valueTo;
	
}
