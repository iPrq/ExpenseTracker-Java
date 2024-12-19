package main.expensemanager;

import java.io.File;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static main.expensemanager.MainApplication.showerror;

public class DataBase {
    public final String url = "jdbc:sqlite:expenses.db";
    public Connection connect() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
            showerror("SQL Exception",e.getMessage());
        }
        return conn;
    }

    public void createDataBase() throws SQLException {
        String sql = "CREATE TABLE IF NOT EXISTS uexpenses (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "expense TEXT NOT NULL," +
                "amount REAL NOT NULL," +
                "date TEXT NOT NULL," +
                "type TEXT NOT NULL" + ");";
        try (Connection conn = connect(); Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            showerror("SQL Exception",e.getMessage());
        }

    }
    public void addData(Expense expense) throws SQLException {
        createDataBase();
        String sql = "INSERT INTO uexpenses(expense,amount,date,type) VALUES(?,?,?,?)";
        try (Connection conn = connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1,expense.expense);
            pstmt.setDouble(2,expense.amount);
            pstmt.setString(3, expense.date);
            pstmt.setString(4, expense.type);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            showerror("SQL Exception",e.getMessage());
        }
    }
    public List<Expense> returndata() throws SQLException {
        createDataBase();
        String sql = "SELECT * FROM uexpenses";
        List<Expense> expenses = new ArrayList<>();
        Expense expense;
        try (Connection conn = connect(); PreparedStatement pstmt = conn.prepareStatement(sql); ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                expense = new Expense(
                        rs.getString(2),
                        rs.getDouble(3),
                        rs.getString(4),
                        rs.getString(5));
                expenses.add(expense);
            }
        } catch (SQLException e) {
            showerror("SQL Exception",e.getMessage());
        }
        return expenses;
    }

    public HashMap<String,Integer> summarydata(List<Expense> expenses) {
        HashMap<String,Integer> summarydata = new HashMap<>();
        summarydata.put("expense",0);
        summarydata.put("amount",0);
        summarydata.put("Others",0);
        summarydata.put("Leisure",0);
        summarydata.put("Entertainment",0);
        summarydata.put("Work",0);
        summarydata.put("Necessities",0);
        summarydata.put("Travel",0);
        summarydata.put("total iterations",0);

        if (expenses == null) {
            showerror("Error", "Something went wrong");
        }
        else {
            for(Expense expense : expenses) {
                summarydata.put("expense",summarydata.get("expense")+1);
                summarydata.put("expense",summarydata.get("amount")+1);
                switch(expense.type) {
                    case "Others":
                        summarydata.put("Others",summarydata.get("Others")+1);
                        break;
                    case "Travel":
                        summarydata.put("Travel",summarydata.get("Travel")+1);
                        break;
                    case "Entertainment":
                        summarydata.put("Entertainment",summarydata.get("Entertainment")+1);
                        break;
                    case "Work":
                        summarydata.put("Work",summarydata.get("Work")+1);
                        break;
                    case "Leisure":
                        summarydata.put("Leisure",summarydata.get("Leisure")+1);
                        break;
                    case "Necessities" :
                        summarydata.put("Necessities",summarydata.get("Necessities")+1);
                        break;
                }
                summarydata.put("total iterations",summarydata.get("total iterations")+1);
            }
        }
        return summarydata;
    }
    public static void deleteDataBase() {
        File dbfile = new File("expenses.db");
        if(dbfile.exists()) {
            if (dbfile.delete()) {
                ExpenseAdderController.showpopup("File Deleted", "The DataBase has been deleted Successfully");
            } else {
                showerror("File Deletion Error", "Failed to Delete File");
            }
        }
        else{
            showerror("File Deletion Error", "Failed to Delete File or The File is already deleted");
        }
    }
    public HashMap<String, Double> expensessummary(List<Expense> expenses) {
        HashMap<String,Double> expsummary = new HashMap<>();
        for(Expense expense : expenses ) {
            expsummary.put(expense.expense,expense.amount);
        }
        return expsummary;
    }
    public double totalexpense(List<Expense> expenses) {
        double amount = 0;
        for (Expense expense: expenses) {
            amount += expense.amount;
        }
        return amount;
    }
}
