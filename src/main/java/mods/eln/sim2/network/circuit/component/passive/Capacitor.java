package mods.eln.sim2.network.circuit.component.passive;

import mods.eln.sim2.network.circuit.Edge;
import mods.eln.sim2.primitive.Capacitance;
import mods.eln.sim2.primitive.Resistance;
import mods.eln.sim2.primitive.TimeDelta;
import mods.eln.sim2.primitive.Voltage;

public class Capacitor extends PassiveComponent {
    private final Capacitance _capacitance;
    
    public Capacitor(final Capacitance capacitance) {
        _capacitance = capacitance;
    }
    
    @Override
    public Resistance dynamicResistance(final Edge edge) {
        return edge.network.simulation.getPeriod().divide(_capacitance);
    }

    @Override
    public Voltage remanentVoltage(final Edge edge) {
        return edge.network.simulation.getPreviousState().getVoltage(edge.terminals).substract(
                edge.network.simulation.getCurrentState().getVoltage(edge.terminals).multiply(2.0));
    }
}
