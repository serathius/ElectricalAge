package mods.eln.sim2.primitive;

public class Voltage extends Unit {
    
    public static Voltage volts(double value) {
        return new Voltage(value);
    }
    
    Voltage(double value) {
    	super(value);
    }
    
    @Override
    protected String getUnit() {
        return "V";
    }
    
    public Power multiply(final Current current) {
    	return new Power(_value * current._value);
    }
    
    public Current multiply(final Conductance conductance) {
    	return new Current(_value * conductance._value);
    }
    
    public Voltage multiply(final double value)
    {
    	return new Voltage(_value * value);
    }

	public Current divide(Resistance resistance) {
		return new Current(_value / resistance._value);
	}
	
	public Resistance divide(Current current) {
		return new Resistance(_value / current._value);
	}
	
	public static Voltage min(final Voltage first, final Voltage second) {
		return new Voltage(Math.min(first._value, second._value));
	}
	
	public static Voltage max(final Voltage first, final Voltage second) {
		return new Voltage(Math.max(first._value, second._value));
	}
}
