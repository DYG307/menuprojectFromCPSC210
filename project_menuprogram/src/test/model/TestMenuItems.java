package model;


import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestMenuItems {

    @Test
    public void testConstructor() {
        MenuItems menuItem = new MenuItems("Burger", 5.99);
        assertEquals("Burger", menuItem.getName());
        assertEquals(5.99, menuItem.getPrice());
    }

    @Test
    public void testToString() {
        String testName = "Coffee";
        MenuItems item = new MenuItems(testName, 2.99);
        assertEquals(testName, item.toString());
    }
}
