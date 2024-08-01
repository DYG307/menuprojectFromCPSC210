package model;


//Stores amount of money an item cost
public class Transaction {

    private double amount;

    //Requires amount > 0
    //Modifies: this
    //Effects: constructs a constructor with amount
    public Transaction(double amount) {
        this.amount = amount;
    }

    public double getAmount() {
        return amount;
    }
}