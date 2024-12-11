package main.expensemanager;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainAppController implements Initializable {
    public MainAppController() { instance = this; }
    @FXML
    private VBox expensevbox;
    @FXML
    ScrollPane expensescrollPane;
    private static MainAppController instance;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        expensevbox.setFillWidth(false);
        expensevbox.setSpacing(2);
        expensescrollPane.setContent(expensevbox);
        expensescrollPane.setFitToWidth(true);
        expensescrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        expensescrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
    }

    @FXML
    private void buttonprsd() throws IOException {
        ExpenseAdderController.secondstage();
    }

    public void addExpensePane(String expense, double amount, String date) {
        Pane expensePane = new Pane();
        expensePane.setStyle("-fx-background-color: #00000026; -fx-background-radius: 10;");
        expensePane.setPrefSize(582,110);
        expensePane.setMaxSize(582,110);
        expensePane.setMinSize(582,110);

        Label expenseLabel = new Label(expense);
        expenseLabel.setLayoutX(14);
        expenseLabel.setLayoutY(14);
        expenseLabel.setPrefSize(142,26);
        Label moneyLabel = new Label(String.valueOf(amount));
        moneyLabel.setLayoutX(14);
        moneyLabel.setLayoutY(62);
        moneyLabel.setPrefSize(142,26);
        Label  dateLabel = new Label(date);
        dateLabel.setLayoutX(427);
        dateLabel.setLayoutY(14);
        dateLabel.setPrefSize(142,26);
        Label typeLabel = new Label("Type");
        typeLabel.setLayoutX(427);
        typeLabel.setLayoutY(62);
        typeLabel.setPrefSize(142,26);
        expensePane.getChildren().addAll(expenseLabel,moneyLabel,dateLabel,typeLabel);
        if (expensevbox != null) {
        expensevbox.getChildren().add(expensePane); }
        else { System.out.println("expensevbox is null!"); }

    }
    public static MainAppController getInstance() {
        return instance; }
}