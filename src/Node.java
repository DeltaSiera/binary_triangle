import java.util.ArrayList;
import java.util.stream.Collectors;

public class Node implements Cloneable {
    private ArrayList<Integer> pathValues;
    private int pathValuesSum;
    int x, y;

    public Node() {
        pathValues = new ArrayList<>();
    }

    public void addNodeValue(int value) {
        pathValues.add(value);
        pathValuesSum += value;
    }

    public int getPathValuesSum() {
        return pathValuesSum;
    }

    public String getPath() {
        return pathValues.stream()
                         .map(String::valueOf)
                         .collect(Collectors.joining("->"));
    }

    @Override
    protected Node clone() throws CloneNotSupportedException {
        Node node = (Node) super.clone();
        node.pathValues = new ArrayList<>(pathValues);
        return node;
    }

    @Override
    public String toString() {
        return String.format("Sum: %d\nPath: %s", getPathValuesSum(), getPath());
    }
}
