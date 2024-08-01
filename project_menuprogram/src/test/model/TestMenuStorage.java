package model;


import static org.junit.jupiter.api.Assertions.assertEquals;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


public class TestMenuStorage {
    private MenuStorage menuStorage;

    @BeforeEach
    public void setUp() {
        menuStorage = new MenuStorage("Test Menu");
    }

    @Test
    public void testAddMenuItem() {
        MenuItems item = new MenuItems("Coffee", 2.99);
        menuStorage.addMenuItem(item);
        assertEquals(1, menuStorage.getMenu().size());
    }

}
