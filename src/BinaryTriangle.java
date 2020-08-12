import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/*
 * The main idea is to imagine this triangle as coordinate plane;
 * Each number has its own coordinates (x, y);
 * Starting coordinate is (0; 0);
 * Moving downwards is a X axis;
 * Moving rightwards is a Y axis;
 * Moving diagonally is a y=x function;
 * Left child node's coordinate is (x+1;y);
 * Right child node's coordinate is (x+1; y+1);
 */

public class BinaryTriangle {
    public static final String INPUT_FILE_NAME = "src\\triangle.txt";
    private static final ArrayList<Node> NODES = new ArrayList<>();
    public static final String INPUT_FILE_FAILURE = "Problems with input file.";

    public static void main(String[] args) throws CloneNotSupportedException {
        try {
            List<String> rowsList = readDataFromFile(INPUT_FILE_NAME);
            int[][] triangle = mapListTo2DArray(rowsList);
            computePathSum(triangle, new Node());
            printPathAndValue();
        } catch (IOException exception) {
            System.out.println(INPUT_FILE_FAILURE);
        } catch (NumberFormatException numberFormatException) {
            System.out.println("Check data.");
        }
    }

    public static void computePathSum(final int[][] triangle, Node node) throws CloneNotSupportedException {
        Node currentNode = node.clone();
        int parentNode = triangle.length > 0 ? triangle[currentNode.getX()][currentNode.getY()] : 0;
        currentNode.addNodeValue(parentNode);
        if (currentNode.getX() + 1 < triangle.length) {
            currentNode.incrementX();
            int leftChildNode = triangle[currentNode.getX()][currentNode.getY()];
            if ((areEvenOddBetweenSelf(parentNode, leftChildNode))) {
                computePathSum(triangle, currentNode.clone());
            }
            currentNode.incrementY();
            int rightChildNode = triangle[node.getX()][node.getY()];
            if (areEvenOddBetweenSelf(parentNode, rightChildNode)) {
                computePathSum(triangle, node.clone());
            }
        } else {
            NODES.add(node);
        }
    }

    private static int[] mapStringNumbersRowToIntArray(String numbersRow) throws NumberFormatException {
        return Stream.of(numbersRow.split(" "))
                     .mapToInt(Integer::parseInt)
                     .toArray();
    }

    public static List<String> readDataFromFile(final String fileName) throws IOException {
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(fileName))) {
            return bufferedReader.lines()
                                 .collect(Collectors.toList());
        }
    }

    public static int[][] mapListTo2DArray(List<String> rowsList) {
        return rowsList.stream()
                       .map(BinaryTriangle::mapStringNumbersRowToIntArray)
                       .toArray(int[][]::new);
    }

    private static void printPathAndValue() {
        NODES.stream()
             .max(Comparator.comparingInt(Node::getPathValuesSum))
             .ifPresent(System.out::println);
    }

    public static boolean areEvenOddBetweenSelf(int a, int b) {
        return areEvenOdd(a, b) || areEvenOdd(b, a);
    }

    protected static boolean areEvenOdd(int a, int b) {
        return isEven(a) && !isEven(b);
    }

    protected static boolean isEven(final int number) {
        return number % 2 == 0;
    }
}
