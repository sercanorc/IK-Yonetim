package sample;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    @FXML
    public static Stage windows;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        windows = primaryStage;
        Parent guı;
        guı = FXMLLoader.load(getClass().getResource("anaEkran.fxml"));

        windows.setTitle("İK bilgi sistemi");
        windows.setScene(new Scene(guı,685,493));
        windows.show();

        windows.setOnCloseRequest(e -> {
            e.consume();
            windows.close();
        });
    }


}
