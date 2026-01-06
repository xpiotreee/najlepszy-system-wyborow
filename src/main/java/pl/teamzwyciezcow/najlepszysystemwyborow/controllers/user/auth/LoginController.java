package pl.teamzwyciezcow.najlepszysystemwyborow.controllers.user.auth;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import pl.teamzwyciezcow.najlepszysystemwyborow.AppProvider;

public class LoginController {
    @FXML
    private TextField emailField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private void handleLogin() {
        String email = emailField.getText();
        String password = passwordField.getText();

        try {
            AppProvider.getInstance().getUserService().login(email, password);
            AppProvider.getInstance().getMainController().refreshMenu();
            AppProvider.getInstance().getMainController().loadView("user/elections/index");
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Błąd logowania");
            alert.setHeaderText(null);
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }

    @FXML
    private void handleBack() {
        AppProvider.getInstance().getMainController().showDefaultMenu();
        AppProvider.getInstance().getMainController().loadView("home");
    }
    
    @FXML
    private void handleRegister() {
         AppProvider.getInstance().getMainController().loadView("user/auth/register");
    }
}
