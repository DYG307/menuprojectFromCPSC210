package persistence;

import model.MenuItems;
import model.MenuStorage;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

//This code was modeled from https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo/blob/master/src/test/persistence/JsonWriterTest.java

class JsonWriterTest extends JsonTest {

    @Test
    void testWriterInvalidFile() {
        try {
            MenuStorage mStorage = new MenuStorage("Menu");
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterEmptyWorkroom() {
        try {
            MenuStorage mStorage = new MenuStorage("Menu");
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyMenu.json");
            writer.open();
            writer.write(mStorage);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyMenu.json");
            mStorage = reader.read();
            assertEquals("Menu", mStorage.getName());
            assertEquals(0, mStorage.numMenu());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterGeneralWorkroom() {
        try {
            MenuStorage mStorage = new MenuStorage("Menu");
            mStorage.addMenuItem(new MenuItems("McDoge", 12.00));
            mStorage.addMenuItem(new MenuItems("Burger", 8.00));
            JsonWriter writer = new JsonWriter("./data/testWriterGeneralMenu.json");
            writer.open();
            writer.write(mStorage);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterGeneralMenu.json");
            mStorage = reader.read();
            assertEquals("Menu", mStorage.getName());
            List<MenuItems> menu = mStorage.getMenu();
            assertEquals(2, menu.size());
            checkMenuItems("McDoge", 12.00, menu.get(0));
            checkMenuItems("Burger", 8.00, menu.get(1));

        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }
}
