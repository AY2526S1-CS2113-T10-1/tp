package finsight.ui;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

public class UiTest {
    private final InputStream originalSystemIn = System.in;

    @AfterEach
    void restoreSystemIn() {
        System.setIn(originalSystemIn);
    }

    @Test
    void getNextLine_leadingSpaces_spacesRemoved() {
        String inputString = "    Hello World\n";
        String expectedString = "hello world";

        ByteArrayInputStream testInput = new ByteArrayInputStream(inputString.getBytes());
        System.setIn(testInput);

        Ui ui = new Ui();
        String actualString = ui.getNextLine();

        assertEquals(expectedString, actualString);
    }

    @Test
    void getNextLine_trailingSpaces_spacesRemoved() {
        String inputString = "Hello World    \n";
        String expectedString = "hello world";

        ByteArrayInputStream testInput = new ByteArrayInputStream(inputString.getBytes());
        System.setIn(testInput);

        Ui ui = new Ui();
        String actualString = ui.getNextLine();

        assertEquals(expectedString, actualString);
    }

    @Test
    void getNextLine_leadingAndTrailingSpaces_spacesRemoved() {
        String inputString = "    Hello World    \n";
        String expectedString = "hello world";

        ByteArrayInputStream testInput = new ByteArrayInputStream(inputString.getBytes());
        System.setIn(testInput);

        Ui ui = new Ui();
        String actualString = ui.getNextLine();

        assertEquals(expectedString, actualString);
    }

    @Test
    void getNextLine_uppercaseString_lowercaseOutput() {
        String inputString = "HELLO WORLD\n";
        String expectedString = "hello world";

        ByteArrayInputStream testInput = new ByteArrayInputStream(inputString.getBytes());
        System.setIn(testInput);

        Ui ui = new Ui();
        String actualString = ui.getNextLine();

        assertEquals(expectedString, actualString);
    }

    @Test
    void printErrorMessage_singleMessage_noExceptionThrown() {
        String message = "Test";
        assertDoesNotThrow(() -> Ui.printErrorMessage(message));
    }

    @Test
    void printWelcomeMessage_noInput_noExceptionThrown() {
        assertDoesNotThrow(Ui::printWelcomeMessage);
    }

    @Test
    void printByeMessage_noInput_noExceptionThrown() {
        assertDoesNotThrow(Ui::printByeMessage);
    }

    @Test
    void printPossibleCommands_noInput_noExceptionThrown() {
        assertDoesNotThrow(Ui::printPossibleCommands);
    }
}
