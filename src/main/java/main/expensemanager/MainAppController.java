package main.expensemanager;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;

public class MainAppController implements Initializable {
    public MainAppController() { instance = this; }
    @FXML
    private VBox expensevbox;
    @FXML
    private ScrollPane expensescrollPane, summaryScrollPane;
    private static MainAppController instance;
    @FXML
    public Pane titlePane,expensebtn,summarybtn;
    private double x,y;
    DataBase dataBase;
    @FXML
    private Pane expensePane, summaryPane;
    @FXML
    private ImageView close,minimise;
    @FXML
    private PieChart typePieChart,expensePieChart;
    @FXML
    private AnchorPane summaryanchor;

    public void init(Stage stage) {
        titlePane.setOnMousePressed(MouseEvent -> {
            x = MouseEvent.getSceneX();
            y = MouseEvent.getSceneY();
        });
        titlePane.setOnMouseDragged(MouseEvent -> {
            stage.setX(MouseEvent.getScreenX() - x);
            stage.setY(MouseEvent.getScreenY() - y);
        });
        close.setOnMouseClicked(MouseEvent -> stage.close());
        minimise.setOnMouseClicked(MouseEvent -> stage.setIconified(true));
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        expensevbox.setFillWidth(false);
        expensevbox.setSpacing(4);
        expensescrollPane.setContent(expensevbox);
        expensescrollPane.setFitToWidth(true);
        expensescrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        expensescrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        summaryScrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        summaryScrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        expensevbox.prefWidthProperty().bind(expensescrollPane.widthProperty());
        summaryanchor.prefWidthProperty().bind(summaryScrollPane.widthProperty());

        dataBase = new DataBase();
        try {
            addPastExpense(dataBase.returndata());
            setPiechart(piechartdata(dataBase.summarydata(dataBase.returndata())));
            setSummaryPieChart(dataBase.expensessummary(dataBase.returndata()));
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
        summaryPane.setVisible(false);
        expensebtn.getStyleClass().add("clicked");
        summarybtn.getStyleClass().remove("clicked");
    }

    @FXML
    private void setsPanevisible() {
       expensePane.setVisible(false);
       summaryPane.setVisible(true);
       expensebtn.getStyleClass().remove("clicked");
       summarybtn.getStyleClass().add("clicked");
    }
    @FXML
    private void deletebtn() {
        DataBase.deleteDataBase();
        expensevbox.getChildren().clear();
    }
    public static HashMap<String,Double> piechartdata(HashMap<String,Integer> summarydata) {
        HashMap<String,Double> piechartdata = new HashMap<>();
        Double iterations = Double.valueOf(summarydata.get("total iterations"));
        piechartdata.put("exp", summarydata.get("expense")/iterations);
        piechartdata.put("amt", summarydata.get("amount")/iterations);
        piechartdata.put("oth", summarydata.get("Others")/iterations);
        piechartdata.put("lei", summarydata.get("Leisure")/iterations);
        piechartdata.put("tra", summarydata.get("Travel")/iterations);
        piechartdata.put("nes", summarydata.get("Necessities")/iterations);
        piechartdata.put("ent", summarydata.get("Entertainment")/iterations);
        piechartdata.put("wor", summarydata.get("Work")/iterations);
        return piechartdata;
    }

    public void setPiechart(HashMap<String,Double> datapie) {
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList(
                new PieChart.Data("Others", datapie.get("oth")),
                new PieChart.Data("Leisure", datapie.get("lei")),
                new PieChart.Data("Travel", datapie.get("tra")),
                new PieChart.Data("Necessities", datapie.get("nes")),
                new PieChart.Data("Work", datapie.get("wor")),
                new PieChart.Data("Entertainment", datapie.get("ent"))
        );
        typePieChart.setData(pieChartData);
    }
    public void setSummaryPieChart(HashMap<String,Double> datamap) {
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();
        for (String key : datamap.keySet()) {
            pieChartData.add(new PieChart.Data(key, datamap.get(key)));
        }
            expensePieChart.setData(pieChartData);
    }
 }