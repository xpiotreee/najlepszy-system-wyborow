package pl.teamzwyciezcow.najlepszysystemwyborow.controllers.admin.auth;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import pl.teamzwyciezcow.najlepszysystemwyborow.AppProvider;
import pl.teamzwyciezcow.najlepszysystemwyborow.models.Admin;
import pl.teamzwyciezcow.najlepszysystemwyborow.services.AdminService;

public class LoginController {

    @FXML
    private TextField emailField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private void handleLogin() {
        String email = emailField.getText();
        String password = passwordField.getText();
        AdminService adminService = AppProvider.getInstance().getAdminService();

        try {
            Admin admin = adminService.login(email, password);
            
            AppProvider.getInstance().getMainController().showAdminMenu();
            AppProvider.getInstance().getMainController().loadView("admin/home");
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Błąd logowania");
            alert.setHeaderText(null);
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }
}