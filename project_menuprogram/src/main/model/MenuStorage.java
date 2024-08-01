package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

// Represents a MenuStorage having a collection of MenuItems
//Uses code from https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo/blob/master/src/main/model/WorkRoom.java

public class MenuStorage implements Writer {
    private String name;
    private List<MenuItems> menu;

    public MenuStorage(String name) {
        this.name = name;
        menu = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    // MODIFIES: this
    // EFFECTS: adds menuItems to this workroom, also now can be logged when menu item is now added
    public void addMenuItem(MenuItems menuItems) {
        menu.add(menuItems);
        EventLog.getInstance().logEvent(new Event("Added menu item: " + menuItems.getName()));
    }

    // EFFECTS: returns an unmodifiable list of thingies in this Menu
    public List<MenuItems> getMenu() {
        return Collections.unmodifiableList(menu);

    }

    // EFFECTS: returns number of thingies in this MenuStorage
    public int numMenu() {
        return menu.size();
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("Menu", menuToJson());
        return json;
    }

    // EFFECTS: returns things in this menu as a JSON array
    private JSONArray menuToJson() {
        JSONArray jsonArray = new JSONArray();

        for (MenuItems t : menu) {
            jsonArray.put(t.toJson());
        }

        return jsonArray;
    }
}
