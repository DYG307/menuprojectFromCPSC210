package model;

import java.util.ArrayList;
import java.util.List;

// Represents the Daily report of how much the Resturant made
// adds transactions to the menu by calling the tranasaction object
// calculates the total revenue todayy


public class DailyReport {
    private List<Transaction> transactions;


    //Effects: gets the date
    public DailyReport() {
        this.transactions = new ArrayList<>();
    }

    //Effects: adds transaction to the list
    //Modifies: this
    public void addTransaction(Transaction transaction) {
        transactions.add(transaction);
    }

    //Calculates how much money was made
    public double calculateTotalRevenue() {
        double totalRevenue = 0;
        for (Transaction transaction : transactions) {
            totalRevenue += transaction.getAmount();
        }
        return totalRevenue;
    }


}