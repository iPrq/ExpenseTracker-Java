package main.expensemanager;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.IOException;

public class ExpenseAdderController  {
    public static ExpenseAdderController instance;
    public ExpenseAdderController() {
        instance =this;
    }

    @FXML
    private Pane addbtn;

    @FXML
    private TextField amounttext;

    @FXML
    private TextField datetxt;

    @FXML
    private TextField expensetxt;

    @FXML
    void addbtnprsd() {
        String expense = expensetxt.getText();
        double amount = Double.parseDouble(amounttext.getText());
        String date = datetxt.getText();
        MainAppController.getInstance().addExpensePane(expense,amount,date);

    }
    public static void secondstage() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("ExpenseAdder.fxml"));
        Stage stage = new Stage();
        Scene scene = new Scene(fxmlLoader.load());
        stage.setResizable(false);
        stage.setTitle("Hello!");
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(scene);
        stage.showAndWait();
        ExpenseAdderController.getInstance().close(stage);
    }
    public static  ExpenseAdderController getInstance() {
        return instance;
    }
    public void close(Stage stage) {
        addbtn.setOnMouseClicked(MouseEvent -> {stage.close();});
    }
}
