package mods.eln.sim2.primitive;

public class Potential extends Unit {
    public static Potential volts(double value) {
        return new Potential(value);
    }
    
    Potential(double value) {
        super(value);
    }
    
    @Override
    protected String getUnit() {
        return "V";
    }

    public Voltage substract(Potential other) {
        return new Voltage(_value - other._value);
    }
}
