package mods.eln.sim2.mna;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import org.apache.commons.math3.linear.Array2DRowRealMatrix;
import org.apache.commons.math3.linear.OpenMapRealMatrix;
import org.apache.commons.math3.linear.QRDecomposition;
import org.apache.commons.math3.linear.RealMatrix;
import org.apache.commons.math3.linear.SingularMatrixException;

import mods.eln.sim2.network.NetworkState;
import mods.eln.sim2.network.circuit.Edge;
import mods.eln.sim2.network.circuit.Node;
import mods.eln.sim2.network.circuit.Terminals;
import mods.eln.sim2.primitive.Resistance;
import mods.eln.sim2.primitive.Voltage;
import mods.eln.sim2.utils.DefaultHashMap;

public class CircutEquasion {
    private final List<DefaultHashMap<Unknown, Double>> _A;
    private final List<Double> _B;
    
    public CircutEquasion() {
        _A = new LinkedList<DefaultHashMap<Unknown, Double>>();
        _B = new LinkedList<Double>();
    }
    
    public Map<Unknown, Double> solve() {
        List<Unknown> X = getX();
        RealMatrix A = getMatrixA(X);
        RealMatrix invertedA;
        try {
            invertedA = new QRDecomposition(A).getSolver().getInverse();
        } 
        catch (SingularMatrixException e) {
            throw e;
        }
        RealMatrix X_values = invertedA.multiply(getMatrixB());
        Map<Unknown, Double> result = new HashMap<Unknown, Double>();
        int result_index = 0;
        for (Unknown unknown: X) {
            result.put(unknown, X_values.getEntry(result_index, 0));
            result_index++;
        }
        return result;
    }
    
    private List<Unknown> getX() {
        Set<Unknown> X = new HashSet<Unknown>();
        for (Map<Unknown, Double> row: _A) {
            X.addAll(row.keySet());
        }
        return new LinkedList<Unknown>(X);
    }
    
    private RealMatrix getMatrixA(final List<Unknown> X) {
        assert _A.size() == X.size();
        RealMatrix matrix = new Array2DRowRealMatrix(_A.size(), X.size());
        int unknown_index = 0;
        for (Unknown unknown: X) {
            int row_index = 0;
            for (Map<Unknown, Double> row: _A) {
                matrix.setEntry(row_index, unknown_index, row.get(unknown));
                row_index++;
            }
            unknown_index++;
        }
        return matrix;
    }
    
    private RealMatrix getMatrixB() {
        RealMatrix matrix = new Array2DRowRealMatrix(_B.size(), 1);
        int row_index = 0;
        for (Double value: _B) {
            matrix.setEntry(row_index, 0, value);
            row_index++;
        }
        return matrix;
    }
    
    public void addPassiveComponent(final Edge current, final Terminals voltage_terminals, final Resistance resistance, final Voltage voltage) {
        DefaultHashMap<Unknown, Double> row = new DefaultHashMap<Unknown, Double>(0.0);
        row.put(current, -resistance.getValue());
        row.put((Unknown) voltage_terminals.first, 1.0);
        row.put((Unknown) voltage_terminals.second, -1.0);
        _A.add(row);
        _B.add(voltage.getValue());
    }
    
    public void addVoltageSource(final Terminals voltage_terminals, final Voltage voltage) {
        DefaultHashMap<Unknown, Double> row = new DefaultHashMap<Unknown, Double>(0.0);
        row.put((Unknown) voltage_terminals.first, -1.0);
        row.put((Unknown) voltage_terminals.second, 1.0);
        _A.add(row);
        _B.add(voltage.getValue());
    }

    public void setUnknown(final Unknown unknown, double value) {
        DefaultHashMap<Unknown, Double> row = new DefaultHashMap<Unknown, Double>(0.0);
        row.put(unknown, -1.0);
        _A.add(row);
        _B.add(value);
        
    }

    public void addNodeWithCurents(final Node node, final List<Edge> currents) {
        DefaultHashMap<Unknown, Double> row = new DefaultHashMap<Unknown, Double>(0.0);
        for (Edge edge: currents){
            if(edge.terminals.first == node) {
                row.put(edge, 1.0);
            }
            else if (edge.terminals.second == node) {
                row.put(edge, -1.0);
            }
            else {
                assert false;
            }
        }
        _A.add(row);
        _B.add(0.0);
    }
}
