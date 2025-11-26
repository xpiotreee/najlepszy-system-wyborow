package pl.teamzwyciezcow.najlepszysystemwyborow;

import io.ebean.DB;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import pl.teamzwyciezcow.najlepszysystemwyborow.models.User;
import pl.teamzwyciezcow.najlepszysystemwyborow.repositories.impl.UserRepositoryImpl;
import pl.teamzwyciezcow.najlepszysystemwyborow.services.UserService;
import pl.teamzwyciezcow.najlepszysystemwyborow.services.impl.UserServiceImpl;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("main.fxml"));
        stage.setTitle("Hello JavaFX!");
        stage.setScene(new Scene(root, 600, 400));
        stage.show();
    }

    public static void main(String[] args) {
        DbSetup.init();
        try {
            UserService users = new UserServiceImpl(new UserRepositoryImpl());
            User logged = users.login("testxowy@example.com", "passwordx");
            System.out.println("Logged in as: " + logged.getFullName());
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }

        launch(args);
    }
}
