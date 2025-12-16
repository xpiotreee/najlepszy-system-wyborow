package pl.teamzwyciezcow.najlepszysystemwyborow.controllers.admin.elections;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import java.time.LocalDate;

public class CreateController {

    @FXML private TextField nazwaField, nowyKandydatNazwa, nowyKandydatOpis;
    @FXML private TextArea opisArea;
    @FXML private DatePicker dataOdPicker, dataDoPicker;
    @FXML private ComboBox<String> widocznoscCombo, kandydatSearchCombo;
    @FXML private VBox listaKandydatowBox;

    @FXML
    public void initialize() {
        // Przykładowe dane do wyszukiwarki
        kandydatSearchCombo.getItems().addAll("Jan Kowalski", "Anna Nowak", "Piotr Wiśniewski");
        // Przykładowe dane do widoczności (żeby nie było puste przy testowaniu)
        widocznoscCombo.getItems().addAll("Publiczne", "Prywatne", "Tylko dla grupy");
    }

    @FXML
    private void handleUtworzKandydata() {
        String nazwa = nowyKandydatNazwa.getText();
        if (nazwa == null || nazwa.isEmpty() || nazwa.length() > 255) {
            showAlert("Błąd", "Nazwa kandydata jest wymagana (max 255 znaków).");
            return;
        }

        Label label = new Label("• " + nazwa + " (Nowy)");
        listaKandydatowBox.getChildren().add(label);

        nowyKandydatNazwa.clear();
        nowyKandydatOpis.clear();
    }

    @FXML
    private void handleDodajKandydataZListy() {
        String wybrany = kandydatSearchCombo.getValue();
        if (wybrany != null) {
            listaKandydatowBox.getChildren().add(new Label("• " + wybrany));
        }
    }

    @FXML
    private void handleZapiszFormularz() {
        if (!validateForm()) return;

        System.out.println("=== ZAPISYWANIE FORMULARZA ===");
        System.out.println("Nazwa wyborów: " + nazwaField.getText());
        System.out.println("Opis: " + opisArea.getText());
        System.out.println("Data od: " + dataOdPicker.getValue());
        System.out.println("Data do: " + dataDoPicker.getValue());
        System.out.println("Widoczność: " + widocznoscCombo.getValue());

        System.out.println("--- Lista Kandydatów ---");
        if (listaKandydatowBox.getChildren().isEmpty()) {
            System.out.println("(Brak kandydatów)");
        } else {
            // Iterujemy po dzieciach VBoxa, aby wyciągnąć tekst z Labeli
            for (Node node : listaKandydatowBox.getChildren()) {
                if (node instanceof Label) {
                    Label lbl = (Label) node;
                    System.out.println(lbl.getText());
                }
            }
        }
        System.out.println("==============================");

        // Tutaj logika zapisu do bazy danych
    }

    private boolean validateForm() {
        if (nazwaField.getText().isEmpty() || nazwaField.getText().length() > 255) {
            showAlert("Błąd walidacji", "Nazwa jest wymagana i nie może przekraczać 255 znaków.");
            return false;
        }

        if (opisArea.getText().length() > 2000) {
            showAlert("Błąd walidacji", "Opis jest za długi (max 2000 znaków).");
            return false;
        }

        LocalDate od = dataOdPicker.getValue();
        LocalDate doData = dataDoPicker.getValue();

        if (od != null && doData != null && doData.isBefore(od)) {
            showAlert("Błąd daty", "Data 'Do kiedy' nie może być przed datą 'Od kiedy'.");
            return false;
        }

        return true;
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}