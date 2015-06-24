package mods.eln.sim2.primitive;

public class Conductance extends Unit {
    public static Conductance simenses(double value) {
        return new Conductance(value);
    }
    
    Conductance(double value) {
        super(value);
    }
    
    @Override
    protected String getUnit() {
        return "S";
    }
    
    public Conductance add(final Conductance other) {
    	return new Conductance(_value + other._value);
    }
    
    public Conductance substract(final Conductance other) {
    	return new Conductance(_value - other._value);
    }
    
    public Current multiply(final Voltage voltage) {
    	return new Current(_value * voltage._value);
    }
}
