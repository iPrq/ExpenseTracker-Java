package main.expensemanager;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class MainAppController implements Initializable {
    public MainAppController() { instance = this; }
    @FXML
    private VBox expensevbox;
    @FXML
    ScrollPane expensescrollPane;
    private static MainAppController instance;
    @FXML
    public Pane titlePane,expensebtn,summarybtn;
    private double x,y;
    DataBase dataBase;
    @FXML
    private Pane expensePane;

    public void init(Stage stage) {
        titlePane.setOnMousePressed(MouseEvent -> {
            x = MouseEvent.getSceneX();
            y = MouseEvent.getSceneY();
        });
        titlePane.setOnMouseDragged(MouseEvent -> {
            stage.setX(MouseEvent.getScreenX() - x);
            stage.setY(MouseEvent.getScreenY() - y);
        });
        // close.setOnMouseClicked(MouseEvent -> stage.close());
       // minimise.setOnMouseClicked(MouseEvent -> stage.setIconified(true));
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        expensevbox.setFillWidth(false);
        expensevbox.setSpacing(4);
        expensescrollPane.setContent(expensevbox);
        expensescrollPane.setFitToWidth(true);
        expensescrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        expensescrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        expensevbox.prefWidthProperty().bind(expensescrollPane.widthProperty());
        dataBase = new DataBase();
        try {
            addPastExpense(dataBase.returndata());
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @FXML
    private void buttonprsd() throws IOException {
        ExpenseAdderController.secondstage();
    }

    public void addExpensePane(Expense expense) {
        Pane expensePane = new Pane();
        expensePane.setStyle("-fx-background-color: #da90ff; -fx-background-radius: 10;");
        expensePane.setPrefSize(582,110);
        expensePane.setMaxSize(582,110);
        expensePane.setMinSize(582,110);

        Label expenseLabel = new Label(expense.expense);
        expenseLabel.getStyleClass().add("ulabel");
        expenseLabel.setLayoutX(20);
        expenseLabel.setLayoutY(14);
        Label moneyLabel = new Label(String.valueOf(expense.amount));
        moneyLabel.getStyleClass().add("ulabel");
        moneyLabel.setLayoutX(20);
        moneyLabel.setLayoutY(62);
        Label dateLabel = new Label(expense.date);
        dateLabel.getStyleClass().add("ulabel");
        dateLabel.setLayoutX(427);
        dateLabel.setLayoutY(14);
        Label typeLabel = new Label(expense.type);
        typeLabel.getStyleClass().add("ulabel");
        typeLabel.setLayoutX(427);
        typeLabel.setLayoutY(62);
        expensePane.getChildren().addAll(expenseLabel,moneyLabel,dateLabel,typeLabel);
        if (expensevbox != null) {
        expensevbox.getChildren().add(expensePane); }
        else { System.out.println("expensevbox is null!"); }

    }
    void addPastExpense(List<Expense> expenses) {
        if (expenses != null) {
            for (Expense expense : expenses) {
                addExpensePane(expense);
            }
        }
    }
    public static MainAppController getInstance() {
        return instance; }

    @FXML
    private void setePanevisible() {
        expensePane.setVisible(true);
        expensebtn.getStyleClass().add("clicked");
        summarybtn.getStyleClass().remove("clicked");
    }

    @FXML
    private void setsPanevisible() {
       expensePane.setVisible(false);
       expensebtn.getStyleClass().remove("clicked");
       summarybtn.getStyleClass().add("clicked");
    }
}