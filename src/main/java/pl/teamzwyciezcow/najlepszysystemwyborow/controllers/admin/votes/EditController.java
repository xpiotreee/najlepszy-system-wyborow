package pl.teamzwyciezcow.najlepszysystemwyborow.controllers.admin.votes;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.util.StringConverter;
import pl.teamzwyciezcow.najlepszysystemwyborow.AppProvider;
import pl.teamzwyciezcow.najlepszysystemwyborow.models.Candidate;
import pl.teamzwyciezcow.najlepszysystemwyborow.models.Vote;
import pl.teamzwyciezcow.najlepszysystemwyborow.services.VoteService;

import java.util.List;

public class EditController {

    @FXML private Label userLabel;
    @FXML private Label electionLabel;
    @FXML private Label currentCandidateLabel;
    @FXML private ComboBox<Candidate> candidateCombo;

    private Vote currentVote;

    public void setVote(Vote vote) {
        this.currentVote = vote;
        userLabel.setText(vote.getUser().getEmail());
        electionLabel.setText(vote.getElection().getTitle());
        currentCandidateLabel.setText(vote.getCandidate().getName());

        List<Candidate> candidates = vote.getElection().getCandidates();
        candidateCombo.setItems(FXCollections.observableArrayList(candidates));
        candidateCombo.setConverter(new StringConverter<>() {
            @Override
            public String toString(Candidate object) {
                return object == null ? "" : object.getName();
            }

            @Override
            public Candidate fromString(String string) {
                return null;
            }
        });
        
        // Pre-select current
        for (Candidate c : candidates) {
            if (c.getId().equals(vote.getCandidate().getId())) {
                candidateCombo.setValue(c);
                break;
            }
        }
    }

    @FXML
    private void handleSave() {
        Candidate newCandidate = candidateCombo.getValue();
        if (newCandidate == null) {
            showAlert("Błąd", "Wybierz kandydata.");
            return;
        }

        try {
            VoteService voteService = AppProvider.getInstance().getVoteService();
            // We modify the existing vote object directly and save it.
            // Check constraints? Logic in Service?
            // Service castVote creates NEW vote. We are updating existing.
            // We should use Repository save.
            
            // Check if user already voted for this candidate in this election (if multiple choice allowed duplicates? No)
            // But we are editing THIS vote.
            // If user has voted for Candidate A and Candidate B.
            // We change Vote A -> Candidate B.
            // Then user has 2 votes for Candidate B.
            // Constraints might block it (unique user, election, candidate).
            
            // Let's rely on DB constraint to throw exception if duplicate.
            currentVote.setCandidate(newCandidate);
            voteService.getRepository().save(currentVote);
            
            showAlert("Sukces", "Głos został zaktualizowany.");
            handleBack();
        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Błąd", "Nie udało się zaktualizować głosu: " + e.getMessage());
        }
    }

    @FXML
    private void handleBack() {
        AppProvider.getInstance().getMainController().loadView("admin/votes/index");
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
