package mods.eln.sim2.network;

import mods.eln.sim2.network.circuit.Edge;
import mods.eln.sim2.network.circuit.Node;
import mods.eln.sim2.network.circuit.component.Component;

public class Subnetwork {
    public final NetworkGraph graph;
    
    public Subnetwork() {
        graph = new NetworkGraph();
    }
    
    public Subnetwork(final Subnetwork first, final Subnetwork second) {
        graph = new NetworkGraph(first.graph, second.graph);
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
