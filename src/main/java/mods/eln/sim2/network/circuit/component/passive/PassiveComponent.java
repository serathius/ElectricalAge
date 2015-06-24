package mods.eln.sim2.network.circuit.component.passive;

import mods.eln.sim2.mna.CircutEquasion;
import mods.eln.sim2.network.circuit.Edge;
import mods.eln.sim2.network.circuit.component.Component;
import mods.eln.sim2.primitive.Resistance;

public abstract class PassiveComponent extends Component {
    @Override
    public void registerInEquasion(final CircutEquasion equasion, final Edge edge) {
        equasion.addPassiveComponent(edge, edge.terminals, dynamicResistance());
    }
    
    public abstract Resistance dynamicResistance();
}
