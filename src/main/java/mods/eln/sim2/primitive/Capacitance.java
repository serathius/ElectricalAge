package mods.eln.sim2.primitive;

public class Capacitance extends Unit {
    public static Capacitance farrads(double value) {
        return new Capacitance(value);
    }
    
    Capacitance(double value) {
        super(value);
    }
    
    @Override
    protected String getUnit() {
        return "F";
    }
    
    public Capacitance add(final Capacitance other) {
    	return new Capacitance(_value + other._value);
    }
    
    public Capacitance substract(final Capacitance other) {
    	return new Capacitance(_value - other._value);
    }
    
    public Conductance divide(final TimeDelta timedelta) {
    	return new Conductance(_value / timedelta._value);
    }
    
    public static Capacitance parallel(final Capacitance first, final Capacitance second) {
        return new Capacitance(first._value * second._value / (first._value + second._value));
    }
    
    public static Capacitance series(final Capacitance first, final Capacitance second) {
        return new Capacitance(first._value + second._value);
    }
}
