package mods.eln.sim2;

import mods.eln.sim2.network.Network;
import mods.eln.sim2.network.NetworkState;
import mods.eln.sim2.primitive.TimeDelta;


public class Simulation {
    public final Network network;
    private NetworkState _previous_state;
    private NetworkState _current_state;
    private TimeDelta _period ;
    
    public Simulation(final TimeDelta period) {
        network = new Network(this);
        _period = period;
        _previous_state = new NetworkState(network);
        _current_state = new NetworkState(network);
    }
    
    public void step() {
        NetworkState new_state = _current_state.next(_period); 
        _previous_state = _current_state;
        _current_state = new_state;
    }
    
    public NetworkState getCurrentState() {
        return _current_state;
    }
    
    public NetworkState getPreviousState() {
        return _previous_state;
    }

    public TimeDelta getPeriod() {
        return _period;
    }
    
}
