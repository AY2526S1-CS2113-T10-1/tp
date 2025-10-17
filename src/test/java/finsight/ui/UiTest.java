package finsight.ui;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

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
        String expectedString = "Hello World";

        ByteArrayInputStream testInput = new ByteArrayInputStream(inputString.getBytes());
        System.setIn(testInput);

        Ui ui = new Ui();
        String actualString = Ui.getNextLine();

        assertEquals(expectedString, actualString);
    }

    @Test
    void getNextLine_trailingSpaces_spacesRemoved() {
        String inputString = "Hello World    \n";
        String expectedString = "Hello World";

        ByteArrayInputStream testInput = new ByteArrayInputStream(inputString.getBytes());
        System.setIn(testInput);

        Ui ui = new Ui();
        String actualString = Ui.getNextLine();

        assertEquals(expectedString, actualString);
    }

    @Test
    void getNextLine_leadingAndTrailingSpaces_spacesRemoved() {
        String inputString = "    Hello World    \n";
        String expectedString = "Hello World";

        ByteArrayInputStream testInput = new ByteArrayInputStream(inputString.getBytes());
        System.setIn(testInput);

        Ui ui = new Ui();
        String actualString = Ui.getNextLine();

        assertEquals(expectedString, actualString);
    }
}
