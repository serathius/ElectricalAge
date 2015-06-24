package mods.eln.sim2.network;

import java.util.HashMap;
import java.util.Map;

import mods.eln.sim2.Simulation;
import mods.eln.sim2.primitive.Current;
import mods.eln.sim2.primitive.Potential;
import mods.eln.sim2.primitive.TimeDelta;
import mods.eln.sim2.primitive.Voltage;
import mods.eln.sim2.utils.DefaultHashMap;
import mods.eln.sim2.mna.Solver;
import mods.eln.sim2.mna.Unknown;
import mods.eln.sim2.network.Network;
import mods.eln.sim2.network.circuit.Edge;
import mods.eln.sim2.network.circuit.Node;
import mods.eln.sim2.network.circuit.Terminals;


public class NetworkState {
    private final Network _network;
    private final DefaultHashMap<Node, Potential> _node_porentials;
    private final DefaultHashMap<Edge, Current> _edge_currents;
    
    public NetworkState(final Network network) {
        _network = network;
        _node_porentials = new DefaultHashMap<Node, Potential>(Potential.volts(0.0));
        _edge_currents = new DefaultHashMap<Edge, Current>(Current.ampers(0.0));
    }
    
    public NetworkState(final Network network, final Map<Unknown, Double> unknowns) {
        this(network);
        for (Map.Entry<Unknown, Double> entry: unknowns.entrySet()) {
            entry.getKey().register(entry.getValue(), _node_porentials, _edge_currents);
        }
    }
    
    public NetworkState next(final TimeDelta time_delta) {
        return new Solver(_network, this).solve();
    }
    
    public Voltage getVoltage(final Terminals terminals) {
        return _node_porentials.get(terminals.second).substract(_node_porentials.get(terminals.first));
    }

    public Current getCurrent(final Edge edge) {
        return _edge_currents.get(edge);
    }
}
