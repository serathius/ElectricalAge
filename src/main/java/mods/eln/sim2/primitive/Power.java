package mods.eln.sim2.primitive;

public class Power extends Unit {
    public static Power wats(double value) {
        return new Power(value);
    }
    
    Power(double value) {
        super(value);
    }
    
    @Override
    protected String getUnit() {
        return "W";
    }
    
    public Power add(final Power other) {
    	return new Power(_value + other._value);
    }
    
    public Power substract(final Power other) {
    	return new Power(_value - other._value);
    }
    
    public Voltage divide(final Current current) {
    	return new Voltage(_value / current._value);
    }
    
    public Current divide(final Voltage voltage) {
    	return new Current(_value / voltage._value);
    }
}
