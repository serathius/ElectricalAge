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
    public final List<Subnetwork> _subnetworks;
    
    public Network() {
        _subnetworks = new LinkedList<Subnetwork>();
    }
    
    public Edge freeComponent(final Component component) {
        Subnetwork subnetwork = new Subnetwork();
        Edge edge = subnetwork.freeComponent(component);
        _subnetworks.add(subnetwork);
        return edge;
    }
    
    public Edge connectComponent(final Node from, final Node to, final Component component) {
        Subnetwork fromSubnetwork = null, toSubnetwork = null;
        for (Subnetwork network: _subnetworks) {
            if (network.graph._nodes.contains(from)) {
                fromSubnetwork = network;
            }
            if (network.graph._nodes.contains(to)) {
                toSubnetwork = network;
            }
        }
        if (fromSubnetwork == toSubnetwork || fromSubnetwork != null) {
            return fromSubnetwork.connectComponent(from, to, component);
        }
        else {
            assert fromSubnetwork != null && toSubnetwork != null;
            _subnetworks.remove(fromSubnetwork);
            _subnetworks.remove(toSubnetwork);
            Subnetwork joined_subnetwork = new Subnetwork(fromSubnetwork, toSubnetwork); 
            _subnetworks.add(joined_subnetwork);
            return joined_subnetwork.connectComponent(from, to, component);
        }
    }
    
    public Edge connectComponentFrom(final Node from, final Component component) {
        for (Subnetwork network: _subnetworks) {
            if (network.graph._nodes.contains(from)) {
                return network.connectComponentFrom(from, component);
            }
        }
        assert false;
        return null;
    }
    
}
