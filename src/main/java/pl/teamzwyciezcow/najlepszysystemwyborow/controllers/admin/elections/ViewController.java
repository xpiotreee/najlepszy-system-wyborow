package pl.teamzwyciezcow.najlepszysystemwyborow.controllers.admin.elections;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import pl.teamzwyciezcow.najlepszysystemwyborow.AppProvider;
import pl.teamzwyciezcow.najlepszysystemwyborow.models.Candidate;
import pl.teamzwyciezcow.najlepszysystemwyborow.models.Election;
import pl.teamzwyciezcow.najlepszysystemwyborow.services.VoteService;
import pl.teamzwyciezcow.najlepszysystemwyborow.utils.DateUtils;

public class ViewController {

    @FXML private Label titleLabel;
    @FXML private Label descriptionLabel;
    @FXML private Label startDateLabel;
    @FXML private Label endDateLabel;
    @FXML private Label visibilityLabel;
    @FXML private VBox candidatesBox;

    public void setElection(Election election) {
        if (election == null) return;

        titleLabel.setText(election.getTitle());
        descriptionLabel.setText(election.getDescription());
        startDateLabel.setText(DateUtils.format(election.getStartDate()));
        endDateLabel.setText(DateUtils.format(election.getEndDate()));
        visibilityLabel.setText(election.getResultVisibility().name()); // Could be improved with translation

        VoteService voteService = AppProvider.getInstance().getVoteService();

        candidatesBox.getChildren().clear();
        if (election.getCandidates() != null) {
            for (Candidate candidate : election.getCandidates()) {
                int votes = voteService.getCandidateVoteCount(candidate.getId(), election.getId());
                
                VBox card = new VBox(5);
                card.getStyleClass().add("candidate-card"); 
                card.setStyle("-fx-border-color: #ccc; -fx-border-radius: 5; -fx-padding: 10; -fx-background-color: rgba(255,255,255,0.1);");

                Label name = new Label(candidate.getName() + " - Głosów: " + votes);
                name.setStyle("-fx-font-weight: bold; -fx-font-size: 1.1em;");
                
                Label desc = new Label(candidate.getDescription());
                desc.setWrapText(true);

                card.getChildren().addAll(name, desc);
                candidatesBox.getChildren().add(card);
            }
        } else {
             candidatesBox.getChildren().add(new Label("Brak kandydatów"));
        }
    }

    @FXML
    private void handleBack() {
        AppProvider.getInstance().getMainController().loadView("admin/elections/index");
    }
}
