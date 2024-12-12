package main.expensemanager;
public class Expense {
    public int id;
    public String expense;
    public double amount;
    public String date;
    public String type;

    public Expense(String expense, double amount, String date, String type) {
        this.expense = expense;
        this.amount = amount;
        this.date = date;
        this.type = type;
    }
}
