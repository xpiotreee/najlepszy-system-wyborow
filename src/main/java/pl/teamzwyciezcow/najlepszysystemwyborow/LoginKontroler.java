import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LoginKontroler {

    // Wstrzyknięcie elementów z FXML
    @FXML
    private TextField emailField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Label messageLabel;

    /**
     * Metoda wywoływana po kliknięciu przycisku "Zaloguj"
     */
    @FXML
    private void handleLoginButton() {
        String email = emailField.getText();
        String password = passwordField.getText();

        if (email.equals("test@app.pl") && password.equals("pass123")) {
            messageLabel.setText("Zalogowano pomyślnie!");
            messageLabel.setStyle("-fx-text-fill: green;");
            // Tutaj nastąpiłoby przełączenie widoku
        } else {
            messageLabel.setText("Błędny email lub hasło.");
            messageLabel.setStyle("-fx-text-fill: red;");
        }
    }
}