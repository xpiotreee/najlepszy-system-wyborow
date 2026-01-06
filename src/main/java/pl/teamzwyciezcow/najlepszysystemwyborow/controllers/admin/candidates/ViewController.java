package pl.teamzwyciezcow.najlepszysystemwyborow.controllers.admin.candidates;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import pl.teamzwyciezcow.najlepszysystemwyborow.AppProvider;
import pl.teamzwyciezcow.najlepszysystemwyborow.models.Candidate;

public class ViewController {

    @FXML private Label nameLabel;
    @FXML private Label descriptionLabel;
    @FXML private Label electionLabel;

    public void setCandidate(Candidate candidate) {
        if (candidate == null) return;

        nameLabel.setText(candidate.getName());
        descriptionLabel.setText(candidate.getDescription());
        
        if (candidate.getElections() != null && !candidate.getElections().isEmpty()) {
            String elections = candidate.getElections().stream()
                    .map(e -> e.getTitle())
                    .reduce((a, b) -> a + ", " + b)
                    .orElse("");
            electionLabel.setText(elections);
        } else {
            electionLabel.setText("Brak przypisania");
        }
    }

    @FXML
    private void handleBack() {
        AppProvider.getInstance().getMainController().loadView("admin/candidates/index");
    }
}
