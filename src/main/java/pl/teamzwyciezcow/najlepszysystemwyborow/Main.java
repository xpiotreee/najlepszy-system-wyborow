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
            // 1. Zlokalizowanie pliku FXML w zasobach projektu
            URL fxmlUrl = getClass().getResource("PanelLogowaniaAdmin.fxml");
            if (fxmlUrl == null) {
                System.err.println("Błąd: Nie znaleziono pliku PanelLogowaniaAdmin.fxml!");
                return;
            }

            // 2. Załadowanie hierarchii widoków z pliku FXML
            Parent root = FXMLLoader.load(fxmlUrl);

            // 3. Utworzenie sceny
            Scene scene = new Scene(root);

            // 4. Konfiguracja i wyświetlenie głównego okna (Stage)
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