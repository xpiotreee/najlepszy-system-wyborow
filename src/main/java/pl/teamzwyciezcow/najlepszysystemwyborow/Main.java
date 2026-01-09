package pl.teamzwyciezcow.najlepszysystemwyborow;

import atlantafx.base.theme.PrimerLight;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import pl.teamzwyciezcow.najlepszysystemwyborow.controllers.NavigationController;
import pl.teamzwyciezcow.najlepszysystemwyborow.models.User;
import pl.teamzwyciezcow.najlepszysystemwyborow.repositories.impl.UserRepositoryImpl;
import pl.teamzwyciezcow.najlepszysystemwyborow.services.UserService;
import pl.teamzwyciezcow.najlepszysystemwyborow.services.impl.UserServiceImpl;

import java.util.Objects;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        Application.setUserAgentStylesheet(new PrimerLight().getUserAgentStylesheet());

        FXMLLoader loader = new FXMLLoader(getClass().getResource("views/main.fxml"));
        Parent root = loader.load();
        NavigationController mainController = loader.getController();
        AppProvider.getInstance().setMainController(mainController);

        Scene scene = new Scene(root, 1000, 700);
        String css = Objects.requireNonNull(this.getClass().getResource("main.css")).toExternalForm();
        String homeCss = Objects.requireNonNull(this.getClass().getResource("home.css")).toExternalForm();
        scene.getStylesheets().addAll(css, homeCss);

        stage.setTitle("Najlepszy system wyborów");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        DbSetup.init();
        launch(args);
    }
}