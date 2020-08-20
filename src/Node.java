import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;


public class Node {
    private final List<Integer> pathValues;
    private int totalPathSum = 0;
    private final int nodeValue;
    private int x = 0;
    private int y = 0;

    public Node(int x, int y, int nodeValue, Node node) {
        this.x = x;
        this.y = y;
        this.nodeValue = nodeValue;
        this.totalPathSum = node.totalPathSum;
        this.totalPathSum += nodeValue;
        this.pathValues = new ArrayList<>(node.pathValues);
        pathValues.add(nodeValue);
    }

    public Node(int nodeValue) {
        this.nodeValue = nodeValue;
        totalPathSum += nodeValue;
        pathValues = new ArrayList<>(Collections.singletonList(nodeValue));
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public boolean canGoFurther(Node node) {
        return BinaryTriangle.areEvenOddBetweenSelf(nodeValue, node.nodeValue);
    }

    public int getPathValuesSum() {
        return totalPathSum;
    }

    public String getPath() {
        return pathValues.stream()
                         .map(String::valueOf)
                         .collect(Collectors.joining("->"));
    }

    @Override
    public String toString() {
        return String.format("Sum: %d\nPath: %s", totalPathSum, getPath());
    }
}
