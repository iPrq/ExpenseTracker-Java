package main.expensemanager;

import java.io.File;
import java.sql.*;
import java.util.ArrayList;
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

    void printdata(List<Expense> expenses) {
        if (expenses == null) {
            showerror("Error", "Something went wrong");
        }
        else {
            for(Expense expense : expenses) {
                System.out.println(expense.expense +" " + String.valueOf(expense.amount) + " " + expense.date);
            }
        }
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
}
