package mods.eln.sim2.network.circuit.component.passive;

import mods.eln.sim2.network.circuit.Edge;
import mods.eln.sim2.network.circuit.Node;
import mods.eln.sim2.network.circuit.component.Component;
import mods.eln.sim2.primitive.Resistance;

public class Resistor extends PassiveComponent {
    final Resistance _resistance;
    
    public Resistor(final Resistance resistance) {
        _resistance = resistance;
    }
    
    @Override
    public Resistance dynamicResistance() {
        return _resistance;
    }
}
