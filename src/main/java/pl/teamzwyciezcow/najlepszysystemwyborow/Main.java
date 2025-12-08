package pl.teamzwyciezcow.najlepszysystemwyborow;

import atlantafx.base.theme.PrimerLight;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import pl.teamzwyciezcow.najlepszysystemwyborow.models.User;
import pl.teamzwyciezcow.najlepszysystemwyborow.repositories.impl.UserRepositoryImpl;
import pl.teamzwyciezcow.najlepszysystemwyborow.services.UserService;
import pl.teamzwyciezcow.najlepszysystemwyborow.services.impl.UserServiceImpl;

import java.util.Objects;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        Application.setUserAgentStylesheet(new PrimerLight().getUserAgentStylesheet());
        Parent root = FXMLLoader.load(getClass().getResource("GUI_do_listy_wyborow.fxml"));
        Scene scene = new Scene(root, 600, 400);
        String css = Objects.requireNonNull(this.getClass().getResource("main.css")).toExternalForm();
        scene.getStylesheets().add(css);

        stage.setTitle("Hello JavaFX!");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        DbSetup.init();
        try {
            UserService users = new UserServiceImpl(new UserRepositoryImpl());
            User logged = users.login("testowy@example.com", "password");
            System.out.println("Logged in as: " + logged.getFullName());
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }

        launch(args);
    }
}