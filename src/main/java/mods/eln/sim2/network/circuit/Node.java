package mods.eln.sim2.network.circuit;

import java.util.Map;

import mods.eln.sim2.mna.Unknown;
import mods.eln.sim2.primitive.Current;
import mods.eln.sim2.primitive.Potential;

public class Node implements Unknown {
    public void register(double value, Map<Node, Potential> potentials, Map<Edge, Current> currents) {
        potentials.put(this, Potential.volts(value));
    }
}
