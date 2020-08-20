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
    public static final String INPUT_FILE_FAILURE = "Problems with input file.";
    public static final String INPUT_FILE_NAME = "src\\triangle.txt";
    private static final ArrayList<Node> NODES = new ArrayList<>();
    public static final int COLUMN = 0;
    public static final int ROW = 0;
    public static final String CHECK_DATA_MESSAGE = "Check data.";

    public static void main(String[] args) {
        try {
            List<String> rowsList = readDataFromFile(INPUT_FILE_NAME);
            int[][] triangle = mapListTo2DArray(rowsList);
            computePathSum(triangle, new Node(triangle[ROW][COLUMN]));
            printPathAndValue();
        } catch (IOException exception) {
            System.out.println(INPUT_FILE_FAILURE);
        } catch (NumberFormatException numberFormatException) {
            System.out.println(CHECK_DATA_MESSAGE);
        }
    }

    public static void computePathSum(final int[][] triangle, final Node node) {
        int row = node.getX();
        int column = node.getY();
        row++;

        if (row < triangle.length) {
            Node left = new Node(row, column, triangle[row][column], node);
            if (node.canGoFurther(left)) {
                computePathSum(triangle, left);
            }
            column++;
            Node right = new Node(row, column, triangle[row][column], node);
            if (node.canGoFurther(right)) {
                computePathSum(triangle, right);
            }
        } else {
            NODES.add(node);
        }
    }

    private static int[] mapStringNumbersRowToIntArray(final String numbersRow) throws NumberFormatException {
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

    public static int[][] mapListTo2DArray(final List<String> rowsList) {
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
