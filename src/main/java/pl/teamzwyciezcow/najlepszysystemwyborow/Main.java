package pl.teamzwyciezcow.najlepszysystemwyborow;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import java.net.URL;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        try {
            URL fxmlUrl = getClass().getResource("PanelLogowaniaAdmin.fxml");
            if (fxmlUrl == null) {
                System.err.println("Błąd: Nie znaleziono pliku PanelLogowaniaAdmin.fxml!");
                return;
            }

            Parent root = FXMLLoader.load(fxmlUrl);

            Scene scene = new Scene(root);

            stage.setTitle("Logowanie FXML");
            stage.setScene(scene);
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}