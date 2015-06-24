package mods.eln.sim2.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import mods.eln.sim2.Simulation;
import mods.eln.sim2.primitive.Current;
import mods.eln.sim2.primitive.Resistance;
import mods.eln.sim2.primitive.TimeDelta;
import mods.eln.sim2.primitive.Voltage;
import mods.eln.sim2.network.NetworkState;
import mods.eln.sim2.network.circuit.Edge;
import mods.eln.sim2.network.circuit.component.active.VoltageSource;
import mods.eln.sim2.network.circuit.component.passive.Resistor;

import org.junit.Test;

public class SimulationTest {
    @Test
    public void beforeSimulation() {
        Simulation sim = new Simulation();
        Edge source = sim._network.freeComponent(new VoltageSource(Voltage.volts(1.0)));
        assertEquals(Voltage.volts(0.0), sim._state.getVoltage(source.terminals));
        assertEquals(Current.ampers(0.0), sim._state.getCurrent(source));
    }
    
    @Test
    public void freeVoltageSource() {
        Simulation sim = new Simulation();
        Edge source = sim._network.freeComponent(new VoltageSource(Voltage.volts(1.0)));
        NetworkState state = sim._state.next(TimeDelta.seconds(1.0));
        assertEquals(Voltage.volts(1.0), state.getVoltage(source.terminals));
        assertEquals(Current.ampers(0.0), state.getCurrent(source));
    }
    
    @Test
    public void freeResistor() {
        Simulation sim = new Simulation();
        Edge resistor = sim._network.freeComponent(new Resistor(Resistance.ohms(1.0)));
        NetworkState state = sim._state.next(TimeDelta.seconds(1.0));
        assertEquals(Voltage.volts(0.0), state.getVoltage(resistor.terminals));
        assertEquals(Current.ampers(0.0), state.getCurrent(resistor));
    }
    
    @Test
    public void unconnectedElements() {
        Simulation sim = new Simulation();
        Edge source = sim._network.freeComponent(new VoltageSource(Voltage.volts(1.0)));
        Edge resistor = sim._network.freeComponent(new Resistor(Resistance.ohms(1.0)));
        NetworkState state = sim._state.next(TimeDelta.seconds(1.0));
        assertEquals(Voltage.volts(1.0), state.getVoltage(source.terminals));
        assertEquals(Current.ampers(0.0), state.getCurrent(source));
        assertEquals(Voltage.volts(0.0), state.getVoltage(resistor.terminals));
        assertEquals(Current.ampers(0.0), state.getCurrent(resistor));
    }
    
    @Test
    public void testSimpleResistorCircuit() {
        Simulation sim = new Simulation();
        Edge source = sim._network.freeComponent(new VoltageSource(Voltage.volts(6.0)));
        Edge resistor = sim._network.connectComponent(
                source.terminals.first, source.terminals.second, new Resistor(Resistance.ohms(2.0)));
        
        NetworkState state = sim._state.next(TimeDelta.seconds(1.0));
        assertEquals(Voltage.volts(6.0), state.getVoltage(source.terminals));
        assertEquals(Current.ampers(3.0), state.getCurrent(source));
        assertEquals(Voltage.volts(6.0), state.getVoltage(resistor.terminals));
        assertEquals(Current.ampers(-3.0), state.getCurrent(resistor));
    }
    
    @Test
    public void testResistorParallel() {
        Simulation sim = new Simulation();
        Edge source = sim._network.freeComponent(new VoltageSource(Voltage.volts(6.0)));
        Edge resistor1 = sim._network.connectComponent(
                source.terminals.first, source.terminals.second, new Resistor(Resistance.ohms(3.0)));
        Edge resistor2 = sim._network.connectComponent(
                source.terminals.first, source.terminals.second, new Resistor(Resistance.ohms(6.0)));
        
        NetworkState state = sim._state.next(TimeDelta.seconds(1.0));
        assertEquals(Voltage.volts(6.0), state.getVoltage(source.terminals));
        assertEquals(Current.ampers(3.0), state.getCurrent(source));
        assertEquals(Voltage.volts(6.0), state.getVoltage(resistor1.terminals));
        assertEquals(Current.ampers(-2.0), state.getCurrent(resistor1));
        assertEquals(Voltage.volts(6.0), state.getVoltage(resistor2.terminals));
        assertEquals(Current.ampers(-1.0), state.getCurrent(resistor2));
    }
    
    @Test
    public void testResistorSeries() {
        Simulation sim = new Simulation();
        Edge source = sim._network.freeComponent(new VoltageSource(Voltage.volts(6.0)));
        Edge resistor1 = sim._network.connectComponentFrom(
                source.terminals.first, new Resistor(Resistance.ohms(2.0)));
        Edge resistor2 = sim._network.connectComponent(
                resistor1.terminals.second, source.terminals.second, new Resistor(Resistance.ohms(4.0)));
        
        NetworkState state = sim._state.next(TimeDelta.seconds(1.0));
        assertEquals(Voltage.volts(6.0), state.getVoltage(source.terminals));
        assertEquals(Current.ampers(1.0), state.getCurrent(source));
        assertEquals(Voltage.volts(2.0), state.getVoltage(resistor1.terminals));
        assertEquals(Current.ampers(-1.0), state.getCurrent(resistor1));
        assertEquals(Voltage.volts(4.0), state.getVoltage(resistor2.terminals));
        assertEquals(Current.ampers(-1.0), state.getCurrent(resistor2));
    }
}
