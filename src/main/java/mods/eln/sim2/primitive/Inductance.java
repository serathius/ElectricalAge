package mods.eln.sim2.primitive;

public class Inductance extends Unit {
    public static Inductance henrs(double value) {
        return new Inductance(value);
    }
    
    Inductance(double value) {
        super(value);
    }
    
    @Override
    protected String getUnit() {
        return "H";
    }
    
    public Inductance substract(final Inductance other) {
    	return new Inductance(_value - other._value);
    }

	public Resistance divide(TimeDelta dt) {
		return new Resistance(_value / dt._value);
	}
}
