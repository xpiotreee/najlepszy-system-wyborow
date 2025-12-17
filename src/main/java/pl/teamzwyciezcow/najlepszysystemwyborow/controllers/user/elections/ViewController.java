package pl.teamzwyciezcow.najlepszysystemwyborow.controllers.user.elections;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import pl.teamzwyciezcow.najlepszysystemwyborow.models.Candidate;
import pl.teamzwyciezcow.najlepszysystemwyborow.models.Election;

public class ViewController {

    @FXML
    private Label titleLabel;

    @FXML
    private Label descriptionLabel;

    @FXML
    private Label dateLabel;

    @FXML
    private VBox candidatesBox;

    public void setElection(Election election) {
        titleLabel.setText(election.getTitle());
        descriptionLabel.setText(election.getDescription());
        dateLabel.setText("Od: " + election.getStartDate() + " Do: " + election.getEndDate());
        
        candidatesBox.getChildren().clear();
        if (election.getCandidates() != null) {
            for (Candidate candidate : election.getCandidates()) {
                VBox candidateCard = new VBox(5);
                candidateCard.setStyle("-fx-border-color: #ddd; -fx-border-radius: 5; -fx-padding: 10; -fx-background-color: #f9f9f9;");
                
                Label nameLabel = new Label(candidate.getName());
                nameLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 14px;");
                
                Label descLabel = new Label(candidate.getDescription());
                descLabel.setWrapText(true);
                
                candidateCard.getChildren().addAll(nameLabel, descLabel);
                candidatesBox.getChildren().add(candidateCard);
            }
        } else {
             candidatesBox.getChildren().add(new Label("Brak kandydatów do wyświetlenia."));
        }
    }
}
