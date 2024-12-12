package main.expensemanager;

import java.sql.*;

public class DataBase {
    public final String url = "jdbc:sqlite:expenses.db";
    public static int index = 0;
    public Connection connect() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
            throw new RuntimeException(e);
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
            System.out.println("database table created successfully");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
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
            System.out.println("data added successfully");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    public Expense returndata() {
        String sql = "SELECT * FROM uexpenses";
        Expense expense = null;
        try (Connection conn = connect(); PreparedStatement pstmt = conn.prepareStatement(sql); ResultSet rs = pstmt.executeQuery()) {
            if (rs.next()) {
                expense = new Expense(
                        rs.getString(2),
                        rs.getDouble(3),
                        rs.getString(4),
                        rs.getString(5));

            }
            else {
                System.out.println("No data in Database");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return expense;
    }

    void printdata(Expense expense) {
        if (expense == null) {
            System.out.println("it is null");
        }
        System.out.println(expense.expense +  String.valueOf(expense.amount));
    }

}
