package pl.teamzwyciezcow.najlepszysystemwyborow.controllers.admin.elections;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import pl.teamzwyciezcow.najlepszysystemwyborow.AppProvider;
import pl.teamzwyciezcow.najlepszysystemwyborow.models.Candidate;
import pl.teamzwyciezcow.najlepszysystemwyborow.models.Election;

import java.time.format.DateTimeFormatter;

public class ViewController {

    @FXML private Label titleLabel;
    @FXML private Label descriptionLabel;
    @FXML private Label startDateLabel;
    @FXML private Label endDateLabel;
    @FXML private Label visibilityLabel;
    @FXML private VBox candidatesBox;

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public void setElection(Election election) {
        if (election == null) return;

        titleLabel.setText(election.getTitle());
        descriptionLabel.setText(election.getDescription());
        startDateLabel.setText(election.getStartDate().format(formatter));
        endDateLabel.setText(election.getEndDate().format(formatter));
        visibilityLabel.setText(election.getResultVisibility().name()); // Could be improved with translation

        candidatesBox.getChildren().clear();
        if (election.getCandidates() != null) {
            for (Candidate candidate : election.getCandidates()) {
                VBox card = new VBox(5);
                card.getStyleClass().add("candidate-card"); // Assume this style exists or just standard box
                // Add simple styling if class doesn't exist
                card.setStyle("-fx-border-color: #ccc; -fx-border-radius: 5; -fx-padding: 10; -fx-background-color: rgba(255,255,255,0.1);");

                Label name = new Label(candidate.getName());
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
