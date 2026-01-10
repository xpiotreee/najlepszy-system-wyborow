package pl.teamzwyciezcow.najlepszysystemwyborow.controllers.admin.users;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import pl.teamzwyciezcow.najlepszysystemwyborow.AppProvider;
import pl.teamzwyciezcow.najlepszysystemwyborow.models.User;
import pl.teamzwyciezcow.najlepszysystemwyborow.services.UserService;

public class CreateController {

    @FXML private TextField fullNameField, emailField, peselField;
    @FXML private PasswordField passwordField;
    @FXML private Label headerLabel, passwordHint;
    @FXML private Button submitButton;

    private Long currentUserId = null;

    public void setUser(User user) {
        if (user == null) return;
        this.currentUserId = user.getId();
        headerLabel.setText("Edycja Użytkownika");
        submitButton.setText("ZAPISZ ZMIANY");
        passwordHint.setVisible(true);

        fullNameField.setText(user.getFullName());
        emailField.setText(user.getEmail());
        peselField.setText(user.getPesel());
    }

    @FXML
    private void handleSave() {
        if (!validateForm()) return;

        try {
            UserService userService = AppProvider.getInstance().getUserService();
            String fullName = fullNameField.getText();
            String email = emailField.getText();
            String pesel = peselField.getText();
            String password = passwordField.getText();

            if (currentUserId == null) {
                // CREATE
                if (password.isEmpty()) {
                    showAlert("Błąd", "Hasło jest wymagane przy tworzeniu użytkownika.");
                    return;
                }
                userService.createUser(fullName, email, password, pesel);
                showAlert("Sukces", "Użytkownik utworzony pomyślnie!");
            } else {
                // UPDATE
                userService.updateUser(currentUserId, fullName, email, password, pesel);
                showAlert("Sukces", "Użytkownik zaktualizowany pomyślnie!");
            }
            handleBack();
        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Błąd", "Wystąpił błąd: " + e.getMessage());
        }
    }

    @FXML
    private void handleBack() {
        AppProvider.getInstance().getMainController().loadView("admin/users/index");
    }

    private boolean validateForm() {
        if (fullNameField.getText().isEmpty() || emailField.getText().isEmpty() || peselField.getText().isEmpty()) {
            showAlert("Błąd", "Wszystkie pola (oprócz hasła przy edycji) są wymagane.");
            return false;
        }
        return true;
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
