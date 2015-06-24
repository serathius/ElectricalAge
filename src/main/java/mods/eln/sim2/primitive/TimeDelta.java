package mods.eln.sim2.primitive;

public class TimeDelta extends Unit {
    public static TimeDelta seconds(double value) {
        return new TimeDelta(value);
    }
    
    TimeDelta(double value) {
        super(value);
    }
    
    @Override
    protected String getUnit() {
        return "s";
    }
}
