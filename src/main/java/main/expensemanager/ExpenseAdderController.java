package main.expensemanager;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ExpenseAdderController implements Initializable {

    @FXML
    private TextField amounttext;

    @FXML
    private TextField datetxt;
    DataBase dataBase;
    @FXML
    private ComboBox comboboxtype;

    @FXML
    private TextField expensetxt;
    private static Stage stage;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        comboboxtype.setItems(FXCollections.observableArrayList(
                                                    "Leisure",
                                                                "Entertainment",
                                                                "Work",
                                                                "Necessities",
                                                                 "Travel", "Others"
        ));
    }
    @FXML
    void addbtnprsd() throws SQLException {
        dataBase = new DataBase();
        String expenses = expensetxt.getText();
        double amount = Double.parseDouble(amounttext.getText());
        String date = datetxt.getText();
        String type = comboboxtype.getValue() == null ? "Others": (String) comboboxtype.getValue();
        Expense expense = new Expense(expenses,amount,date,type);
        MainAppController.getInstance().addExpensePane(expense);
        dataBase.addData(expense);
        MainAppController.getInstance().setPiechart(MainAppController.piechartdata(dataBase.summarydata(dataBase.returndata())));
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
    public static void showpopup(String title,String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}

