package persistence;

import model.MenuItems;

import static org.junit.jupiter.api.Assertions.assertEquals;


//This code was modeled from https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo/blob/master/src/test/persistence/JsonTest.java

public class JsonTest {
    protected void checkMenuItems(String name, double price, MenuItems menuItems) {
        assertEquals(name, menuItems.getName());
        assertEquals(price, menuItems.getPrice());
    }
}

