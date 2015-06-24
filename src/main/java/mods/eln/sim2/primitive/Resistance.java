package mods.eln.sim2.primitive;

public class Resistance extends Unit {
    public static Resistance ohms(double value) {
        return new Resistance(value);
    }
    
    Resistance(double value) {
        super(value);
    }
    
    @Override
    protected String getUnit() {
        return "Ohm";
    }
    
    public Resistance substract(final Resistance other) {
    	return new Resistance(_value - other._value);
    }
    
    public Voltage multiply(final Current current) {
    	return new Voltage(_value * current._value);
    }
    
    public Resistance multiply(double value) {
    	return new Resistance(_value * value);
    }
    
    public double multiply(final Conductance conductance) {
    	return _value * conductance._value;
    }
    
    public Conductance invert() {
    	return new Conductance(1 / _value);
    }

	public double getValue() {
		return _value;
	}

	public Resistance add(final Resistance other) {
		return new Resistance(_value + other._value);
	}

	public double divide(Resistance other) {
		return _value / other._value;
	}

	public Resistance opposite() {
		return new Resistance(-_value);
	}
}
