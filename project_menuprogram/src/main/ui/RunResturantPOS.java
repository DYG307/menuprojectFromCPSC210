package ui;

import model.*;
import persistence.JsonReader;
import persistence.JsonWriter;


import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;
import java.util.List;

// A console UI that runs the user interface and calls upon all the functions
// Uses all the items from Model to run the restaurant
//Uses code from https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo/blob/master/src/main/ui/WorkRoomApp.java

public class RunResturantPOS {
    private static Scanner scanner = new Scanner(System.in);
    private static List<MenuItems> menu = new ArrayList<>();
    private static List<OrderItems> order = new ArrayList<>();
    private static int tableNumber;
    private static String serverName;
    private static DailyReport dailyReport = new DailyReport();
    private static MenuStorage mStorage;
    private static JsonWriter jsonWriter;
    static JsonReader jsonReader;
    private static final String JSON_STORE = "./data/menu.json";


    //Effects: runs the entire program when called
    public RunResturantPOS() {
        scanner = new Scanner(System.in);
        mStorage = new MenuStorage("Menu");
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
        runResturantPOS();
    }

    //Effects: runs resturantPOS and calls all classes to simplify it into one class
    public static void runResturantPOS() {
        selectResturantPOS();

    }

    //Requires: choice is is 1>=choice<=3
    //Effects: depending on user input choice, it'll select one of the options
    //Modifies: this
    @SuppressWarnings({"checkstyle:MethodLength", "checkstyle:SuppressWarnings"})
    public static void selectResturantPOS() {
        boolean keepGoing = true;
        collectServerInfo();
        System.out.println("load old menu? Enter 1 for old menu");
        int oldMenu = scanner.nextInt();
        if (oldMenu == 1) {
            loadMenu();
            for (int i = 0; i < mStorage.getMenu().size(); i++) {
                menu.add(mStorage.getMenu().get(i));
            }
        } else {
            addMenuItemsManually();
            displayMenu();
        }
        while (keepGoing) {
            options();
            int choice = scanner.nextInt();
            switch (choice) {
                case 1:
                    addToOrder();
                    break;
                case 2:
                    checkout();
                    break;
                case 3:
                    reset();
                    collectServerInfo();
                    break;
                case 4:
                    System.exit(0);
                    keepGoing = false;
                    break;
                case 5:
                    printDailyReport();
            }
        }
    }

    //Effects: Collects server info and table name
    //Modifies: this
    public static void collectServerInfo() {
        System.out.println("Enter Server Name: ");
        serverName = scanner.next();
        System.out.println("Enter Table Number: ");
        tableNumber = scanner.nextInt();
    }



    //Modifies: keepAdding2
    //Effects Adds Items to the Menu list
    //Modifies: this
    public static void addMenuItemsManually() {
        System.out.println("Manually add menu items to the system (type 'done' to finish):");
        boolean keepAdding2 = true;
        while (keepAdding2) {
            System.out.println("Enter a menu item name: ");
            String itemName = scanner.next();
            if (itemName.equalsIgnoreCase("done")) {
                keepAdding2 = false;
                break;
            }
            System.out.print("Enter the price for '" + itemName + "': ");
            double itemPrice = Double.parseDouble(scanner.next());
            scanner.nextLine();
            MenuItems menuObj = new MenuItems(itemName, itemPrice);
            menu.add(menuObj);
            mStorage.addMenuItem(menuObj);
            saveMenu();
        }
    }

    //Effects: Displays menu once all items have been added
    public static void displayMenu() {
        System.out.println("Menu:");
        List<MenuItems> menu = mStorage.getMenu();
        for (int i = 0; i < menu.size(); i++) {
            MenuItems item = menu.get(i);
            System.out.println(i + 1 + ". " + item.getName() + " - $" + item.getPrice());
        }
    }

    //Requires: menu item to only be added in one instance and quantity to be greater than 0
    //Effects: takes order and adds it to the order list
    //Modifies: this
    public static void addToOrder() {
        displayMenu();
        System.out.print("Enter the item number to add to your order: ");
        int itemNumber = scanner.nextInt();
        if (itemNumber >= 1 && itemNumber <= menu.size()) {

            MenuItems selectedMenuItem = mStorage.getMenu().get(itemNumber - 1);
            System.out.print("Enter the quantity: ");
            int quantity = scanner.nextInt();
            if (quantity > 0) {
                order.add(new OrderItems(selectedMenuItem, quantity));
                System.out.println(quantity + " " + selectedMenuItem.getName() + " added to your order.");
                double totalAmount = selectedMenuItem.getPrice() * quantity;
                dailyReport.addTransaction(new Transaction(totalAmount));
            } else {
                System.out.println("Quantity must be greater than 0.");
            }
        } else {
            System.out.println("Invalid item not on Menu.");
        }
    }

    //Effects: reads from the order list and displays the total price, server info and table number
    public static void checkout() {
        double total = 0;
        if (order.isEmpty()) {
            System.out.println("Nothing Ordered");
        } else {
            for (OrderItems item : order) {
                total += item.getTotalPrice();
            }
        }

        System.out.println("Server: " + serverName);
        System.out.println("Table Number: " + tableNumber);
        System.out.println("Total: $" + total);
        System.out.println("Thank you for your order!");
    }

    //Effects: resets parameters for new customer
    public static void reset() {
        serverName = "";
        tableNumber = 0;
        order.clear();
    }

    //Effects: the selection menu for the main options
    public static void options() {
        System.out.println("\n1. Add item to order");
        System.out.println("2. Checkout");
        System.out.println("3. New Order");
        System.out.println("4. Exit");
        System.out.println("5. Daily Report");
        System.out.println("Enter your choice: ");
    }

    //Effects: Prints out the daily earnings
    private static void printDailyReport() {

        System.out.println("Total Revenue for Today: $" + dailyReport.calculateTotalRevenue());
    }

    // EFFECTS: saves the menu to file
    private static void saveMenu() {
        try {
            jsonWriter.open();
            jsonWriter.write(mStorage);
            jsonWriter.close();
            System.out.println("Saved " + mStorage.getName() + " to " + JSON_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }

    // MODIFIES: this
    // EFFECTS: loads menu from file
    private static void loadMenu() {
        try {
            mStorage = jsonReader.read();
            System.out.println("Loaded " + mStorage.getName() + " from " + JSON_STORE);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }


}


