package main.expensemanager;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class MainApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("ExpenseManagerGUI.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
       stage.setResizable(false);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        scene.setFill(Color.TRANSPARENT);
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.show();
        ((MainAppController)fxmlLoader.getController()).init(stage);
    }

    public static void run() {
        launch();
    }
}