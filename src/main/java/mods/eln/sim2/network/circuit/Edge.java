package mods.eln.sim2.network.circuit;

import java.util.Map;

import mods.eln.sim2.mna.CircutEquasion;
import mods.eln.sim2.mna.Unknown;
import mods.eln.sim2.network.Network;
import mods.eln.sim2.network.circuit.component.Component;
import mods.eln.sim2.primitive.Current;
import mods.eln.sim2.primitive.Potential;
import mods.eln.sim2.primitive.TimeDelta;

public class Edge implements Unknown {
    public final Network network;
    public final Terminals terminals;
    public final Component component;
    
    public Edge(final Network network, final Terminals terminals, final Component component) {
        this.network = network;
        this.terminals = terminals;
        this.component = component;
    }
    
    public boolean equals(Object object) {
        if (object instanceof Terminals) {
            Edge other = (Edge) object;
            return terminals.equals(other.terminals) && component == other.component;
        } 
        else {
            return false;
        }
    }
    
    public int hashCode() {
        return terminals.hashCode() + component.hashCode();
    }
    
    public void registerInState(double value, Map<Node, Potential> potentials, Map<Edge, Current> currents) {
        currents.put(this, Current.ampers(value));
    }
    
    public void registerInEquasion(final CircutEquasion equasion) {
        component.registerInEquasion(equasion, this);
    }
    
}
