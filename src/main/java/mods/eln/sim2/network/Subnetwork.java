package mods.eln.sim2.network;

import mods.eln.sim2.network.circuit.Edge;
import mods.eln.sim2.network.circuit.Node;
import mods.eln.sim2.network.circuit.Terminals;
import mods.eln.sim2.network.circuit.component.Component;

public class Subnetwork {
    public final NetworkGraph graph;
    
    public Subnetwork(final Network network) {
        graph = new NetworkGraph(network);
    }
    
    public Subnetwork(final Subnetwork first, final Subnetwork second) {
        graph = new NetworkGraph(first.graph, second.graph);
    }

    public Edge freeComponent(final Component component) {
        return graph.freeComponent(component);
    }
    
    public Edge connectComponent(final Terminals terminals, final Component component) {
        return graph.connectComponent(terminals, component);
    }
    
    public Edge connectComponentFrom(final Node from, final Component component) {
        return graph.connectComponentFrom(from, component);
    }
    
    public Edge connectComponentTo(final Node to, final Component component) {
        return graph.connectComponentFrom(to, component);
    }
}
