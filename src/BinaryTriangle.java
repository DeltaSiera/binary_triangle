import exception.InputDataException;
import exception.PositiveNumberException;

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
    private static final HashMap<String, Integer> paths = new HashMap<>();                  // keeps all path and their sums
    public static final String INPUT_FILE_NAME = "triangle.txt";                            // txt file where information about triangle is stored
    public static final int INITIAL_SUM = 0;
    public static final String PATH = "";
    public static final String ARROW_SEPARATOR = " -> ";
    public static final String FAILURE_MESSAGE = "Problems with an input file. Please, make sure that input file is created.";
    public static final String WRONG_INPUT_DATA_MESSAGE = "Wrong input data, check it again.";
    public static final String POSITIVE_NUMBERS_ARE_ALLOWED_MESSAGE = "Only positive numbers are allowed.";
    public static final String NUMERIC_SYMBOLS_ARE_ALLOWED_MESSAGE = "Only numeric symbols are allowed.";

    public static void main(String[] args) {
        final ArrayList<List<Integer>> triangle = readTriangleDataFromFile(INPUT_FILE_NAME);                                // 2d triangle array of numbers rows
        int startPosX = 0;                                                                                                  // starting positions
        int startPosY = 0;

        computePathSum(triangle, startPosX, startPosY, INITIAL_SUM, PATH);
        printPathAndValue();
    }

    // Prints path and its maximum value
    private static void printPathAndValue() {
        paths.entrySet()
                .stream()
                .max(Map.Entry.comparingByValue())
                .ifPresent(path -> System.out.printf("Max sum: %d\nPath: %s\n", path.getValue(), path.getKey()));
    }

    // Main method which recursively calculates all possible paths and their sums
    public static void computePathSum(final ArrayList<List<Integer>> triangle, int x, int y, int sum, String path) {
        int parentNodeValue = triangle.size() > 0 ? triangle.get(x).get(y) : 0;     // takes current parent node's value or zero if  2d array is empty

        sum += parentNodeValue;                                                     // adds current parent node's value to total sum
        path += parentNodeValue;                                                    // adds number to path

        if (x + 1 < triangle.size()) {                                              // checks whether there are values on next row of numbers
            path += ARROW_SEPARATOR;                                                // add separator if x coordinate did not reach bottom

            int leftChildNodeValue = triangle.get(x + 1).get(y);                    // takes left child node's value which is in the downward
            int rightChildNodeValue = triangle.get(x + 1).get(y + 1);               // takes right child node's value which is in the diagonal

            boolean isEvenParentNode = isNumberEven(parentNodeValue);               // find if parent node's value is even number or no
            boolean isEvenLeftChildNode = isNumberEven(leftChildNodeValue);         // find if left child node's value is even number or no
            boolean isEvenRightChildNode = isNumberEven(rightChildNodeValue);       // find if right child node's value is even number or no

            // parent node is even, left child is not even and right child is not even
            // or parent node is not even, left child is even and right child is even
            // then go downwards and diagonally
            if ((isEvenParentNode && !isEvenLeftChildNode && !isEvenRightChildNode) ||
                    (!isEvenParentNode && isEvenLeftChildNode && isEvenRightChildNode)) {
                ++x;                            // x value has to be incremented before, because recursion goes downwards and diagonally
                computePathSum(triangle, x, y, sum, path);
                computePathSum(triangle, x, ++y, sum, path);
            }
            // parent node is even, left child is not even and right child is even,
            // or parent node is not even, left child is even and right child is not even
            // then go downwards
            else if ((isEvenParentNode && !isEvenLeftChildNode) ||
                    (!isEvenParentNode && isEvenLeftChildNode)) {
                computePathSum(triangle, ++x, y, sum, path);
            }
            // parent node is even, left child is even and right child is not even
            // or parent node is not even, left child is not even and right child is even
            // then go diagonally
            else if ((isEvenParentNode && !isEvenRightChildNode) ||
                    (!isEvenParentNode && isEvenRightChildNode)) {
                computePathSum(triangle, ++x, ++y, sum, path);
            }
        } else {
            paths.put(path, sum);            // put final sum and answer to map
        }
    }

    public static ArrayList<List<Integer>> readTriangleDataFromFile(final String fileName) {
        ArrayList<List<Integer>> triangleNumbersRows = new ArrayList<>();                               // keeps triangle grid of numbers
        List<Integer> numbersRow = new ArrayList<>();                                                   // keeps one line of triangle numbers row

        int numbersPerLineQuantity = 1;                                                                 // 1-st line has one number, 2-nd - two ...
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String numbersLine;                                                                         // temporary line to read line of numbers
            while ((numbersLine = br.readLine()) != null) {
                String[] numbers = numbersLine.split(" ");
                numbersRow = createNumbersRowList(numbersPerLineQuantity, numbers);
                numbersPerLineQuantity++;
                triangleNumbersRows.add(numbersRow);
            }
        } catch (IOException ioException) {
            System.out.println(FAILURE_MESSAGE);
            System.out.println(ioException.getMessage());
        } catch (InputDataException | PositiveNumberException exception) {
            System.out.println(exception.getMessage());
            return new ArrayList<List<Integer>>();
        }

        return triangleNumbersRows;
    }

    protected static List<Integer> createNumbersRowList(int numbersPerLineQuantity, String[] numbers) throws InputDataException, PositiveNumberException {
        List<Integer> numbersRow = new ArrayList<>();
        for (String number : numbers) {
            if (numbers.length > numbersPerLineQuantity ||
                    numbers.length < numbersPerLineQuantity) {
                throw new InputDataException(WRONG_INPUT_DATA_MESSAGE);
            } else if (!isNumericAndPositive(number)) {
                throw new PositiveNumberException(NUMERIC_SYMBOLS_ARE_ALLOWED_MESSAGE);
            } else {
                numbersRow.add(Integer.parseInt(number));
            }
        }
        return numbersRow;
    }

    // Check if given string number is numeric and positive
    public static boolean isNumericAndPositive(final String stringNumber) {
        if (stringNumber == null) {
            return false;
        }
        try {
            int parseNumber = Integer.parseInt(stringNumber);
            if (parseNumber < 1) {
                throw new PositiveNumberException(POSITIVE_NUMBERS_ARE_ALLOWED_MESSAGE);
            }
        } catch (NumberFormatException | PositiveNumberException numberFormatException) {
            return false;
        }
        return true;
    }

    // Checks whether numbers is seven or not
    public static boolean isNumberEven(final int number) {
        return number % 2 == 0;
    }
}
