package mods.eln.sim2.network;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import mods.eln.sim2.network.circuit.Edge;
import mods.eln.sim2.network.circuit.Node;
import mods.eln.sim2.network.circuit.Terminals;
import mods.eln.sim2.network.circuit.component.Component;
import mods.eln.sim2.network.circuit.component.active.VoltageSource;
import mods.eln.sim2.network.circuit.component.passive.Resistor;
import mods.eln.sim2.utils.MultivaluedHashMap;


public class Network {
    public final NetworkGraph graph;
    
    public Network() {
        graph = new NetworkGraph();
    }
    
    public Edge freeComponent(final Component component) {
        return graph.freeComponent(component);
    }
    
    public Edge connectComponent(final Node from, final Node to, final Component component) {
        return graph.connectComponent(from, to, component);
    }
    
    public Edge connectComponentFrom(final Node from, final Component component) {
        return graph.connectComponentFrom(from, component);
    }
}
