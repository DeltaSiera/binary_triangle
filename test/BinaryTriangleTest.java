//import exception.InputDataException;
//import exception.PositiveNumberException;
//import org.junit.jupiter.api.Test;
//
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;


import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class BinaryTriangleTest {

    @Test
    public void shouldAssertTrue_ifOneNumberIsEven() {
        int evenNumber = 22;
        int notEvenNumber = 21;
        assertTrue(BinaryTriangle.areEvenOddBetweenSelf(evenNumber, notEvenNumber));
    }

    @Test
    public void shouldAssertFalse_ifNumbersAreEven() {
        int evenNumber = 22;
        assertFalse(BinaryTriangle.areEvenOddBetweenSelf(evenNumber, evenNumber));
    }

    @Test
    public void test() {
        List<String> testList = List.of("1", "2 3", "4 5 6");
        int[][] expected = {{1}, {2, 3}, {4, 5, 6}};
        int[][] actual = BinaryTriangle.mapListTo2DArray(testList);
        assertTrue(Arrays.deepEquals(expected, actual));
    }

    @Test
    public void readDataFromFile() throws IOException {
        List<String> expected = List.of("1", "2 3", "4 5 6");
        List<String> actual = BinaryTriangle.readDataFromFile("test\\triangle_test.txt");
        assertEquals(actual, expected);
    }

    @Test
    public void wrongData() {
        List<String> wrongData = List.of("1", "a 2");
        NumberFormatException exception = assertThrows(NumberFormatException.class,
                () -> BinaryTriangle.mapListTo2DArray(wrongData),
                "Check data.");
        System.out.println(exception.getMessage()
                                    .contains("For input string: "));
    }
//
//    @Test
//    public void go() throws CloneNotSupportedException {
//        int[][] triangle = {{1}, {2, 4}};
//        BinaryTriangle.computePathSum(triangle, new Node());
//        assertEquals(2, BinaryTriangle.NODES.size());
//    }
}
