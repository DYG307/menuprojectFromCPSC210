package persistence;


import model.MenuItems;
import model.MenuStorage;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;



// Represents a reader that reads workroom from JSON data stored in file
// Code modeled after example from JSONSerializationDemo from CPSC 210
// the code can be found https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo/blob/master/src/main/persistence/JsonWriter.java
public class JsonReader {

    private final String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads menu from file and returns it;
    // throws IOException if an error occurs reading data from file
    public MenuStorage read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseMenuStorage(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {

        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(contentBuilder::append);
        }
        return contentBuilder.toString();
    }

    // EFFECTS: parses MenuItem from JSON object and returns it
    private MenuStorage parseMenuStorage(JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        MenuStorage items = new MenuStorage(name);
        addMenu(items, jsonObject);
        return items;
    }

    // MODIFIES: items
    // EFFECTS: parses thingies from JSON object and adds them to workroom
    private void addMenu(MenuStorage items, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("Menu");
        for (Object json : jsonArray) {
            JSONObject nextThingy = (JSONObject) json;
            addMenuStorage(items, nextThingy);
        }
    }

    // MODIFIES: items
    // EFFECTS: parses thingy from JSON object and adds it to menu
    private void addMenuStorage(MenuStorage items, JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        double price = jsonObject.getDouble("price");
        MenuItems food = new MenuItems(name, price);
        items.addMenuItem(food);

    }




}
