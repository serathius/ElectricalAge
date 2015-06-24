package mods.eln.sim2.primitive;

import java.util.Arrays;
import java.util.List;

import mods.eln.sim2.utils.Pair;

public abstract class Unit {
    private static List<Pair<Double, String>> sizes = Arrays.asList(
            new Pair<Double, String>(1.0e9, "G"),
            new Pair<Double, String>(1.0e6, "M"),
            new Pair<Double, String>(1.0e3, "k"),
            new Pair<Double, String>(1.0, ""),
            new Pair<Double, String>(1.0e-3, "m"),
            new Pair<Double, String>(1.0e-6, "u"),
            new Pair<Double, String>(1.0e-9, "n")
            );
	final double _value;
    
    protected Unit(double value) {
    	_value = value;
    }
    
    public boolean isNaN() {
    	return Double.isNaN(_value);
    }
    
	public double getValue() {
		return _value;
	}
    
    public String toString() {
        if (_value == 0.0) {
            return String.format("%.2f %s", _value, getUnit());
        }
        else {
            for (Pair<Double, String> entry: sizes) {
                if (Math.abs(_value) >= entry.first) {
                    return String.format("%.2f %s%s", _value / entry.first, entry.second, getUnit());
                }
            }
            Pair<Double, String> last_entry = sizes.get(sizes.size() - 1);
            return String.format("%.2f %s%s", _value / last_entry.first, last_entry.second, getUnit());
        }
    }
	
	protected abstract String getUnit();
	
	public boolean equals(Object object) {
	    if (object.getClass() == getClass()) {
	        Unit other = (Unit) object;
	        return Math.abs(other._value - _value) <= 1.0e-14;
	    }
	    else {
	        return false;
	    }
	}
}
