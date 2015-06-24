package mods.eln.sim2.primitive;

public class Current extends Unit {
    public static Current ampers(double value) {
        return new Current(value);
    }
    
    Current(double value) {
        super(value);
    }
    
    @Override
    protected String getUnit() {
        return "A";
    }
    
    public Current add(final Current other) {
    	return new Current(_value + other._value);
    }
    
    public Current substract(final Current other) {
    	return new Current(_value - other._value);
    }
    
    public Voltage multiply(final Resistance resistance) {
    	return new Voltage(_value * resistance._value);
    }
    
    public Power multiply(final Voltage voltage) {
    	return new Power(_value * voltage._value);
    }

	public Current multiply(double value) {
		return new Current(_value * value);
	}
	
	public Current opposite() {
		return new Current(-_value);
	}
}
