package model;


import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestOrderItems {


    @Test
    public void testGetMenuItem() {
        MenuItems menuItem = new MenuItems("Burger", 5.99);
        OrderItems orderItem = new OrderItems(menuItem, 2);
        assertEquals(menuItem, orderItem.getMenuItem());
    }

    @Test
    public void testGetQuantity() {
        MenuItems menuItem = new MenuItems("Pizza", 8.99);
        OrderItems orderItem = new OrderItems(menuItem, 3);

        assertEquals(3, orderItem.getQuantity());
    }

    @Test
    public void testGetTotalPrice() {
        MenuItems menuItem = new MenuItems("Salad", 4.99);
        OrderItems orderItem = new OrderItems(menuItem, 4);
        assertEquals(19.96, orderItem.getTotalPrice());
    }



}
