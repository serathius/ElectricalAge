package mods.eln.sim2.network.circuit.component;

import mods.eln.sim2.mna.CircutEquasion;
import mods.eln.sim2.network.circuit.Edge;
import mods.eln.sim2.primitive.TimeDelta;

public abstract class Component {
    public abstract void registerInEquasion(final CircutEquasion equasion, final Edge edge);
}
