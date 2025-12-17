package pl.teamzwyciezcow.najlepszysystemwyborow.controllers.admin.elections;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.StringConverter;
import pl.teamzwyciezcow.najlepszysystemwyborow.AppProvider;
import pl.teamzwyciezcow.najlepszysystemwyborow.models.Candidate;
import pl.teamzwyciezcow.najlepszysystemwyborow.models.Election;
import pl.teamzwyciezcow.najlepszysystemwyborow.models.ResultVisibility;
import pl.teamzwyciezcow.najlepszysystemwyborow.services.CandidateService;
import pl.teamzwyciezcow.najlepszysystemwyborow.services.ElectionService;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class CreateController {

    @FXML private TextField nazwaField, nowyKandydatNazwa, nowyKandydatOpis;
    @FXML private TextArea opisArea;
    @FXML private DatePicker dataOdPicker, dataDoPicker;
    @FXML private ComboBox<ResultVisibility> widocznoscCombo;
    @FXML private ComboBox<Candidate> kandydatSearchCombo;
    @FXML private VBox listaKandydatowBox;

    
    private static class CandidateDTO {
        String name;
        String description;
        
        
        CandidateDTO(String name, String description) {
            this.name = name;
            this.description = description;
        }
    }

    private final List<CandidateDTO> candidatesToAdd = new ArrayList<>();

    @FXML
    public void initialize() {
        setupVisibilityCombo();
        setupCandidateSearch();
    }

    private void setupVisibilityCombo() {
        widocznoscCombo.setItems(FXCollections.observableArrayList(ResultVisibility.values()));
        widocznoscCombo.setConverter(new StringConverter<>() {
            @Override
            public String toString(ResultVisibility object) {
                if (object == null) return "";
                switch (object) {
                    case ALWAYS: return "Zawsze";
                    case AFTER_VOTE: return "Po zagłosowaniu";
                    case AFTER_CLOSE: return "Po zakończeniu wyborów";
                    default: return object.name();
                }
            }

            @Override
            public ResultVisibility fromString(String string) {
                return null; 
            }
        });
        widocznoscCombo.getSelectionModel().selectFirst();
    }

    private void setupCandidateSearch() {
        CandidateService candidateService = AppProvider.getInstance().getCandidateService();
        List<Candidate> allCandidates = candidateService.getRepository().findAll(null); 
        kandydatSearchCombo.setItems(FXCollections.observableArrayList(allCandidates));
        
        kandydatSearchCombo.setConverter(new StringConverter<>() {
            @Override
            public String toString(Candidate object) {
                return object == null ? "" : object.getName();
            }

            @Override
            public Candidate fromString(String string) {
                return null;
            }
        });
    }

    @FXML
    private void handleUtworzKandydata() {
        String nazwa = nowyKandydatNazwa.getText();
        String opis = nowyKandydatOpis.getText();
        
        if (nazwa == null || nazwa.isEmpty() || nazwa.length() > 255) {
            showAlert("Błąd", "Nazwa kandydata jest wymagana (max 255 znaków).");
            return;
        }

        addCandidateToList(new CandidateDTO(nazwa, opis), "Nowy");
        
        nowyKandydatNazwa.clear();
        nowyKandydatOpis.clear();
    }

    @FXML
    private void handleDodajKandydataZListy() {
        Candidate selected = kandydatSearchCombo.getValue();
        if (selected != null) {
            
            addCandidateToList(new CandidateDTO(selected.getName(), selected.getDescription()), "Z bazy");
            kandydatSearchCombo.getSelectionModel().clearSelection();
        }
    }

    private void addCandidateToList(CandidateDTO candidate, String source) {
        candidatesToAdd.add(candidate);
        
        HBox row = new HBox(10);
        Label label = new Label("• " + candidate.name + " (" + source + ")");
        Button removeBtn = new Button("X");
        removeBtn.setOnAction(e -> {
            candidatesToAdd.remove(candidate);
            listaKandydatowBox.getChildren().remove(row);
        });
        
        row.getChildren().addAll(label, removeBtn);
        listaKandydatowBox.getChildren().add(row);
    }

    @FXML
    private void handleZapiszFormularz() {
        if (!validateForm()) return;

        try {
            ElectionService electionService = AppProvider.getInstance().getElectionService();
            CandidateService candidateService = AppProvider.getInstance().getCandidateService();

            String title = nazwaField.getText();
            String description = opisArea.getText();
            
            
            java.time.LocalDateTime startDateTime = dataOdPicker.getValue().atStartOfDay();
            java.time.LocalDateTime endDateTime = dataDoPicker.getValue().atTime(LocalTime.MAX); 
            ResultVisibility visibility = widocznoscCombo.getValue();

            
            
            Election election = electionService.createElection(
                    title, 
                    description, 
                    startDateTime, 
                    endDateTime, 
                    visibility, 
                    new ArrayList<>() 
            );

            
            for (CandidateDTO dto : candidatesToAdd) {
                candidateService.createCandidate(
                        election.getId(),
                        dto.name,
                        dto.description,
                        null, 
                        null  
                );
            }

            showAlert("Sukces", "Wybory zostały utworzone pomyślnie!");
            goBackToIndex();

        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Błąd", "Wystąpił błąd podczas tworzenia wyborów: " + e.getMessage());
        }
    }
    
    private void goBackToIndex() {
        AppProvider.getInstance().getMainController().loadView("admin/elections/index");
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
        
        if (dataOdPicker.getValue() == null || dataDoPicker.getValue() == null) {
            showAlert("Błąd walidacji", "Daty rozpoczęcia i zakończenia są wymagane.");
            return false;
        }

        LocalDate od = dataOdPicker.getValue();
        LocalDate doData = dataDoPicker.getValue();

        if (doData.isBefore(od)) {
            showAlert("Błąd daty", "Data 'Do kiedy' nie może być przed datą 'Od kiedy'.");
            return false;
        }
        
        if (candidatesToAdd.isEmpty()) {
             showAlert("Błąd walidacji", "Musisz dodać co najmniej jednego kandydata.");
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