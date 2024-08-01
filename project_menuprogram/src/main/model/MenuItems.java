package model;


//Stores the price and menu item to be called when resturantPOS runs

import org.json.JSONObject;
import persistence.Writer;

public class MenuItems implements Writer {
    private String name;
    private double price;

    //Effects: sets item price and name
    //Modifies: this
    public MenuItems(String name, double price) {
        this.name = name;
        this.price = price;
    }



    //Effects: gets name
    public String getName() {
        return name;
    }

    //Effects: gets price
    public double getPrice() {
        return price;
    }

    //Effects: returns to string so visble in GUI
    public String toString() {
        return name;
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("price", price);
        return json;
    }
}
