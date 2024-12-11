package main.expensemanager;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;

public class ExpenseAdderController  {

    @FXML
    private Pane addbtn;

    @FXML
    private TextField amounttext;

    @FXML
    private TextField datetxt;

    @FXML
    private TextField expensetxt;
    private static Stage stage;
    @FXML
    void addbtnprsd() {
        String expense = expensetxt.getText();
        double amount = Double.parseDouble(amounttext.getText());
        String date = datetxt.getText();
        MainAppController.getInstance().addExpensePane(expense,amount,date);
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

