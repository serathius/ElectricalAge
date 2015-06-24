package mods.eln.sim2.primitive;

public class Energy extends Unit {
    public static Energy joules(double value) {
        return new Energy(value);
    }
    
    Energy(double value) {
        super(value);
    }
    
    @Override
    protected String getUnit() {
        return "J";
    }
    
    public Energy(final Voltage voltage, final Capacitance capacity) {  	
    	super(voltage._value * voltage._value * capacity._value / 2);
    }
}
