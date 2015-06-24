package mods.eln.sim2.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import mods.eln.sim2.Simulation;
import mods.eln.sim2.primitive.Capacitance;
import mods.eln.sim2.primitive.Current;
import mods.eln.sim2.primitive.Resistance;
import mods.eln.sim2.primitive.TimeDelta;
import mods.eln.sim2.primitive.Voltage;
import mods.eln.sim2.network.NetworkState;
import mods.eln.sim2.network.circuit.Edge;
import mods.eln.sim2.network.circuit.component.active.VoltageSource;
import mods.eln.sim2.network.circuit.component.passive.Capacitor;
import mods.eln.sim2.network.circuit.component.passive.Resistor;

import org.junit.Test;

public class SimulationTest {
    @Test
    public void beforeSimulation() {
        Simulation sim = new Simulation(TimeDelta.seconds(1.0));
        Edge source = sim.network.freeComponent(new VoltageSource(Voltage.volts(1.0)));
        assertEquals(Voltage.volts(0.0), sim.getCurrentState().getVoltage(source.terminals));
        assertEquals(Current.ampers(0.0), sim.getCurrentState().getCurrent(source));
    }
    
    @Test
    public void freeVoltageSource() {
        Simulation sim = new Simulation(TimeDelta.seconds(1.0));
        Edge source = sim.network.freeComponent(new VoltageSource(Voltage.volts(1.0)));
        sim.step();
        assertEquals(Voltage.volts(1.0), sim.getCurrentState().getVoltage(source.terminals));
        assertEquals(Current.ampers(0.0), sim.getCurrentState().getCurrent(source));
    }
    
    @Test
    public void freeResistor() {
        Simulation sim = new Simulation(TimeDelta.seconds(1.0));
        Edge resistor = sim.network.freeComponent(new Resistor(Resistance.ohms(1.0)));
        sim.step();
        assertEquals(Voltage.volts(0.0), sim.getCurrentState().getVoltage(resistor.terminals));
        assertEquals(Current.ampers(0.0), sim.getCurrentState().getCurrent(resistor));
    }
    
    @Test
    public void freeCapacitor() {
        Simulation sim = new Simulation(TimeDelta.seconds(1.0));
        Edge resistor = sim.network.freeComponent(new Capacitor(Capacitance.farrads(1.0)));
        sim.step();
        assertEquals(Voltage.volts(0.0), sim.getCurrentState().getVoltage(resistor.terminals));
        assertEquals(Current.ampers(0.0), sim.getCurrentState().getCurrent(resistor));
    }
    
    @Test
    public void testSimpleResistorCircuit() {
        Simulation sim = new Simulation(TimeDelta.seconds(1.0));
        Edge source = sim.network.freeComponent(new VoltageSource(Voltage.volts(6.0)));
        Edge resistor = sim.network.connectComponent(source.terminals, new Resistor(Resistance.ohms(2.0)));
        sim.step();
        assertEquals(Voltage.volts(6.0), sim.getCurrentState().getVoltage(source.terminals));
        assertEquals(Current.ampers(3.0), sim.getCurrentState().getCurrent(source));
        assertEquals(Voltage.volts(6.0), sim.getCurrentState().getVoltage(resistor.terminals));
        assertEquals(Current.ampers(-3.0), sim.getCurrentState().getCurrent(resistor));
    }
    
    @Test
    public void testSimpleCapacitorCircuit() {
        Simulation sim = new Simulation(TimeDelta.seconds(1.0));
        Edge source = sim.network.freeComponent(new VoltageSource(Voltage.volts(6.0)));
        Edge capacitor = sim.network.connectComponent(source.terminals, new Capacitor(Capacitance.farrads(1.0)));
        sim.step();
        assertEquals(Voltage.volts(6.0), sim.getCurrentState().getVoltage(source.terminals));
        assertEquals(Current.ampers(6.0), sim.getCurrentState().getCurrent(source));
        assertEquals(Voltage.volts(6.0), sim.getCurrentState().getVoltage(capacitor.terminals));
        assertEquals(Current.ampers(-6.0), sim.getCurrentState().getCurrent(capacitor));
        sim.step();
        assertEquals(Voltage.volts(6.0), sim.getCurrentState().getVoltage(source.terminals));
        assertEquals(Current.ampers(-6.0), sim.getCurrentState().getCurrent(source));
        assertEquals(Voltage.volts(6.0), sim.getCurrentState().getVoltage(capacitor.terminals));
        assertEquals(Current.ampers(6.0), sim.getCurrentState().getCurrent(capacitor));
        sim.step();
        assertEquals(Voltage.volts(6.0), sim.getCurrentState().getVoltage(source.terminals));
        assertEquals(Current.ampers(0.0), sim.getCurrentState().getCurrent(source));
        assertEquals(Voltage.volts(6.0), sim.getCurrentState().getVoltage(capacitor.terminals));
        assertEquals(Current.ampers(0.0), sim.getCurrentState().getCurrent(capacitor));
    }
    
    @Test
    public void testResistorParallel() {
        Simulation sim = new Simulation(TimeDelta.seconds(1.0));
        Edge source = sim.network.freeComponent(new VoltageSource(Voltage.volts(6.0)));
        Edge resistor1 = sim.network.connectComponent(source.terminals, new Resistor(Resistance.ohms(3.0)));
        Edge resistor2 = sim.network.connectComponent(source.terminals, new Resistor(Resistance.ohms(6.0)));
        sim.step();
        assertEquals(Voltage.volts(6.0), sim.getCurrentState().getVoltage(source.terminals));
        assertEquals(Current.ampers(3.0), sim.getCurrentState().getCurrent(source));
        assertEquals(Voltage.volts(6.0), sim.getCurrentState().getVoltage(resistor1.terminals));
        assertEquals(Current.ampers(-2.0), sim.getCurrentState().getCurrent(resistor1));
        assertEquals(Voltage.volts(6.0), sim.getCurrentState().getVoltage(resistor2.terminals));
        assertEquals(Current.ampers(-1.0), sim.getCurrentState().getCurrent(resistor2));
    }
    
    @Test
    public void testCapacitorParallel() {
        Simulation sim = new Simulation(TimeDelta.seconds(1.0));
        Edge source = sim.network.freeComponent(new VoltageSource(Voltage.volts(6.0)));
        Edge capacitor1 = sim.network.connectComponent(source.terminals, new Capacitor(Capacitance.farrads(2)));
        Edge capacitor2 = sim.network.connectComponent(source.terminals, new Capacitor(Capacitance.farrads(1)));
        sim.step();
        assertEquals(Voltage.volts(6.0), sim.getCurrentState().getVoltage(source.terminals));
        assertEquals(Current.ampers(9.0), sim.getCurrentState().getCurrent(source));
        assertEquals(Voltage.volts(6.0), sim.getCurrentState().getVoltage(capacitor1.terminals));
        assertEquals(Current.ampers(-3.0), sim.getCurrentState().getCurrent(capacitor1));
        assertEquals(Voltage.volts(6.0), sim.getCurrentState().getVoltage(capacitor2.terminals));
        assertEquals(Current.ampers(-6.0), sim.getCurrentState().getCurrent(capacitor2));
        sim.step();
        assertEquals(Voltage.volts(6.0), sim.getCurrentState().getVoltage(source.terminals));
        assertEquals(Current.ampers(-9.0), sim.getCurrentState().getCurrent(source));
        assertEquals(Voltage.volts(6.0), sim.getCurrentState().getVoltage(capacitor1.terminals));
        assertEquals(Current.ampers(3.0), sim.getCurrentState().getCurrent(capacitor1));
        assertEquals(Voltage.volts(6.0), sim.getCurrentState().getVoltage(capacitor2.terminals));
        assertEquals(Current.ampers(6.0), sim.getCurrentState().getCurrent(capacitor2));
        sim.step();
        assertEquals(Voltage.volts(6.0), sim.getCurrentState().getVoltage(source.terminals));
        assertEquals(Current.ampers(0.0), sim.getCurrentState().getCurrent(source));
        assertEquals(Voltage.volts(6.0), sim.getCurrentState().getVoltage(capacitor1.terminals));
        assertEquals(Current.ampers(0.0), sim.getCurrentState().getCurrent(capacitor1));
        assertEquals(Voltage.volts(6.0), sim.getCurrentState().getVoltage(capacitor2.terminals));
        assertEquals(Current.ampers(0.0), sim.getCurrentState().getCurrent(capacitor2));
    }
    
    @Test
    public void testResistorSeries() {
        Simulation sim = new Simulation(TimeDelta.seconds(1.0));
        Edge source = sim.network.freeComponent(new VoltageSource(Voltage.volts(6.0)));
        Edge resistor1 = sim.network.connectComponentFrom(
                source.terminals.first, new Resistor(Resistance.ohms(2.0)));
        Edge resistor2 = sim.network.connectComponent(
                source.terminals.second, resistor1.terminals.second, new Resistor(Resistance.ohms(4.0)));
        sim.step();
        assertEquals(Voltage.volts(6.0), sim.getCurrentState().getVoltage(source.terminals));
        assertEquals(Current.ampers(1.0), sim.getCurrentState().getCurrent(source));
        assertEquals(Voltage.volts(2.0), sim.getCurrentState().getVoltage(resistor1.terminals));
        assertEquals(Current.ampers(-1.0), sim.getCurrentState().getCurrent(resistor1));
        assertEquals(Voltage.volts(-4.0), sim.getCurrentState().getVoltage(resistor2.terminals));
        assertEquals(Current.ampers(1.0), sim.getCurrentState().getCurrent(resistor2));
    }
    
    @Test
    public void testCapacitorSeries() {
        Simulation sim = new Simulation(TimeDelta.seconds(1.0));
        Edge source = sim.network.freeComponent(new VoltageSource(Voltage.volts(6.0)));
        Edge capacitor1 = sim.network.connectComponentFrom(
                source.terminals.first, new Capacitor(Capacitance.farrads(2)));
        Edge capacitor2 = sim.network.connectComponent(
                source.terminals.second, capacitor1.terminals.second, new Capacitor(Capacitance.farrads(1)));
        sim.step();
        assertEquals(Voltage.volts(6.0), sim.getCurrentState().getVoltage(source.terminals));
        assertEquals(Current.ampers(2.0), sim.getCurrentState().getCurrent(source));
        assertEquals(Voltage.volts(4.0), sim.getCurrentState().getVoltage(capacitor1.terminals));
        assertEquals(Current.ampers(-2.0), sim.getCurrentState().getCurrent(capacitor1));
        assertEquals(Voltage.volts(-2.0), sim.getCurrentState().getVoltage(capacitor2.terminals));
        assertEquals(Current.ampers(2.0), sim.getCurrentState().getCurrent(capacitor2));
        sim.step();
        assertEquals(Voltage.volts(6.0), sim.getCurrentState().getVoltage(source.terminals));
        assertEquals(Current.ampers(-2.0), sim.getCurrentState().getCurrent(source));
        assertEquals(Voltage.volts(4.0), sim.getCurrentState().getVoltage(capacitor1.terminals));
        assertEquals(Current.ampers(2.0), sim.getCurrentState().getCurrent(capacitor1));
        assertEquals(Voltage.volts(-2.0), sim.getCurrentState().getVoltage(capacitor2.terminals));
        assertEquals(Current.ampers(-2.0), sim.getCurrentState().getCurrent(capacitor2));
        sim.step();
        assertEquals(Voltage.volts(6.0), sim.getCurrentState().getVoltage(source.terminals));
        assertEquals(Current.ampers(0.0), sim.getCurrentState().getCurrent(source));
        assertEquals(Voltage.volts(4.0), sim.getCurrentState().getVoltage(capacitor1.terminals));
        assertEquals(Current.ampers(0.0), sim.getCurrentState().getCurrent(capacitor1));
        assertEquals(Voltage.volts(-2.0), sim.getCurrentState().getVoltage(capacitor2.terminals));
        assertEquals(Current.ampers(0.0), sim.getCurrentState().getCurrent(capacitor2));
    }
    
    @Test
    public void testResistorCapacitorSeries() {
        Simulation sim = new Simulation(TimeDelta.seconds(1));
        Edge source = sim.network.freeComponent(new VoltageSource(Voltage.volts(1.0)));
        Edge capacitor = sim.network.connectComponentFrom(
                source.terminals.first, new Capacitor(Capacitance.farrads(1)));
        Edge resistor = sim.network.connectComponent(
                source.terminals.second, capacitor.terminals.second, new Resistor(Resistance.ohms(1)));
        sim.step();
        assertEquals(Voltage.volts(1.0), sim.getCurrentState().getVoltage(source.terminals));
        assertEquals(Current.ampers(0.5), sim.getCurrentState().getCurrent(source));
        assertEquals(Voltage.volts(0.5), sim.getCurrentState().getVoltage(capacitor.terminals));
        assertEquals(Current.ampers(-0.5), sim.getCurrentState().getCurrent(capacitor));
        assertEquals(Voltage.volts(-0.5), sim.getCurrentState().getVoltage(resistor.terminals));
        assertEquals(Current.ampers(0.5), sim.getCurrentState().getCurrent(resistor));
        sim.step();
        assertEquals(Voltage.volts(1.0), sim.getCurrentState().getVoltage(source.terminals));
        assertEquals(Current.ampers(0.0), sim.getCurrentState().getCurrent(source));
        assertEquals(Voltage.volts(1.0), sim.getCurrentState().getVoltage(capacitor.terminals));
        assertEquals(Current.ampers(0.0), sim.getCurrentState().getCurrent(capacitor));
        assertEquals(Voltage.volts(0.0), sim.getCurrentState().getVoltage(resistor.terminals));
        assertEquals(Current.ampers(0.0), sim.getCurrentState().getCurrent(resistor));
        sim.step();
        assertEquals(Voltage.volts(1.0), sim.getCurrentState().getVoltage(source.terminals));
        assertEquals(Current.ampers(-0.25), sim.getCurrentState().getCurrent(source));
        assertEquals(Voltage.volts(1.25), sim.getCurrentState().getVoltage(capacitor.terminals));
        assertEquals(Current.ampers(0.25), sim.getCurrentState().getCurrent(capacitor));
        assertEquals(Voltage.volts(0.25), sim.getCurrentState().getVoltage(resistor.terminals));
        assertEquals(Current.ampers(-0.25), sim.getCurrentState().getCurrent(resistor));
    }
    
    @Test
    public void testResistorCapacitorParallel() {
        Simulation sim = new Simulation(TimeDelta.seconds(1.0));
        Edge source = sim.network.freeComponent(new VoltageSource(Voltage.volts(1.0)));
        Edge capacitor = sim.network.connectComponent(source.terminals, new Capacitor(Capacitance.farrads(1)));
        Edge resistor = sim.network.connectComponent(source.terminals, new Resistor(Resistance.ohms(1)));
        sim.step();
        assertEquals(Voltage.volts(1.0), sim.getCurrentState().getVoltage(source.terminals));
        assertEquals(Current.ampers(2.0), sim.getCurrentState().getCurrent(source));
        assertEquals(Voltage.volts(1.0), sim.getCurrentState().getVoltage(capacitor.terminals));
        assertEquals(Current.ampers(-1.0), sim.getCurrentState().getCurrent(capacitor));
        assertEquals(Voltage.volts(1.0), sim.getCurrentState().getVoltage(resistor.terminals));
        assertEquals(Current.ampers(-1.0), sim.getCurrentState().getCurrent(resistor));
        sim.step();
        assertEquals(Voltage.volts(1.0), sim.getCurrentState().getVoltage(source.terminals));
        assertEquals(Current.ampers(0.0), sim.getCurrentState().getCurrent(source));
        assertEquals(Voltage.volts(1.0), sim.getCurrentState().getVoltage(capacitor.terminals));
        assertEquals(Current.ampers(1.0), sim.getCurrentState().getCurrent(capacitor));
        assertEquals(Voltage.volts(1.0), sim.getCurrentState().getVoltage(resistor.terminals));
        assertEquals(Current.ampers(-1.0), sim.getCurrentState().getCurrent(resistor));
        sim.step();
        assertEquals(Voltage.volts(1.0), sim.getCurrentState().getVoltage(source.terminals));
        assertEquals(Current.ampers(1.0), sim.getCurrentState().getCurrent(source));
        assertEquals(Voltage.volts(1.0), sim.getCurrentState().getVoltage(capacitor.terminals));
        assertEquals(Current.ampers(0.0), sim.getCurrentState().getCurrent(capacitor));
        assertEquals(Voltage.volts(1.0), sim.getCurrentState().getVoltage(resistor.terminals));
        assertEquals(Current.ampers(-1.0), sim.getCurrentState().getCurrent(resistor));
    }
    
    @Test
    public void testResistorSeriesParallel() {
        Simulation sim = new Simulation(TimeDelta.seconds(1.0));
        Edge source = sim.network.freeComponent(new VoltageSource(Voltage.volts(6.0)));
        Edge resistor1 = sim.network.connectComponentFrom(
                source.terminals.first, new Resistor(Resistance.ohms(2.0)));
        Edge resistor2 = sim.network.connectComponent(
                resistor1.terminals.second, source.terminals.second, new Resistor(Resistance.ohms(2.0)));
        Edge resistor3 = sim.network.connectComponent(resistor2.terminals, new Resistor(Resistance.ohms(2.0)));
        sim.step();
        assertEquals(Voltage.volts(6.0), sim.getCurrentState().getVoltage(source.terminals));
        assertEquals(Current.ampers(2.0), sim.getCurrentState().getCurrent(source));
        assertEquals(Voltage.volts(4.0), sim.getCurrentState().getVoltage(resistor1.terminals));
        assertEquals(Current.ampers(-2.0), sim.getCurrentState().getCurrent(resistor1));
        assertEquals(Voltage.volts(2.0), sim.getCurrentState().getVoltage(resistor2.terminals));
        assertEquals(Current.ampers(-1.0), sim.getCurrentState().getCurrent(resistor2));
        assertEquals(Voltage.volts(2.0), sim.getCurrentState().getVoltage(resistor3.terminals));
        assertEquals(Current.ampers(-1.0), sim.getCurrentState().getCurrent(resistor3));
    }
    
    @Test
    public void testCapacitorSeriesParallel() {
        Simulation sim = new Simulation(TimeDelta.seconds(1.0));
        Edge source = sim.network.freeComponent(new VoltageSource(Voltage.volts(6.0)));
        Edge capacitor1 = sim.network.connectComponentFrom(
                source.terminals.first, new Capacitor(Capacitance.farrads(2.0)));
        Edge capacitor2 = sim.network.connectComponent(
                capacitor1.terminals.second, source.terminals.second, new Capacitor(Capacitance.farrads(2.0)));
        Edge capacitor3 = sim.network.connectComponent(capacitor2.terminals, new Capacitor(Capacitance.farrads(2.0)));
        sim.step();
        assertEquals(Voltage.volts(6.0), sim.getCurrentState().getVoltage(source.terminals));
        assertEquals(Current.ampers(2.0), sim.getCurrentState().getCurrent(source));
        assertEquals(Voltage.volts(4.0), sim.getCurrentState().getVoltage(capacitor1.terminals));
        assertEquals(Current.ampers(-2.0), sim.getCurrentState().getCurrent(capacitor1));
        assertEquals(Voltage.volts(2.0), sim.getCurrentState().getVoltage(capacitor2.terminals));
        assertEquals(Current.ampers(-1.0), sim.getCurrentState().getCurrent(capacitor2));
        assertEquals(Voltage.volts(2.0), sim.getCurrentState().getVoltage(capacitor3.terminals));
        assertEquals(Current.ampers(-1.0), sim.getCurrentState().getCurrent(capacitor3));
        sim.step();
        assertEquals(Voltage.volts(6.0), sim.getCurrentState().getVoltage(source.terminals));
        assertEquals(Current.ampers(-2.0), sim.getCurrentState().getCurrent(source));
        assertEquals(Voltage.volts(4.0), sim.getCurrentState().getVoltage(capacitor1.terminals));
        assertEquals(Current.ampers(2.0), sim.getCurrentState().getCurrent(capacitor1));
        assertEquals(Voltage.volts(2.0), sim.getCurrentState().getVoltage(capacitor2.terminals));
        assertEquals(Current.ampers(1.0), sim.getCurrentState().getCurrent(capacitor2));
        assertEquals(Voltage.volts(2.0), sim.getCurrentState().getVoltage(capacitor3.terminals));
        assertEquals(Current.ampers(1.0), sim.getCurrentState().getCurrent(capacitor3));
        sim.step();
        assertEquals(Voltage.volts(6.0), sim.getCurrentState().getVoltage(source.terminals));
        assertEquals(Current.ampers(0.0), sim.getCurrentState().getCurrent(source));
        assertEquals(Voltage.volts(4.0), sim.getCurrentState().getVoltage(capacitor1.terminals));
        assertEquals(Current.ampers(0.0), sim.getCurrentState().getCurrent(capacitor1));
        assertEquals(Voltage.volts(2.0), sim.getCurrentState().getVoltage(capacitor2.terminals));
        assertEquals(Current.ampers(0.0), sim.getCurrentState().getCurrent(capacitor2));
        assertEquals(Voltage.volts(2.0), sim.getCurrentState().getVoltage(capacitor3.terminals));
        assertEquals(Current.ampers(0.0), sim.getCurrentState().getCurrent(capacitor3));
    }
    
    @Test
    public void testResistorParallelSeries() {
        Simulation sim = new Simulation(TimeDelta.seconds(1.0));
        Edge source = sim.network.freeComponent(new VoltageSource(Voltage.volts(6.0)));
        Edge resistor1 = sim.network.connectComponent(source.terminals, new Resistor(Resistance.ohms(3.0)));
        Edge resistor2 = sim.network.connectComponentFrom(
                resistor1.terminals.first, new Resistor(Resistance.ohms(2.0)));
        Edge resistor3 = sim.network.connectComponent(
                resistor2.terminals.second, source.terminals.second, new Resistor(Resistance.ohms(4.0)));
        sim.step();
        assertEquals(Voltage.volts(6.0), sim.getCurrentState().getVoltage(source.terminals));
        assertEquals(Current.ampers(3.0), sim.getCurrentState().getCurrent(source));
        assertEquals(Voltage.volts(6.0), sim.getCurrentState().getVoltage(resistor1.terminals));
        assertEquals(Current.ampers(-2.0), sim.getCurrentState().getCurrent(resistor1));
        assertEquals(Voltage.volts(2.0), sim.getCurrentState().getVoltage(resistor2.terminals));
        assertEquals(Current.ampers(-1.0), sim.getCurrentState().getCurrent(resistor2));
        assertEquals(Voltage.volts(4.0), sim.getCurrentState().getVoltage(resistor3.terminals));
        assertEquals(Current.ampers(-1.0), sim.getCurrentState().getCurrent(resistor3));
    }
    
    @Test
    public void testCapacitorParallelSeries() {
        Simulation sim = new Simulation(TimeDelta.seconds(1.0));
        Edge source = sim.network.freeComponent(new VoltageSource(Voltage.volts(6.0)));
        Edge capacitor1 = sim.network.connectComponent(source.terminals, new Capacitor(Capacitance.farrads(3.0)));
        Edge capacitor2 = sim.network.connectComponentFrom(
                capacitor1.terminals.first, new Capacitor(Capacitance.farrads(2.0)));
        Edge capacitor3 = sim.network.connectComponent(
                capacitor2.terminals.second, source.terminals.second, new Capacitor(Capacitance.farrads(4.0)));
        sim.step();
        assertEquals(Voltage.volts(6.0), sim.getCurrentState().getVoltage(source.terminals));
        assertEquals(Current.ampers(3.0), sim.getCurrentState().getCurrent(source));
        assertEquals(Voltage.volts(6.0), sim.getCurrentState().getVoltage(capacitor1.terminals));
        assertEquals(Current.ampers(-2.0), sim.getCurrentState().getCurrent(capacitor1));
        assertEquals(Voltage.volts(2.0), sim.getCurrentState().getVoltage(capacitor2.terminals));
        assertEquals(Current.ampers(-1.0), sim.getCurrentState().getCurrent(capacitor2));
        assertEquals(Voltage.volts(4.0), sim.getCurrentState().getVoltage(capacitor3.terminals));
        assertEquals(Current.ampers(-1.0), sim.getCurrentState().getCurrent(capacitor3));
        sim.step();
        assertEquals(Voltage.volts(6.0), sim.getCurrentState().getVoltage(source.terminals));
        assertEquals(Current.ampers(-3.0), sim.getCurrentState().getCurrent(source));
        assertEquals(Voltage.volts(6.0), sim.getCurrentState().getVoltage(capacitor1.terminals));
        assertEquals(Current.ampers(2.0), sim.getCurrentState().getCurrent(capacitor1));
        assertEquals(Voltage.volts(2.0), sim.getCurrentState().getVoltage(capacitor2.terminals));
        assertEquals(Current.ampers(1.0), sim.getCurrentState().getCurrent(capacitor2));
        assertEquals(Voltage.volts(4.0), sim.getCurrentState().getVoltage(capacitor3.terminals));
        assertEquals(Current.ampers(1.0), sim.getCurrentState().getCurrent(capacitor3));
        sim.step();
        assertEquals(Voltage.volts(6.0), sim.getCurrentState().getVoltage(source.terminals));
        assertEquals(Current.ampers(0.0), sim.getCurrentState().getCurrent(source));
        assertEquals(Voltage.volts(6.0), sim.getCurrentState().getVoltage(capacitor1.terminals));
        assertEquals(Current.ampers(0.0), sim.getCurrentState().getCurrent(capacitor1));
        assertEquals(Voltage.volts(2.0), sim.getCurrentState().getVoltage(capacitor2.terminals));
        assertEquals(Current.ampers(0.0), sim.getCurrentState().getCurrent(capacitor2));
        assertEquals(Voltage.volts(4.0), sim.getCurrentState().getVoltage(capacitor3.terminals));
        assertEquals(Current.ampers(0.0), sim.getCurrentState().getCurrent(capacitor3));
    }
}
