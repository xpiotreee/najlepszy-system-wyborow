package pl.teamzwyciezcow.najlepszysystemwyborow.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.BorderPane;
import pl.teamzwyciezcow.najlepszysystemwyborow.Main;

import java.io.IOException;

public class MainController implements NavigationController {
    @FXML
    BorderPane mainBorderPane;

    private Node defaultMenu;

    @FXML
    public void initialize() {
        defaultMenu = mainBorderPane.getTop();
    }

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

    @FXML
    private void handleShowElections() {
        loadView("user/elections/index");
    }

    @Override
    public void showAdminMenu() {
        try {
            FXMLLoader loader = new FXMLLoader(Main.class.getResource("views/admin/menu.fxml"));
            mainBorderPane.setTop(loader.load());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void showDefaultMenu() {
        mainBorderPane.setTop(defaultMenu);
    }

    public void loadView(String view) {
        loadViewWithController(view);
    }

    @Override
    public Object loadViewWithController(String view) {
        try {
            String path = "views/" + view + ".fxml";
            FXMLLoader loader = new FXMLLoader(Main.class.getResource(path));
            mainBorderPane.setCenter(loader.load());
            return loader.getController();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
