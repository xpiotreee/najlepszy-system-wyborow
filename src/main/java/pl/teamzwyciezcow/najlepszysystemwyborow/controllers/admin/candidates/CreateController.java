package pl.teamzwyciezcow.najlepszysystemwyborow.controllers.admin.candidates;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.util.StringConverter;
import pl.teamzwyciezcow.najlepszysystemwyborow.AppProvider;
import pl.teamzwyciezcow.najlepszysystemwyborow.models.Candidate;
import pl.teamzwyciezcow.najlepszysystemwyborow.models.Election;
import pl.teamzwyciezcow.najlepszysystemwyborow.services.CandidateService;
import pl.teamzwyciezcow.najlepszysystemwyborow.services.ElectionService;

import java.util.List;

public class CreateController {

    @FXML private TextField nameField;
    @FXML private TextArea descriptionArea;
    @FXML private ListView<Election> electionListView;
    @FXML private Label headerLabel;
    @FXML private Button submitButton;

    private Long currentCandidateId = null;

    @FXML
    public void initialize() {
        setupElectionList();
    }

    private void setupElectionList() {
        ElectionService electionService = AppProvider.getInstance().getElectionService();
        List<Election> elections = electionService.getRepository().findAll(null);
        electionListView.setItems(FXCollections.observableArrayList(elections));
        electionListView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        
        electionListView.setCellFactory(param -> new ListCell<>() {
            @Override
            protected void updateItem(Election item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(item.getTitle());
                }
            }
        });
    }

    public void setCandidate(Candidate candidate) {
        if (candidate == null) return;
        
        this.currentCandidateId = candidate.getId();
        headerLabel.setText("Edycja Kandydata");
        submitButton.setText("ZAPISZ ZMIANY");

        nameField.setText(candidate.getName());
        descriptionArea.setText(candidate.getDescription());
        
        if (candidate.getElections() != null) {
            for (Election e : candidate.getElections()) {
                 for (Election item : electionListView.getItems()) {
                     if (item.getId() == e.getId()) {
                         electionListView.getSelectionModel().select(item);
                     }
                 }
            }
        }
    }

    @FXML
    private void handleSave() {
        if (!validateForm()) return;

        try {
            CandidateService candidateService = AppProvider.getInstance().getCandidateService();

            String name = nameField.getText();
            String description = descriptionArea.getText();
            List<Election> selectedElections = electionListView.getSelectionModel().getSelectedItems();
            List<Long> electionIds = selectedElections.stream().map(Election::getId).toList();
            
            if (currentCandidateId == null) {
                // CREATE
                candidateService.createCandidate(
                        electionIds,
                        name,
                        description,
                        null, 
                        null 
                );
                showAlert("Sukces", "Kandydat został utworzony pomyślnie!");
            } else {
                // UPDATE
                candidateService.updateCandidate(
                        currentCandidateId,
                        electionIds,
                        name,
                        description,
                        null,
                        null
                );
                showAlert("Sukces", "Kandydat został zaktualizowany pomyślnie!");
            }

            goBackToIndex();

        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Błąd", "Wystąpił błąd: " + e.getMessage());
        }
    }

    private void goBackToIndex() {
        AppProvider.getInstance().getMainController().loadView("admin/candidates/index");
    }

    private boolean validateForm() {
        if (nameField.getText().isEmpty() || nameField.getText().length() > 255) {
            showAlert("Błąd walidacji", "Nazwa jest wymagana i nie może przekraczać 255 znaków.");
            return false;
        }

        if (descriptionArea.getText().length() > 2000) {
             showAlert("Błąd walidacji", "Opis jest za długi (max 2000 znaków).");
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
