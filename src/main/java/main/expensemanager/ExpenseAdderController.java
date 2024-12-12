package main.expensemanager;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

public class ExpenseAdderController  {

    @FXML
    private TextField amounttext;

    @FXML
    private TextField datetxt;
    DataBase dataBase;

    @FXML
    private TextField expensetxt;
    private static Stage stage;
    @FXML
    void addbtnprsd() throws SQLException {
        dataBase = new DataBase();
        String expenses = expensetxt.getText();
        double amount = Double.parseDouble(amounttext.getText());
        String date = datetxt.getText();
        String type = "type";
        Expense expense = new Expense(expenses,amount,date,type);
        MainAppController.getInstance().addExpensePane(expense);
        dataBase.addData(expense);
        dataBase.printdata(dataBase.returndata());
        stage.close();

    }
    public static void secondstage() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("ExpenseAdder.fxml"));
        stage = new Stage();
        Scene scene = new Scene(fxmlLoader.load());
        stage.setResizable(false);
        stage.setTitle("Add Expense");
        scene.setFill(Color.TRANSPARENT);
        stage.setScene(scene);
        stage.showAndWait();
    }
}

