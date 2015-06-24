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
    public final Set<Node> _nodes;
    public final MultivaluedHashMap<Terminals, Edge> _edges;
    
    public Network() {
        _nodes = new HashSet<Node>();
        _edges = new MultivaluedHashMap<Terminals, Edge>();
    }
    
    public Edge freeComponent(final Component component) {
        Node from = new Node();
        Node to = new Node();
        return connectComponent(from, to, component);
    }

    public Edge connectComponent(final Node from, final Node to, final Component component) {
        _nodes.add(from);
        _nodes.add(to);
        Terminals terminals = new Terminals(from, to); 
        Edge edge = new Edge(terminals, component);
        _edges.put(edge.terminals, edge);
        return edge;
    }

    public Edge connectComponentFrom(final Node from, final Component component) {
        Node to = new Node();
        return connectComponent(from, to, component);
    }
}
