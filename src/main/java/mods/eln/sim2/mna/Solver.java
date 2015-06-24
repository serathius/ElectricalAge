package mods.eln.sim2.mna;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import mods.eln.sim2.network.Network;
import mods.eln.sim2.network.NetworkState;
import mods.eln.sim2.network.Subnetwork;
import mods.eln.sim2.network.circuit.Edge;
import mods.eln.sim2.network.circuit.Node;
import mods.eln.sim2.network.circuit.Terminals;
import mods.eln.sim2.network.circuit.component.Component;
import mods.eln.sim2.network.circuit.component.passive.Resistor;
import mods.eln.sim2.utils.DefaultHashMap;

public class Solver {
    private final Network _network;
    private final NetworkState _state;
    
    public Solver(final Network network, final NetworkState state) {
        _network = network;
        _state = state; 
        }
    
    public NetworkState solve() {
        Map<Unknown, Double> results = new HashMap<Unknown, Double>();
        for (Subnetwork subnetwork: _network._subnetworks) {
            results.putAll(solveSubnetwork(subnetwork));
        }
        return new NetworkState(_network, results);
    }
    
    private Map<Unknown, Double> solveSubnetwork(final Subnetwork subnetwork) {
        CircutEquasion equasion = new CircutEquasion();
        for (Edge edge: subnetwork.graph._edges) {
            edge.component.registerInEquasion(equasion, edge);
        }
        Iterator<Node> nodes = subnetwork.graph._nodes.iterator();
        Node ground_node = nodes.next();
        equasion.setUnknown(ground_node, 0.0);
        while (nodes.hasNext()) {
            Node node = nodes.next();
            List<Edge> edges = new LinkedList<Edge>();
            for (Edge edge: subnetwork.graph._edges) {
                if (edge.terminals.first == node || edge.terminals.second == node) {
                    edges.add(edge);
                }
            }
            equasion.addNodeWithCurents(node, edges);
        }
        return equasion.solve();
    }
}
