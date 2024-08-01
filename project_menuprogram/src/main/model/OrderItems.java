package model;

//Creates the order item and stores that item to be used in transaction class

public class OrderItems {
    private MenuItems menuItem;
    private int quantity;

    //Modifies: menuItem, quantity, this
    //Effects: creates constructor with these items
    public OrderItems(MenuItems menuItem, int quantity) {
        this.menuItem = menuItem;
        this.quantity = quantity;
    }


    public double getTotalPrice() {
        return menuItem.getPrice() * quantity;
    }

    public int getQuantity() {
        return quantity;
    }

    public MenuItems getMenuItem() {
        return menuItem;
    }

}