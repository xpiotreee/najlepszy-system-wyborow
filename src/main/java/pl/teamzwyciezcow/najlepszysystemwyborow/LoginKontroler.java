package pl.teamzwyciezcow.najlepszysystemwyborow;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import pl.teamzwyciezcow.najlepszysystemwyborow.AppProvider;
import pl.teamzwyciezcow.najlepszysystemwyborow.models.Admin;
import pl.teamzwyciezcow.najlepszysystemwyborow.services.AdminService;

public class LoginKontroler {

    @FXML
    private TextField emailField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Label messageLabel;

    @FXML
    private void handleLoginButton() {
        String email = emailField.getText();
        String password = passwordField.getText();
        AdminService adminService = AppProvider.getInstance().getAdminService();

        try {
            Admin admin = adminService.login(email, password);
            messageLabel.setText("Zalogowano pomyślnie jako " + admin.getFullName() + "!");
            messageLabel.setStyle("-fx-text-fill: green;");
        } catch (Exception e) {
            messageLabel.setText("Error " + e.getMessage());
            messageLabel.setStyle("-fx-text-fill: red;");
        }
    }
}