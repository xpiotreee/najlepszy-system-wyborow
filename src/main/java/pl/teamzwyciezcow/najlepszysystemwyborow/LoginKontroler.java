import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

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

        if (email.equals("test@app.pl") && password.equals("pass123")) {
            messageLabel.setText("Zalogowano pomyślnie!");
            messageLabel.setStyle("-fx-text-fill: green;");
        } else {
            messageLabel.setText("Błędny email lub hasło.");
            messageLabel.setStyle("-fx-text-fill: red;");
        }
    }
}