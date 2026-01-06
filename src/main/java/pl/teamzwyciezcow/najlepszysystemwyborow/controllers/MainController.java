package pl.teamzwyciezcow.najlepszysystemwyborow.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.BorderPane;
import pl.teamzwyciezcow.najlepszysystemwyborow.Main;

import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import pl.teamzwyciezcow.najlepszysystemwyborow.AppProvider;
import pl.teamzwyciezcow.najlepszysystemwyborow.models.User;

import java.io.IOException;

public class MainController implements NavigationController {
    @FXML
    BorderPane mainBorderPane;
    @FXML
    MenuButton userMenuButton;

    private Node defaultMenu;

    @FXML
    public void initialize() {
        defaultMenu = mainBorderPane.getTop();
        refreshMenu();
    }
    
    @Override
    public void refreshMenu() {
        if (userMenuButton == null) return; // Might be null if admin menu is active

        User loggedIn = AppProvider.getInstance().getUserService().getLoggedIn();
        userMenuButton.getItems().clear();

        if (loggedIn != null) {
            userMenuButton.setText(loggedIn.getFullName()); // Or "Profil"
            
            MenuItem profileItem = new MenuItem("Profil"); // Placeholder for now
            // profileItem.setOnAction(e -> loadView("user/profile")); 

            MenuItem logoutItem = new MenuItem("Wyloguj");
            logoutItem.setOnAction(e -> handleLogoutUser());

            userMenuButton.getItems().addAll(profileItem, logoutItem);
        } else {
            userMenuButton.setText("Login");
            
            MenuItem adminItem = new MenuItem("Admin");
            adminItem.setId("nav-admin");
            adminItem.setOnAction(e -> handleShowAdmin());

            MenuItem userItem = new MenuItem("User");
            userItem.setId("nav-user");
            userItem.setOnAction(e -> handleShowUser());

            userMenuButton.getItems().addAll(adminItem, userItem);
        }
    }

    private void handleLogoutUser() {
        AppProvider.getInstance().getUserService().logout();
        refreshMenu();
        handleShowHomepage();
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
        refreshMenu(); // Ensure state is correct when switching back
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
