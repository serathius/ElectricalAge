package mods.eln.sim2.utils;


public class Pair<A, B> {
    public final A first;
    public final B second;
    
    public Pair(final A first, final B second) {
        assert first != second;
        this.first = first;
        this.second = second;
    }
    
    public boolean equals(Object object) {
        if (object instanceof Pair) {
            Pair other = (Pair) object;
            return (first == other.first && second == other.second) || 
                    (first == other.second && second == other.first);
        } 
        else {
            return false;
        }
    }
    
    public int hashCode() {
        return first.hashCode() + second.hashCode();
    }
}
