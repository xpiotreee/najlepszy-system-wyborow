package pl.teamzwyciezcow.najlepszysystemwyborow;

import io.ebean.DB;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import pl.teamzwyciezcow.najlepszysystemwyborow.models.User;

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
//        try {
//            DbSetup.run();
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }

        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedNow = now.format(formatter);

        String name = "testowy_" +  formattedNow;
        User user = new User(name, "123123120", name + "@example.com", "password");

        DB.save(user);
        User found = DB.find(User.class).where().eq("name", name).findOne();
        assert found != null;
        System.out.println(found.getFullName());
        launch(args);
    }
}
