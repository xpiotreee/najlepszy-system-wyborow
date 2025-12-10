package pl.teamzwyciezcow.najlepszysystemwyborow.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.BorderPane;
import pl.teamzwyciezcow.najlepszysystemwyborow.Main;

import java.io.IOException;

public class MainController {
    @FXML
    BorderPane mainBorderPane;

    @FXML
    private void handleShowHomepage() {
        loadView("home");
    }

    @FXML
    private void handleShowAdmin() {
        loadView("admin/auth/login");
    }

    @FXML
    private void handleShowUser() {
        loadView("user/auth/login");
    }

    public void loadView(String view) {
        try {
            String path = "views/" + view + ".fxml";
            FXMLLoader loader = new FXMLLoader(Main.class.getResource(path));
            mainBorderPane.setCenter(loader.load());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
