package mods.eln.sim2;

import mods.eln.sim2.network.Network;
import mods.eln.sim2.network.NetworkState;


public class Simulation {
    public final Network _network;
    public NetworkState _state;
    
    public Simulation() {
        _network = new Network();
        _state = new NetworkState(_network);
    }
    
}
