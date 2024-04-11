package ca.mcmaster.se2aa4.mazerunner;
import java.util.HashMap;
import java.util.Map;

public class PathTracker {
    private Map<Position, Position> childParentMap;

    //map containing relations between the different nodes

    public PathTracker() {
        this.childParentMap = new HashMap<>();
    }

    public void addChildParentPair(Position child, Position parent) {
        childParentMap.put(child, parent);
    }

    public boolean containsChild(Position child) {
        return childParentMap.containsKey(child);
    }

    public Position getParent(Position child) {
        return childParentMap.get(child);
    }
}
