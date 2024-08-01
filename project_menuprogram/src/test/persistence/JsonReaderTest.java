package persistence;


import model.MenuItems;
import model.MenuStorage;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

//This code was modeled from https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo/blob/master/src/test/persistence/JsonReaderTest.java

public class JsonReaderTest extends JsonTest {

    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            MenuStorage mStorage = reader.read();
            fail("IOException expected");
        } catch (IOException e) {

        }
    }

    @Test
    void testReaderEmptyMenuStoage() {
        JsonReader reader = new JsonReader("./data/testWriterEmptyMenu.json");
        try {
            MenuStorage mStorage = reader.read();
            assertEquals("Menu", mStorage.getName());
            assertEquals(0, mStorage.numMenu());
        } catch (IOException e) {
             fail("Couldn't read file");
        }
    }

    @Test
    void testReaderGeneralWorkRoom() {
        JsonReader reader = new JsonReader("./data/testWriterGeneralMenu.json");
        try {
            MenuStorage mStorage = reader.read();
            assertEquals("Menu", mStorage.getName());
            List<MenuItems> menu = mStorage.getMenu();
            assertEquals(2, menu.size());
            checkMenuItems("McDoge", 12.00, menu.get(0));
            checkMenuItems("Burger", 8.00, menu.get(1));
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }
}
