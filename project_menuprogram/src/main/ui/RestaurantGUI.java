package ui;

import model.*;
import model.Event;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import model.EventLog;


/*
Resturant GUI that includes two images and utilizes functions and methods from other previous classes, is able to
do most of the work that the console version of this GUI can do

The following code was also influenced, modeled by many examples used here https://docs.oracle.com/javase/tutorial/uiswing/examples/components/index.html
some code is inspired from CPSC 210 https://github.students.cs.ubc.ca/CPSC210/AlarmSystem/tree/main/src/main/ca/ubc/cpsc210/alarm/ui

 */

public class RestaurantGUI extends JFrame {
    private MenuStorage menuStorage;
    private DailyReport dailyReport;
    private List<OrderItems> currentOrder;
    private JComboBox<MenuItems> menuItemsComboBox;
    private JTextArea orderTextArea;
    private JTextField quantityField;
    private JButton loadMenuButton;
    private static final String JSON_STORE = "./data/menu.json";
    private static final String McDoge = "./data/Mcdoge.png";
    private static final String DogeCoin = "./data/dogecoin-doge4661.jpg";
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;
    private static final String FILE_DESCRIPTOR = "...file";
    private static final String SCREEN_DESCRIPTOR = "...screen";
    private JComboBox<String> printCombo;



    //Effects: Runs the resturant GUI
    public RestaurantGUI() {
        menuStorage = new MenuStorage("Menu");
        dailyReport = new DailyReport();
        currentOrder = new ArrayList<>();
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);

        setTitle("Restaurant POS System");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new FlowLayout());

        menuPanel();
        initializeOrderPanel();
        initializeCheckoutPanel();
        initializeResetButton();
        initializeExitButton();
        addShutdownFunction();


        setVisible(true);
    }

    //Effects: initalizes the GUI and the main menu body
    @SuppressWarnings({"checkstyle:MethodLength", "checkstyle:SuppressWarnings"})
    private void menuPanel() {
        JPanel menuPanel = new JPanel();
        menuPanel.setLayout(new BoxLayout(menuPanel, BoxLayout.Y_AXIS));

        JTextField itemNameField = new JTextField(10);
        JTextField itemPriceField = new JTextField(5);
        JButton addMenuItemButton = new JButton("Add Menu Item");

        addMenuItemButton.addActionListener(e -> {
            String itemName = itemNameField.getText();
            double itemPrice = Double.parseDouble(itemPriceField.getText());
            MenuItems newItem = new MenuItems(itemName, itemPrice);
            menuStorage.addMenuItem(newItem);
            itemNameField.setText("");
            itemPriceField.setText("");
            menuItemsComboBox.addItem(newItem); // Update combo box

        });

        menuPanel.add(new JLabel("Item Name:"));
        menuPanel.add(itemNameField);
        menuPanel.add(new JLabel("Item Price:"));
        menuPanel.add(itemPriceField);
        menuPanel.add(addMenuItemButton);

        JButton loadMenuButton = new JButton("Load Menu");
        loadMenuButton.addActionListener(e -> {
            try {
                loadMenuFromJson();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });
        menuPanel.add(loadMenuButton);

        JButton saveMenuButton = new JButton("Save Menu");
        saveMenuButton.addActionListener(e -> {
            try {
                saveMenuToJson();
            } catch (FileNotFoundException ex) {
                throw new RuntimeException(ex);
            }
        });
        menuPanel.add(saveMenuButton);

        add(menuPanel);
    }


    //Effects: Resets the GUI for a new customer
    private void initializeResetButton() {
        JButton resetButton = new JButton("New Order");
        resetButton.addActionListener(e -> resetForNewOrder());
        add(resetButton);
    }




    //Effects: clears the lines for new order
    private void resetForNewOrder() {
        currentOrder.clear(); // Clear the current order list
        orderTextArea.setText("");
    }

    //Effects: The first thing that boots up when the application is run, creates a popup that last 5 seconds
    private static void showSplashScreen() {
        JWindow splashScreen = new JWindow();
        splashScreen.setSize(400, 300); // size of the splash screen

        // Add an image
        JLabel imageLabel = new JLabel(new ImageIcon(McDoge));
        splashScreen.add(imageLabel, BorderLayout.CENTER);

        // Set the splash screen position to the center of the screen
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        splashScreen.setLocation(screenSize.width / 2 - splashScreen.getSize().width / 2,
                screenSize.height / 2 - splashScreen.getSize().height / 2);

        splashScreen.setVisible(true);

        // Timer to close the splash screen after 5 seconds (5000ms)
        new Timer(5000, e -> splashScreen.dispose()).start();
    }



    //Effects: creates white blank space to add to the order, and updates as items are added
    @SuppressWarnings({"checkstyle:MethodLength", "checkstyle:SuppressWarnings"})
    private void initializeOrderPanel() {
        JPanel orderPanel = new JPanel();
        orderPanel.setLayout(new BoxLayout(orderPanel, BoxLayout.Y_AXIS));

        menuItemsComboBox = new JComboBox<MenuItems>();
        for (MenuItems item : menuStorage.getMenu()) {
            menuItemsComboBox.addItem(item);
        }
        JButton addItemToOrderButton = new JButton("Add to Order");
        quantityField = new JTextField(5);
        orderTextArea = new JTextArea(10, 30);
        orderTextArea.setEditable(false);

        addItemToOrderButton.addActionListener(e -> {
            MenuItems selectedItem = (MenuItems) menuItemsComboBox.getSelectedItem();
            int quantity = Integer.parseInt(quantityField.getText());
            double totalItemPrice = selectedItem.getPrice() * quantity;
            OrderItems orderItem = new OrderItems(selectedItem, quantity);
            currentOrder.add(orderItem);
            orderTextArea.append(quantity + " x " + selectedItem.getName() + " - $"
                    + String.format("%.2f", totalItemPrice) + "\n");
            dailyReport.addTransaction(new Transaction(selectedItem.getPrice() * quantity));

        });

        orderPanel.add(new JLabel("Select Menu Item:"));
        orderPanel.add(menuItemsComboBox);
        orderPanel.add(new JLabel("Quantity:"));
        orderPanel.add(quantityField);
        orderPanel.add(addItemToOrderButton);
        orderPanel.add(new JScrollPane(orderTextArea));

        add(orderPanel);
    }

    //Effects: loads items from previously saced JSON file
    private void loadMenuFromJson() throws IOException {

        menuStorage = jsonReader.read();
        menuItemsComboBox.removeAllItems();
        for (MenuItems item : menuStorage.getMenu()) {
            menuItemsComboBox.addItem(item);
        }
    }

    //Effects: Saves new menu and updates the old JSON file
    private void saveMenuToJson() throws FileNotFoundException {
        jsonWriter.open();
        jsonWriter.write(menuStorage);
        jsonWriter.close();
        JOptionPane.showMessageDialog(this, "Menu saved to " + JSON_STORE);

    }

    //Effects: closes the window when the x button is pressed and prints a log
    private void addShutdownFunction() {
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                // Execute your method before shutting down
                printLogToConsole();
                // Close the application
                System.exit(0);
            }
        });
    }




    //Effects: initalizes the GUI and the main menu body
    private void initializeCheckoutPanel() {
        JButton checkoutButton = new JButton("Checkout");
        checkoutButton.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "Order Total: $" + calculateTotalOrder());
            showCheckoutImage();
            currentOrder.clear();
            orderTextArea.setText("");
        });

        add(checkoutButton);
    }

    //Effects: creates a panel that shows an image when checkOut is pressed in previous method
    private void showCheckoutImage() {
        JDialog imageDialog = new JDialog(this, "Checkout Image", true);
        imageDialog.setSize(500, 500);


        ImageIcon checkoutImage = new ImageIcon(DogeCoin);
        JLabel imageLabel = new JLabel(checkoutImage);
        imageDialog.add(imageLabel);


        imageDialog.setLocationRelativeTo(this);
        imageDialog.setVisible(true);
    }



    //Effects: calculates total order
    private double calculateTotalOrder() {
        return currentOrder.stream().mapToDouble(OrderItems::getTotalPrice).sum();
    }


    //Effects: creates an exit button, when pressed the logs will be printed and system will shutdown
    private void initializeExitButton() {
        JButton exitButton = new JButton("Exit");
        exitButton.addActionListener(e -> {
            // Log the exit event
            EventLog.getInstance().logEvent(new Event("Exiting application"));

            // Print the log to the console
            printLogToConsole();

            // Exit the application
            System.exit(0);
        });
        add(exitButton);
    }

    //Effects: Prints the logs to the console
    private void printLogToConsole() {
        for (Event event : EventLog.getInstance()) {
            System.out.println(event);
        }
    }





    public static void main(String[] args) {
        // Show the splash screen
        RestaurantGUI.showSplashScreen();

        // Use a thread to wait and then display the main application window
        new Thread(() -> {
            try {
                Thread.sleep(5000); // Wait for 5 seconds
            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
            }

            // Now show the main application window
            SwingUtilities.invokeLater(() -> {
                RestaurantGUI gui = new RestaurantGUI();
                gui.setVisible(true);
            });
        }).start();
    }




}


