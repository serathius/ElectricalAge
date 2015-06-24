package mods.eln.sim2.network;

import java.util.HashSet;
import java.util.Set;

import com.google.common.collect.ImmutableSet;

import mods.eln.sim2.network.circuit.Edge;
import mods.eln.sim2.network.circuit.Node;
import mods.eln.sim2.network.circuit.Terminals;
import mods.eln.sim2.network.circuit.component.Component;
import mods.eln.sim2.utils.MultivaluedHashMap;

public class NetworkGraph {
    public final Network _network;
    public final Set<Node> _nodes;
    public final MultivaluedHashMap<Terminals, Edge> _edges;
    
    public NetworkGraph(final Network network) {
        _network = network;
        _nodes = new HashSet<Node>();
        _edges = new MultivaluedHashMap<Terminals, Edge>();
    }
    
    public NetworkGraph(final NetworkGraph first, final NetworkGraph second) {
        this(first._network);
        assert first._network == second._network;
        _nodes.addAll(first._nodes);
        _nodes.addAll(second._nodes);
        _edges.addAll(first._edges);
        _edges.addAll(second._edges);
    }

    public Edge freeComponent(final Component component) {
        return connectComponent(new Terminals(new Node(), new Node()), component);
    }

    public Edge connectComponent(final Terminals terminals, final Component component) {
        _nodes.add(terminals.first);
        _nodes.add(terminals.second);
        Edge edge = new Edge(_network, terminals, component);
        _edges.put(terminals, edge);
        return edge;
    }

    public Edge connectComponentFrom(final Node from, final Component component) {
        return connectComponent(new Terminals(from, new Node()), component);
    }
    
    public Edge connectComponentTo(final Node to, final Component component) {
        return connectComponent(new Terminals(new Node(), to), component);
    }
}
