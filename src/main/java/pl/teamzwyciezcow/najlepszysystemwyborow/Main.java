package pl.teamzwyciezcow.najlepszysystemwyborow;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Objects;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("GUI_do_logowania.fxml"));
        Scene scene = new Scene(root, 600, 400);
        String css = Objects.requireNonNull(this.getClass().getResource("main.css")).toExternalForm();
        scene.getStylesheets().add(css);

        stage.setTitle("Hello JavaFX!");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
