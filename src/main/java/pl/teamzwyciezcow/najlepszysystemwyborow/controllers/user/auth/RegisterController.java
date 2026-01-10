package pl.teamzwyciezcow.najlepszysystemwyborow.controllers.user.auth;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import pl.teamzwyciezcow.najlepszysystemwyborow.AppProvider;
import pl.teamzwyciezcow.najlepszysystemwyborow.services.UserService;

public class RegisterController {

    @FXML private TextField firstNameField;
    @FXML private TextField lastNameField;
    @FXML private TextField emailField;
    @FXML private TextField peselField;
    @FXML private PasswordField passwordField;
    @FXML private PasswordField confirmPasswordField;

    @FXML
    private void handleRegisterSubmit() {
        String firstName = firstNameField.getText();
        String lastName = lastNameField.getText();
        String email = emailField.getText();
        String pesel = peselField.getText();
        String password = passwordField.getText();
        String confirmPassword = confirmPasswordField.getText();

        if (firstName.isEmpty() || lastName.isEmpty() || email.isEmpty() || pesel.isEmpty() || password.isEmpty()) {
            showAlert("Błąd", "Wszystkie pola są wymagane.");
            return;
        }

        if (!password.equals(confirmPassword)) {
            showAlert("Błąd", "Hasła nie są identyczne.");
            return;
        }

        UserService userService = AppProvider.getInstance().getUserService();
        String fullName = firstName + " " + lastName;

        try {
            userService.register(fullName, email, password, pesel);
            showAlert("Sukces", "Rejestracja pomyślna! Możesz się teraz zalogować.");
            AppProvider.getInstance().getMainController().loadView("user/auth/login");
        } catch (Exception e) {
            showAlert("Błąd rejestracji", e.getMessage());
        }
    }

    @FXML
    private void handleBackToLogin() {
        AppProvider.getInstance().getMainController().loadView("user/auth/login");
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
