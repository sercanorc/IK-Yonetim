package sample;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    @FXML
    public static Stage pencere;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        pencere = primaryStage;
        Parent arayuz;
        arayuz = FXMLLoader.load(getClass().getResource("anaEkran.fxml"));

        pencere.setTitle("İnsan Kaynakları Bilgi Sistemi");
        pencere.setScene(new Scene(arayuz,685,493));
        pencere.show();

        pencere.setOnCloseRequest(e -> {
            e.consume();
            pencere.close();
        });
    }


}
