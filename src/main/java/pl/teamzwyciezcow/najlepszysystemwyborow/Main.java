package pl.teamzwyciezcow.najlepszysystemwyborow;

import io.ebean.DB;
import io.ebean.migration.MigrationConfig;
import io.ebean.migration.MigrationRunner;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import pl.teamzwyciezcow.najlepszysystemwyborow.models.User;
import pl.teamzwyciezcow.najlepszysystemwyborow.*;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("main.fxml"));
        stage.setTitle("Hello JavaFX!");
        stage.setScene(new Scene(root, 600, 400));
        stage.show();
    }

    public static void main(String[] args) {
        MigrationConfig config = new MigrationConfig();
        config.setMigrationPath("dbmigration");;
        config.setDbUsername("");
        config.setDbPassword("");
        config.setDbUrl("jdbc:sqlite:database.db");

        MigrationRunner runner = new MigrationRunner(config);
        runner.run();

        User user = new User();
        user.setName("testowy");

        DB.save(user);
        User found = DB.find(User.class).where().eq("name", "testowy").findOne();
        assert found != null;
        System.out.println(found.getName());
        launch(args);
    }
}
