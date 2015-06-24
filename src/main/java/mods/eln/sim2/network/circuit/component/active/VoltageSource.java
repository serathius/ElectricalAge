package mods.eln.sim2.network.circuit.component.active;

import mods.eln.sim2.mna.CircutEquasion;
import mods.eln.sim2.network.circuit.Edge;
import mods.eln.sim2.network.circuit.Node;
import mods.eln.sim2.network.circuit.component.Component;
import mods.eln.sim2.primitive.Voltage;

public class VoltageSource extends Component {
    final Voltage _voltage;
    
    public VoltageSource(final Voltage voltage) {
        _voltage = voltage;
    }

    @Override
    public void registerInEquasion(final CircutEquasion equasion, final Edge edge) {
        equasion.addVoltageSource(edge.terminals, _voltage);
    }
}
