package mods.eln.sim2.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import mods.eln.sim2.Simulation;
import mods.eln.sim2.primitive.Capacitance;
import mods.eln.sim2.primitive.Current;
import mods.eln.sim2.primitive.Inductance;
import mods.eln.sim2.primitive.Resistance;
import mods.eln.sim2.primitive.TimeDelta;
import mods.eln.sim2.primitive.Voltage;
import mods.eln.sim2.network.NetworkState;
import mods.eln.sim2.network.circuit.Edge;
import mods.eln.sim2.network.circuit.component.active.VoltageSource;
import mods.eln.sim2.network.circuit.component.passive.Capacitor;
import mods.eln.sim2.network.circuit.component.passive.Inductor;
import mods.eln.sim2.network.circuit.component.passive.Resistor;

import org.junit.Test;

public class SimulationTest {
    @Test
    public void beforeSimulation() {
        Simulation sim = new Simulation(TimeDelta.seconds(0.1));
        Edge source = sim.network.freeComponent(new VoltageSource(Voltage.volts(1.0)));
        assertEquals(Voltage.volts(0.0), sim.getCurrentState().getVoltage(source.terminals));
        assertEquals(Current.ampers(0.0), sim.getCurrentState().getCurrent(source));
    }
    
    @Test
    public void freeVoltageSource() {
        Simulation sim = new Simulation(TimeDelta.seconds(0.1));
        Edge source = sim.network.freeComponent(new VoltageSource(Voltage.volts(1.0)));
        sim.step();
        assertEquals(Voltage.volts(1.0), sim.getCurrentState().getVoltage(source.terminals));
        assertEquals(Current.ampers(0.0), sim.getCurrentState().getCurrent(source));
    }
    
    @Test
    public void freeResistor() {
        Simulation sim = new Simulation(TimeDelta.seconds(0.1));
        Edge resistor = sim.network.freeComponent(new Resistor(Resistance.ohms(1.0)));
        sim.step();
        assertEquals(Voltage.volts(0.0), sim.getCurrentState().getVoltage(resistor.terminals));
        assertEquals(Current.ampers(0.0), sim.getCurrentState().getCurrent(resistor));
    }
    
    @Test
    public void freeCapacitor() {
        Simulation sim = new Simulation(TimeDelta.seconds(0.1));
        Edge resistor = sim.network.freeComponent(new Capacitor(Capacitance.farrads(1.0)));
        sim.step();
        assertEquals(Voltage.volts(0.0), sim.getCurrentState().getVoltage(resistor.terminals));
        assertEquals(Current.ampers(0.0), sim.getCurrentState().getCurrent(resistor));
    }
    
    @Test
    public void freeInductor() {
        Simulation sim = new Simulation(TimeDelta.seconds(0.1));
        Edge inductor = sim.network.freeComponent(new Inductor(Inductance.henrs(1.0)));
        sim.step();
        assertEquals(Voltage.volts(0.0), sim.getCurrentState().getVoltage(inductor.terminals));
        assertEquals(Current.ampers(0.0), sim.getCurrentState().getCurrent(inductor));
    }
    
    @Test
    public void testSimpleResistorCircuit() {
        Simulation sim = new Simulation(TimeDelta.seconds(0.1));
        Edge source = sim.network.freeComponent(new VoltageSource(Voltage.volts(1.0)));
        Edge resistor = sim.network.connectComponent(source.terminals, new Resistor(Resistance.ohms(2.0)));
        sim.step();
        assertEquals(Voltage.volts(1.0), sim.getCurrentState().getVoltage(source.terminals));
        assertEquals(Current.ampers(0.5), sim.getCurrentState().getCurrent(source));
        assertEquals(Voltage.volts(1.0), sim.getCurrentState().getVoltage(resistor.terminals));
        assertEquals(Current.ampers(-0.5), sim.getCurrentState().getCurrent(resistor));
    }
    
    @Test
    public void testSimpleCapacitorCircuit() {
        Simulation sim = new Simulation(TimeDelta.seconds(0.1));
        Edge source = sim.network.freeComponent(new VoltageSource(Voltage.volts(1.0)));
        Edge capacitor = sim.network.connectComponent(source.terminals, new Capacitor(Capacitance.farrads(1.0)));
        sim.step();
        assertEquals(Voltage.volts(1.0), sim.getCurrentState().getVoltage(source.terminals));
        assertEquals(Current.ampers(10.0), sim.getCurrentState().getCurrent(source));
        assertEquals(Voltage.volts(1.0), sim.getCurrentState().getVoltage(capacitor.terminals));
        assertEquals(Current.ampers(-10.0), sim.getCurrentState().getCurrent(capacitor));
        sim.step();
        assertEquals(Voltage.volts(1.0), sim.getCurrentState().getVoltage(source.terminals));
        assertEquals(Current.ampers(-10.0), sim.getCurrentState().getCurrent(source));
        assertEquals(Voltage.volts(1.0), sim.getCurrentState().getVoltage(capacitor.terminals));
        assertEquals(Current.ampers(10.0), sim.getCurrentState().getCurrent(capacitor));
        sim.step();
        assertEquals(Voltage.volts(1.0), sim.getCurrentState().getVoltage(source.terminals));
        assertEquals(Current.ampers(0.0), sim.getCurrentState().getCurrent(source));
        assertEquals(Voltage.volts(1.0), sim.getCurrentState().getVoltage(capacitor.terminals));
        assertEquals(Current.ampers(0.0), sim.getCurrentState().getCurrent(capacitor));
    }
    
    @Test
    public void testSimpleInductorCircuit() {
        Simulation sim = new Simulation(TimeDelta.seconds(0.1));
        Edge source = sim.network.freeComponent(new VoltageSource(Voltage.volts(1.0)));
        Edge inductor = sim.network.connectComponent(source.terminals, new Inductor(Inductance.henrs(1.0)));
        assertEquals(Voltage.volts(0.0), sim.getCurrentState().getVoltage(source.terminals));
        assertEquals(Current.ampers(0.0), sim.getCurrentState().getCurrent(source));
        assertEquals(Voltage.volts(0.0), sim.getCurrentState().getVoltage(inductor.terminals));
        assertEquals(Current.ampers(0.0), sim.getCurrentState().getCurrent(inductor));
        sim.step();
        assertEquals(Voltage.volts(1.0), sim.getCurrentState().getVoltage(source.terminals));
        assertEquals(Current.ampers(0.1), sim.getCurrentState().getCurrent(source));
        assertEquals(Voltage.volts(1.0), sim.getCurrentState().getVoltage(inductor.terminals));
        assertEquals(Current.ampers(-0.1), sim.getCurrentState().getCurrent(inductor));
        sim.step();
        assertEquals(Voltage.volts(1.0), sim.getCurrentState().getVoltage(source.terminals));
        assertEquals(Current.ampers(0.2), sim.getCurrentState().getCurrent(source));
        assertEquals(Voltage.volts(1.0), sim.getCurrentState().getVoltage(inductor.terminals));
        assertEquals(Current.ampers(-0.2), sim.getCurrentState().getCurrent(inductor));
        sim.step();
        assertEquals(Voltage.volts(1.0), sim.getCurrentState().getVoltage(source.terminals));
        assertEquals(Current.ampers(0.31), sim.getCurrentState().getCurrent(source));
        assertEquals(Voltage.volts(1.0), sim.getCurrentState().getVoltage(inductor.terminals));
        assertEquals(Current.ampers(-0.31), sim.getCurrentState().getCurrent(inductor));
    }
    
    @Test
    public void testResistorParallel() {
        Simulation sim = new Simulation(TimeDelta.seconds(0.1));
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
        Simulation sim = new Simulation(TimeDelta.seconds(0.1));
        Edge source = sim.network.freeComponent(new VoltageSource(Voltage.volts(6.0)));
        Edge capacitor1 = sim.network.connectComponent(source.terminals, new Capacitor(Capacitance.farrads(2)));
        Edge capacitor2 = sim.network.connectComponent(source.terminals, new Capacitor(Capacitance.farrads(1)));
        sim.step();
        assertEquals(Voltage.volts(6.0), sim.getCurrentState().getVoltage(source.terminals));
        assertEquals(Current.ampers(90.0), sim.getCurrentState().getCurrent(source));
        assertEquals(Voltage.volts(6.0), sim.getCurrentState().getVoltage(capacitor1.terminals));
        assertEquals(Current.ampers(-30.0), sim.getCurrentState().getCurrent(capacitor1));
        assertEquals(Voltage.volts(6.0), sim.getCurrentState().getVoltage(capacitor2.terminals));
        assertEquals(Current.ampers(-60.0), sim.getCurrentState().getCurrent(capacitor2));
        sim.step();
        assertEquals(Voltage.volts(6.0), sim.getCurrentState().getVoltage(source.terminals));
        assertEquals(Current.ampers(-90.0), sim.getCurrentState().getCurrent(source));
        assertEquals(Voltage.volts(6.0), sim.getCurrentState().getVoltage(capacitor1.terminals));
        assertEquals(Current.ampers(30.0), sim.getCurrentState().getCurrent(capacitor1));
        assertEquals(Voltage.volts(6.0), sim.getCurrentState().getVoltage(capacitor2.terminals));
        assertEquals(Current.ampers(60.0), sim.getCurrentState().getCurrent(capacitor2));
        sim.step();
        assertEquals(Voltage.volts(6.0), sim.getCurrentState().getVoltage(source.terminals));
        assertEquals(Current.ampers(0.0), sim.getCurrentState().getCurrent(source));
        assertEquals(Voltage.volts(6.0), sim.getCurrentState().getVoltage(capacitor1.terminals));
        assertEquals(Current.ampers(0.0), sim.getCurrentState().getCurrent(capacitor1));
        assertEquals(Voltage.volts(6.0), sim.getCurrentState().getVoltage(capacitor2.terminals));
        assertEquals(Current.ampers(0.0), sim.getCurrentState().getCurrent(capacitor2));
    }
    
    @Test
    public void testInductorParallel() {
        Simulation sim = new Simulation(TimeDelta.seconds(0.1));
        Edge source = sim.network.freeComponent(new VoltageSource(Voltage.volts(1.0)));
        Edge inductor1 = sim.network.connectComponent(source.terminals, new Inductor(Inductance.henrs(2.0)));
        Edge inductor2 = sim.network.connectComponent(source.terminals, new Inductor(Inductance.henrs(1.0)));
        sim.step();
        assertEquals(Voltage.volts(1.0), sim.getCurrentState().getVoltage(source.terminals));
        assertEquals(Current.ampers(0.15), sim.getCurrentState().getCurrent(source));
        assertEquals(Voltage.volts(1.0), sim.getCurrentState().getVoltage(inductor1.terminals));
        assertEquals(Current.ampers(-0.05), sim.getCurrentState().getCurrent(inductor1));
        assertEquals(Voltage.volts(1.0), sim.getCurrentState().getVoltage(inductor2.terminals));
        assertEquals(Current.ampers(-0.1), sim.getCurrentState().getCurrent(inductor2));
        sim.step();
        assertEquals(Voltage.volts(1.0), sim.getCurrentState().getVoltage(source.terminals));
        assertEquals(Current.ampers(0.3), sim.getCurrentState().getCurrent(source));
        assertEquals(Voltage.volts(1.0), sim.getCurrentState().getVoltage(inductor1.terminals));
        assertEquals(Current.ampers(-0.1), sim.getCurrentState().getCurrent(inductor1));
        assertEquals(Voltage.volts(1.0), sim.getCurrentState().getVoltage(inductor2.terminals));
        assertEquals(Current.ampers(-0.2), sim.getCurrentState().getCurrent(inductor2));
        sim.step();
        assertEquals(Voltage.volts(1.0), sim.getCurrentState().getVoltage(source.terminals));
        assertEquals(Current.ampers(0.465), sim.getCurrentState().getCurrent(source));
        assertEquals(Voltage.volts(1.0), sim.getCurrentState().getVoltage(inductor1.terminals));
        assertEquals(Current.ampers(-0.155), sim.getCurrentState().getCurrent(inductor1));
        assertEquals(Voltage.volts(1.0), sim.getCurrentState().getVoltage(inductor2.terminals));
        assertEquals(Current.ampers(-0.310), sim.getCurrentState().getCurrent(inductor2));
    }
    
    @Test
    public void testResistorSeries() {
        Simulation sim = new Simulation(TimeDelta.seconds(0.1));
        Edge source = sim.network.freeComponent(new VoltageSource(Voltage.volts(6.0)));
        Edge resistor1 = sim.network.connectComponentFrom(
                source.terminals.first, new Resistor(Resistance.ohms(2.0)));
        Edge resistor2 = sim.network.connectComponent(
                resistor1.terminals.second, source.terminals.second, new Resistor(Resistance.ohms(4.0)));
        sim.step();
        assertEquals(Voltage.volts(6.0), sim.getCurrentState().getVoltage(source.terminals));
        assertEquals(Current.ampers(1.0), sim.getCurrentState().getCurrent(source));
        assertEquals(Voltage.volts(2.0), sim.getCurrentState().getVoltage(resistor1.terminals));
        assertEquals(Current.ampers(-1.0), sim.getCurrentState().getCurrent(resistor1));
        assertEquals(Voltage.volts(4.0), sim.getCurrentState().getVoltage(resistor2.terminals));
        assertEquals(Current.ampers(-1.0), sim.getCurrentState().getCurrent(resistor2));
    }
    
    @Test
    public void testCapacitorSeries() {
        Simulation sim = new Simulation(TimeDelta.seconds(0.1));
        Edge source = sim.network.freeComponent(new VoltageSource(Voltage.volts(6.0)));
        Edge capacitor1 = sim.network.connectComponentFrom(
                source.terminals.first, new Capacitor(Capacitance.farrads(2)));
        Edge capacitor2 = sim.network.connectComponent(
                capacitor1.terminals.second, source.terminals.second, new Capacitor(Capacitance.farrads(1)));
        sim.step();
        assertEquals(Voltage.volts(6.0), sim.getCurrentState().getVoltage(source.terminals));
        assertEquals(Current.ampers(20.0), sim.getCurrentState().getCurrent(source));
        assertEquals(Voltage.volts(4.0), sim.getCurrentState().getVoltage(capacitor1.terminals));
        assertEquals(Current.ampers(-20.0), sim.getCurrentState().getCurrent(capacitor1));
        assertEquals(Voltage.volts(2.0), sim.getCurrentState().getVoltage(capacitor2.terminals));
        assertEquals(Current.ampers(-20.0), sim.getCurrentState().getCurrent(capacitor2));
        sim.step();
        assertEquals(Voltage.volts(6.0), sim.getCurrentState().getVoltage(source.terminals));
        assertEquals(Current.ampers(-20.0), sim.getCurrentState().getCurrent(source));
        assertEquals(Voltage.volts(4.0), sim.getCurrentState().getVoltage(capacitor1.terminals));
        assertEquals(Current.ampers(20.0), sim.getCurrentState().getCurrent(capacitor1));
        assertEquals(Voltage.volts(2.0), sim.getCurrentState().getVoltage(capacitor2.terminals));
        assertEquals(Current.ampers(20.0), sim.getCurrentState().getCurrent(capacitor2));
        sim.step();
        assertEquals(Voltage.volts(6.0), sim.getCurrentState().getVoltage(source.terminals));
        assertEquals(Current.ampers(0.0), sim.getCurrentState().getCurrent(source));
        assertEquals(Voltage.volts(4.0), sim.getCurrentState().getVoltage(capacitor1.terminals));
        assertEquals(Current.ampers(0.0), sim.getCurrentState().getCurrent(capacitor1));
        assertEquals(Voltage.volts(2.0), sim.getCurrentState().getVoltage(capacitor2.terminals));
        assertEquals(Current.ampers(0.0), sim.getCurrentState().getCurrent(capacitor2));
    }
    @Test
    public void testInductorSeries() {
        Simulation sim = new Simulation(TimeDelta.seconds(0.1));
        Edge source = sim.network.freeComponent(new VoltageSource(Voltage.volts(1.0)));
        Edge inductor1 = sim.network.connectComponentFrom(
                source.terminals.first, new Inductor(Inductance.henrs(3)));
        Edge inductor2 = sim.network.connectComponent(
                inductor1.terminals.second, source.terminals.second, new Inductor(Inductance.henrs(1)));
        sim.step();
        assertEquals(Voltage.volts(1.0), sim.getCurrentState().getVoltage(source.terminals));
        assertEquals(Current.ampers(0.025), sim.getCurrentState().getCurrent(source));
        assertEquals(Voltage.volts(0.75), sim.getCurrentState().getVoltage(inductor1.terminals));
        assertEquals(Current.ampers(-0.025), sim.getCurrentState().getCurrent(inductor1));
        assertEquals(Voltage.volts(0.25), sim.getCurrentState().getVoltage(inductor2.terminals));
        assertEquals(Current.ampers(-0.025), sim.getCurrentState().getCurrent(inductor2));
        sim.step();
        assertEquals(Voltage.volts(1.0), sim.getCurrentState().getVoltage(source.terminals));
        assertEquals(Current.ampers(0.05), sim.getCurrentState().getCurrent(source));
        assertEquals(Voltage.volts(0.75), sim.getCurrentState().getVoltage(inductor1.terminals));
        assertEquals(Current.ampers(-0.05), sim.getCurrentState().getCurrent(inductor1));
        assertEquals(Voltage.volts(0.25), sim.getCurrentState().getVoltage(inductor2.terminals));
        assertEquals(Current.ampers(-0.05), sim.getCurrentState().getCurrent(inductor2));
        sim.step();
        assertEquals(Voltage.volts(1.0), sim.getCurrentState().getVoltage(source.terminals));
        assertEquals(Current.ampers(0.0775), sim.getCurrentState().getCurrent(source));
        assertEquals(Voltage.volts(0.75), sim.getCurrentState().getVoltage(inductor1.terminals));
        assertEquals(Current.ampers(-0.0775), sim.getCurrentState().getCurrent(inductor1));
        assertEquals(Voltage.volts(0.25), sim.getCurrentState().getVoltage(inductor2.terminals));
        assertEquals(Current.ampers(-0.0775), sim.getCurrentState().getCurrent(inductor2));
    }
    @Test
    public void testResistorCapacitorSeries() {
        Simulation sim = new Simulation(TimeDelta.seconds(0.1));
        Edge source = sim.network.freeComponent(new VoltageSource(Voltage.volts(1.0)));
        Edge capacitor = sim.network.connectComponentFrom(
                source.terminals.first, new Capacitor(Capacitance.farrads(1)));
        Edge resistor = sim.network.connectComponent(
                source.terminals.second, capacitor.terminals.second, new Resistor(Resistance.ohms(0.1)));
        sim.step();
        assertEquals(Voltage.volts(1.0), sim.getCurrentState().getVoltage(source.terminals));
        assertEquals(Current.ampers(5.0), sim.getCurrentState().getCurrent(source));
        assertEquals(Voltage.volts(0.5), sim.getCurrentState().getVoltage(capacitor.terminals));
        assertEquals(Current.ampers(-5.0), sim.getCurrentState().getCurrent(capacitor));
        assertEquals(Voltage.volts(-0.5), sim.getCurrentState().getVoltage(resistor.terminals));
        assertEquals(Current.ampers(5.0), sim.getCurrentState().getCurrent(resistor));
        sim.step();
        assertEquals(Voltage.volts(1.0), sim.getCurrentState().getVoltage(source.terminals));
        assertEquals(Current.ampers(0.0), sim.getCurrentState().getCurrent(source));
        assertEquals(Voltage.volts(1.0), sim.getCurrentState().getVoltage(capacitor.terminals));
        assertEquals(Current.ampers(0.0), sim.getCurrentState().getCurrent(capacitor));
        assertEquals(Voltage.volts(0.0), sim.getCurrentState().getVoltage(resistor.terminals));
        assertEquals(Current.ampers(0.0), sim.getCurrentState().getCurrent(resistor));
        sim.step();
        assertEquals(Voltage.volts(1.0), sim.getCurrentState().getVoltage(source.terminals));
        assertEquals(Current.ampers(-2.5), sim.getCurrentState().getCurrent(source));
        assertEquals(Voltage.volts(1.25), sim.getCurrentState().getVoltage(capacitor.terminals));
        assertEquals(Current.ampers(2.5), sim.getCurrentState().getCurrent(capacitor));
        assertEquals(Voltage.volts(0.25), sim.getCurrentState().getVoltage(resistor.terminals));
        assertEquals(Current.ampers(-2.5), sim.getCurrentState().getCurrent(resistor));
    }
    
    @Test
    public void testResistorInductorSeries() {
        Simulation sim = new Simulation(TimeDelta.seconds(0.1));
        Edge source = sim.network.freeComponent(new VoltageSource(Voltage.volts(1.0)));
        Edge inductor = sim.network.connectComponentFrom(
                source.terminals.first, new Inductor(Inductance.henrs(1)));
        Edge resistor = sim.network.connectComponent(
                inductor.terminals.second, source.terminals.second, new Resistor(Resistance.ohms(10)));
        sim.step();
        assertEquals(Voltage.volts(1.0), sim.getCurrentState().getVoltage(source.terminals));
        assertEquals(Current.ampers(0.05), sim.getCurrentState().getCurrent(source));
        assertEquals(Voltage.volts(0.5), sim.getCurrentState().getVoltage(inductor.terminals));
        assertEquals(Current.ampers(-0.05), sim.getCurrentState().getCurrent(inductor));
        assertEquals(Voltage.volts(0.5), sim.getCurrentState().getVoltage(resistor.terminals));
        assertEquals(Current.ampers(-0.05), sim.getCurrentState().getCurrent(resistor));
        sim.step();
        assertEquals(Voltage.volts(1.0), sim.getCurrentState().getVoltage(source.terminals));
        assertEquals(Current.ampers(0.075), sim.getCurrentState().getCurrent(source));
        assertEquals(Voltage.volts(0.25), sim.getCurrentState().getVoltage(inductor.terminals));
        assertEquals(Current.ampers(-0.075), sim.getCurrentState().getCurrent(inductor));
        assertEquals(Voltage.volts(0.75), sim.getCurrentState().getVoltage(resistor.terminals));
        assertEquals(Current.ampers(-0.075), sim.getCurrentState().getCurrent(resistor));
        sim.step();
        assertEquals(Voltage.volts(1.0), sim.getCurrentState().getVoltage(source.terminals));
        assertEquals(Current.ampers(0.09), sim.getCurrentState().getCurrent(source));
        assertEquals(Voltage.volts(0.1), sim.getCurrentState().getVoltage(inductor.terminals));
        assertEquals(Current.ampers(-0.09), sim.getCurrentState().getCurrent(inductor));
        assertEquals(Voltage.volts(0.9), sim.getCurrentState().getVoltage(resistor.terminals));
        assertEquals(Current.ampers(-0.09), sim.getCurrentState().getCurrent(resistor));
    }
    
    @Test
    public void testCapacitorInductorSeries() {
        Simulation sim = new Simulation(TimeDelta.seconds(0.1));
        Edge source = sim.network.freeComponent(new VoltageSource(Voltage.volts(1.0)));
        Edge inductor = sim.network.connectComponentFrom(
                source.terminals.first, new Inductor(Inductance.henrs(0.1)));
        Edge capacitor = sim.network.connectComponent(
                inductor.terminals.second, source.terminals.second, new Capacitor(Capacitance.farrads(10)));
        sim.step();
        assertEquals(Voltage.volts(1.0), sim.getCurrentState().getVoltage(source.terminals));
        assertEquals(Current.ampers(0.5), sim.getCurrentState().getCurrent(source));
        assertEquals(Voltage.volts(0.5), sim.getCurrentState().getVoltage(inductor.terminals));
        assertEquals(Current.ampers(-0.5), sim.getCurrentState().getCurrent(inductor));
        assertEquals(Voltage.volts(0.5), sim.getCurrentState().getVoltage(capacitor.terminals));
        assertEquals(Current.ampers(-0.5), sim.getCurrentState().getCurrent(capacitor));
        sim.step();
        assertEquals(Voltage.volts(1.0), sim.getCurrentState().getVoltage(source.terminals));
        assertEquals(Current.ampers(0.25), sim.getCurrentState().getCurrent(source));
        assertEquals(Voltage.volts(-0.25), sim.getCurrentState().getVoltage(inductor.terminals));
        assertEquals(Current.ampers(-0.25), sim.getCurrentState().getCurrent(inductor));
        assertEquals(Voltage.volts(1.25), sim.getCurrentState().getVoltage(capacitor.terminals));
        assertEquals(Current.ampers(-0.25), sim.getCurrentState().getCurrent(capacitor));
        sim.step();
        assertEquals(Voltage.volts(1.0), sim.getCurrentState().getVoltage(source.terminals));
        assertEquals(Current.ampers(-0.35), sim.getCurrentState().getCurrent(source));
        assertEquals(Voltage.volts(-0.65), sim.getCurrentState().getVoltage(inductor.terminals));
        assertEquals(Current.ampers(0.35), sim.getCurrentState().getCurrent(inductor));
        assertEquals(Voltage.volts(1.65), sim.getCurrentState().getVoltage(capacitor.terminals));
        assertEquals(Current.ampers(0.35), sim.getCurrentState().getCurrent(capacitor));
    }
    
    @Test
    public void testResistorCapacitorParallel() {
        Simulation sim = new Simulation(TimeDelta.seconds(0.1));
        Edge source = sim.network.freeComponent(new VoltageSource(Voltage.volts(1.0)));
        Edge capacitor = sim.network.connectComponent(source.terminals, new Capacitor(Capacitance.farrads(1)));
        Edge resistor = sim.network.connectComponent(source.terminals, new Resistor(Resistance.ohms(0.1)));
        sim.step();
        assertEquals(Voltage.volts(1.0), sim.getCurrentState().getVoltage(source.terminals));
        assertEquals(Current.ampers(20.0), sim.getCurrentState().getCurrent(source));
        assertEquals(Voltage.volts(1.0), sim.getCurrentState().getVoltage(capacitor.terminals));
        assertEquals(Current.ampers(-10.0), sim.getCurrentState().getCurrent(capacitor));
        assertEquals(Voltage.volts(1.0), sim.getCurrentState().getVoltage(resistor.terminals));
        assertEquals(Current.ampers(-10.0), sim.getCurrentState().getCurrent(resistor));
        sim.step();
        assertEquals(Voltage.volts(1.0), sim.getCurrentState().getVoltage(source.terminals));
        assertEquals(Current.ampers(0.0), sim.getCurrentState().getCurrent(source));
        assertEquals(Voltage.volts(1.0), sim.getCurrentState().getVoltage(capacitor.terminals));
        assertEquals(Current.ampers(10.0), sim.getCurrentState().getCurrent(capacitor));
        assertEquals(Voltage.volts(1.0), sim.getCurrentState().getVoltage(resistor.terminals));
        assertEquals(Current.ampers(-10.0), sim.getCurrentState().getCurrent(resistor));
        sim.step();
        assertEquals(Voltage.volts(1.0), sim.getCurrentState().getVoltage(source.terminals));
        assertEquals(Current.ampers(10.0), sim.getCurrentState().getCurrent(source));
        assertEquals(Voltage.volts(1.0), sim.getCurrentState().getVoltage(capacitor.terminals));
        assertEquals(Current.ampers(0.0), sim.getCurrentState().getCurrent(capacitor));
        assertEquals(Voltage.volts(1.0), sim.getCurrentState().getVoltage(resistor.terminals));
        assertEquals(Current.ampers(-10.0), sim.getCurrentState().getCurrent(resistor));
    }
    
    @Test
    public void testResistorInductorParallel() {
        Simulation sim = new Simulation(TimeDelta.seconds(0.1));
        Edge source = sim.network.freeComponent(new VoltageSource(Voltage.volts(1.0)));
        Edge inductor = sim.network.connectComponent(source.terminals, new Inductor(Inductance.henrs(1)));
        Edge resistor = sim.network.connectComponent(source.terminals, new Resistor(Resistance.ohms(10)));
        sim.step();
        assertEquals(Voltage.volts(1.0), sim.getCurrentState().getVoltage(source.terminals));
        assertEquals(Current.ampers(0.2), sim.getCurrentState().getCurrent(source));
        assertEquals(Voltage.volts(1.0), sim.getCurrentState().getVoltage(inductor.terminals));
        assertEquals(Current.ampers(-0.1), sim.getCurrentState().getCurrent(inductor));
        assertEquals(Voltage.volts(1.0), sim.getCurrentState().getVoltage(resistor.terminals));
        assertEquals(Current.ampers(-0.1), sim.getCurrentState().getCurrent(resistor));
        sim.step();
        assertEquals(Voltage.volts(1.0), sim.getCurrentState().getVoltage(source.terminals));
        assertEquals(Current.ampers(0.3), sim.getCurrentState().getCurrent(source));
        assertEquals(Voltage.volts(1.0), sim.getCurrentState().getVoltage(inductor.terminals));
        assertEquals(Current.ampers(-0.2), sim.getCurrentState().getCurrent(inductor));
        assertEquals(Voltage.volts(1.0), sim.getCurrentState().getVoltage(resistor.terminals));
        assertEquals(Current.ampers(-0.1), sim.getCurrentState().getCurrent(resistor));
        sim.step();
        assertEquals(Voltage.volts(1.0), sim.getCurrentState().getVoltage(source.terminals));
        assertEquals(Current.ampers(0.41), sim.getCurrentState().getCurrent(source));
        assertEquals(Voltage.volts(1.0), sim.getCurrentState().getVoltage(inductor.terminals));
        assertEquals(Current.ampers(-0.31), sim.getCurrentState().getCurrent(inductor));
        assertEquals(Voltage.volts(1.0), sim.getCurrentState().getVoltage(resistor.terminals));
        assertEquals(Current.ampers(-0.1), sim.getCurrentState().getCurrent(resistor));
    }
    
    @Test
    public void testCapacitorInductorParallel() {
        Simulation sim = new Simulation(TimeDelta.seconds(0.1));
        Edge source = sim.network.freeComponent(new VoltageSource(Voltage.volts(1.0)));
        Edge inductor = sim.network.connectComponent(source.terminals, new Inductor(Inductance.henrs(0.1)));
        Edge capacitor = sim.network.connectComponent(source.terminals, new Capacitor(Capacitance.farrads(10)));
        sim.step();
        assertEquals(Voltage.volts(1.0), sim.getCurrentState().getVoltage(source.terminals));
        assertEquals(Current.ampers(2), sim.getCurrentState().getCurrent(source));
        assertEquals(Voltage.volts(1.0), sim.getCurrentState().getVoltage(inductor.terminals));
        assertEquals(Current.ampers(-1.0), sim.getCurrentState().getCurrent(inductor));
        assertEquals(Voltage.volts(1.0), sim.getCurrentState().getVoltage(capacitor.terminals));
        assertEquals(Current.ampers(-1.0), sim.getCurrentState().getCurrent(capacitor));
        sim.step();
        assertEquals(Voltage.volts(1.0), sim.getCurrentState().getVoltage(source.terminals));
        assertEquals(Current.ampers(1.0), sim.getCurrentState().getCurrent(source));
        assertEquals(Voltage.volts(1.0), sim.getCurrentState().getVoltage(inductor.terminals));
        assertEquals(Current.ampers(-2.0), sim.getCurrentState().getCurrent(inductor));
        assertEquals(Voltage.volts(1.0), sim.getCurrentState().getVoltage(capacitor.terminals));
        assertEquals(Current.ampers(1.0), sim.getCurrentState().getCurrent(capacitor));
        sim.step();
        assertEquals(Voltage.volts(1.0), sim.getCurrentState().getVoltage(source.terminals));
        assertEquals(Current.ampers(3.1), sim.getCurrentState().getCurrent(source));
        assertEquals(Voltage.volts(1.0), sim.getCurrentState().getVoltage(inductor.terminals));
        assertEquals(Current.ampers(-3.1), sim.getCurrentState().getCurrent(inductor));
        assertEquals(Voltage.volts(1.0), sim.getCurrentState().getVoltage(capacitor.terminals));
        assertEquals(Current.ampers(0.0), sim.getCurrentState().getCurrent(capacitor));
    }
}
