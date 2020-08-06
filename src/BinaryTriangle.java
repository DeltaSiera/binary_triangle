import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

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
    private static final HashMap<String, Integer> paths = new HashMap<>();                  //keeps all path and their sums
    public static final String INPUT_FILE_NAME = "triangle.txt";                            //txt file where information about triangle is stored
    public static final int INITIAL_SUM = 0;
    public static final String PATH = "";
    public static final String ARROW_SEPARATOR = " -> ";
    public static final String FAILURE_MESSAGE = "Problems with an input file. Please, make sure that input file is created.";

    public static void main(String[] args) {
        int[][] triangle = readTriangleDataFromFile();                                      //2d triangle array of numbers rows
        int startPosX = 0;
        int startPosY = 0;                                                                  //starting positions

        computePathSum(triangle, startPosX, startPosY, INITIAL_SUM, PATH);
        printPathAndValue();
    }

    //Prints path and its maximum value
    private static void printPathAndValue() {
        paths.entrySet()
                .stream()
                .max(Map.Entry.comparingByValue())
                .ifPresent(path -> System.out.printf("Max sum: %d\nPath: %s\n", path.getValue(), path.getKey()));
    }

    //Main method which recursively calculates all possible paths and their sums
    private static void computePathSum(int[][] triangle, int x, int y, int sum, String path) {
        int parentNodeValue = triangle[x][y];                       //takes current parent node's value

        sum += parentNodeValue;                                     //adds current parent node's value  to total sum
        path += parentNodeValue;                                    //adds number to path

        if (x + 1 < triangle.length) {                              //checks whether there are values on next level
            path += ARROW_SEPARATOR;                                //add separator if x coordinate did not reach bottom

            int leftChildNodeValue = triangle[x + 1][y];            //takes left child node's value which is in the downward
            int rightChildNodeValue = triangle[x + 1][y + 1];       //takes right child node's value which is in the diagonal

            boolean isEvenParentNode = isNumberEven(parentNodeValue);
            boolean isEvenLeftChild = isNumberEven(leftChildNodeValue);
            boolean isEvenRightChild = isNumberEven(rightChildNodeValue);

            //parent node is even, left child is not even and right child is not even, then go downwards and diagonally
            if (isEvenParentNode && !isEvenLeftChild && !isEvenRightChild) {
                ++x;                         //x value has to be incremented before, because recursion goes downwards and diagonally
                computePathSum(triangle, x, y, sum, path);
                computePathSum(triangle, x, ++y, sum, path);
            }
            //parent node is not even, left child is even and right child is even, then go downwards and diagonally
            else if (!isEvenParentNode && isEvenLeftChild && isEvenRightChild) {
                ++x;
                computePathSum(triangle, x, y, sum, path);
                computePathSum(triangle, x, ++y, sum, path);
            }
            //parent node is even, left child is not even and right child is even, then go downwards
            else if (isEvenParentNode && !isEvenLeftChild) {
                computePathSum(triangle, ++x, y, sum, path);
            }
            //parent node is even, left child is even and right child is not even, then go diagonally
            else if (isEvenParentNode && !isEvenRightChild) {
                computePathSum(triangle, ++x, ++y, sum, path);
            }
            //parent node is not even, left child is even and right child is not even, then go downwards
            else if (!isEvenParentNode && isEvenLeftChild) {
                computePathSum(triangle, ++x, y, sum, path);
            }
            //parent node is not even, left child is not even and right child is even, then go diagonally
            else if (!isEvenParentNode && isEvenRightChild) {
                computePathSum(triangle, ++x, ++y, sum, path);
            }
        } else {
            paths.put(path, sum);
        }
    }

    // This  method reads triangle numbers rows from txt file triangle.txt and returns 2d array of int elements
    private static int[][] readTriangleDataFromFile() {
        ArrayList<List<String>> triangleNumbersRows = new ArrayList<>();              //keeps triangle grid of numbers
        List<String> numbersRow;                                                      //keeps one line of triangle numbers row

        try (BufferedReader br = new BufferedReader(new FileReader(INPUT_FILE_NAME))) {
            String line;                                                              //temporary line to read line of numbers
            while ((line = br.readLine()) != null) {
                numbersRow = Arrays.asList(line.split(" "));                    //gets list of numbers after splitting string line of numbers
                triangleNumbersRows.add(numbersRow);
            }
        } catch (IOException e) {
            System.out.println(FAILURE_MESSAGE);
        }

        return parseArray(triangleNumbersRows);
    }

    // Method converts List of Lists to 2d array of int primitives
    private static int[][] parseArray(ArrayList<List<String>> triangleNumbersRows) {
        return triangleNumbersRows.stream()
                .map(numbersRow -> numbersRow.stream()
                        .mapToInt(Integer::parseInt)                //maps string number to int number
                        .toArray())
                .toArray(int[][]::new);
    }

    //Checks whether numbers is seven or not
    private static boolean isNumberEven(int number) {
        return number % 2 == 0;
    }
}
