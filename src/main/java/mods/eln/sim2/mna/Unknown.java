package mods.eln.sim2.mna;

import java.util.Map;

import mods.eln.sim2.network.circuit.Edge;
import mods.eln.sim2.network.circuit.Node;
import mods.eln.sim2.primitive.Current;
import mods.eln.sim2.primitive.Potential;

public interface Unknown {
   public void register(double value, Map<Node, Potential> potentials, Map<Edge, Current> currents);
}
