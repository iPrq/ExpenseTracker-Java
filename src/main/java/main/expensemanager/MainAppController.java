package main.expensemanager;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;

public class MainAppController implements Initializable {
    @FXML
    private VBox expensevbox;
    @FXML
    private ScrollPane expensescrollPane;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        expensevbox.setFillWidth(false);
        expensevbox.setSpacing(2);
        expensescrollPane.setContent(expensevbox);
    }

    @FXML
    private void buttonprsd(MouseEvent e) {
        addExpensePane();
    }

    private void addExpensePane() {
        Pane expensePane = new Pane();
        expensePane.setStyle("-fx-background-color: #00000026; -fx-background-radius: 10;");
        expensePane.setPrefSize(582,110);
        Label expenseLabel = new Label("Expense");
        expenseLabel.setLayoutX(14);
        expenseLabel.setLayoutY(14);
        expenseLabel.setPrefSize(142,26);
        Label moneyLabel = new Label("Money");
        moneyLabel.setLayoutX(14);
        moneyLabel.setLayoutY(62);
        moneyLabel.setPrefSize(142,26);
        Label  dateLabel = new Label("Date");
        dateLabel.setLayoutX(427);
        dateLabel.setLayoutY(14);
        dateLabel.setPrefSize(142,26);
        Label typeLabel = new Label("Type");
        typeLabel.setLayoutX(427);
        typeLabel.setLayoutY(62);
        typeLabel.setPrefSize(142,26);
        expensePane.getChildren().addAll(expenseLabel,moneyLabel,dateLabel,typeLabel);
        expensevbox.getChildren().add(expensePane);

    }

}