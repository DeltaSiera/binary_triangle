import exception.InputDataException;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class BinaryTriangleTest {

    @Test
    public void shouldAssertTrue_ifNumberIsEven() {
        int evenNumber = 22;
        assertTrue(BinaryTriangle.isNumberEven(evenNumber));
    }

    @Test
    public void shouldAssertFalse_ifNumberIsNotEven() {
        int evenNumber = 21;
        assertFalse(BinaryTriangle.isNumberEven(evenNumber));
    }

    @Test
    public void shouldAssertTrue_whenNumberIsNumericAndPositive() {
        String numericPositiveNumber = "22";
        assertTrue(BinaryTriangle.isNumericAndPositive(numericPositiveNumber));
    }

    @Test
    public void shouldAssertFalse_whenNotNumberIsGiven() {
        String numericPositiveNumber = "word";
        assertFalse(BinaryTriangle.isNumericAndPositive(numericPositiveNumber));
    }

    @Test
    public void shouldAssertFalse_whenNumberIsNull() {
        assertFalse(BinaryTriangle.isNumericAndPositive(null));
    }

    @Test
    public void shouldAssertFalse_whenNumberIsNumericAndNegative() {
        assertFalse(BinaryTriangle.isNumericAndPositive("-1"));
    }

    @Test
    public void shouldAssertTrue_whenListIsValid() throws InputDataException {
        String[] listToCheck = {"1", "2", "3"};
        List<Integer> expectedList = Arrays.asList(1, 2, 3);
        assertEquals(expectedList, BinaryTriangle.createNumbersRowList(expectedList.size(), listToCheck));
    }

    @Test
    public void shouldAssertTrue_whenFileReadCorrectly() {
        ArrayList<List<Integer>> expectedList = new ArrayList<>();
        expectedList.add(Arrays.asList(1));
        expectedList.add(Arrays.asList(1, 2));
        expectedList.add(Arrays.asList(1, 2, 3));

        ArrayList<List<Integer>> actualList = new ArrayList<>();
        actualList = BinaryTriangle.readTriangleDataFromFile("triangle_test.txt");
        assertEquals(expectedList, actualList);
    }

    @Test
    public void shouldAssertEquals_whenStringOfInputIsGiven() {

        String[] wrongNumberList = {"a"};
        Exception exception = assertThrows(InputDataException.class, () -> BinaryTriangle.createNumbersRowList(wrongNumberList.length, wrongNumberList));

        String expectedMessage = BinaryTriangle.WRONG_INPUT_DATA_MESSAGE;
        String actualMessage = exception.getMessage();

        assertEquals(expectedMessage, actualMessage);
    }
}
