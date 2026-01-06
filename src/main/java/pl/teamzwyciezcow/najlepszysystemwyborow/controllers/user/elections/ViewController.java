package pl.teamzwyciezcow.najlepszysystemwyborow.controllers.user.elections;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.VBox;
import pl.teamzwyciezcow.najlepszysystemwyborow.AppProvider;
import pl.teamzwyciezcow.najlepszysystemwyborow.models.Candidate;
import pl.teamzwyciezcow.najlepszysystemwyborow.models.Election;
import pl.teamzwyciezcow.najlepszysystemwyborow.models.User;
import pl.teamzwyciezcow.najlepszysystemwyborow.services.VoteService;

public class ViewController {

    @FXML
    private Label titleLabel;

    @FXML
    private Label descriptionLabel;

    @FXML
    private Label dateLabel;

    @FXML
    private VBox candidatesBox;

    @FXML
    private Button voteButton;
    
    @FXML
    private Label statusLabel;

    private Election currentElection;
    private ToggleGroup candidateGroup;

    public void setElection(Election election) {
        this.currentElection = election;
        titleLabel.setText(election.getTitle());
        descriptionLabel.setText(election.getDescription());
        dateLabel.setText("Od: " + election.getStartDate() + " Do: " + election.getEndDate());
        
        User user = AppProvider.getInstance().getUserService().getLoggedIn();
        VoteService voteService = AppProvider.getInstance().getVoteService();
        boolean isLoggedIn = user != null;
        boolean hasVoted = isLoggedIn && voteService.hasVoted(user, election);
        
        if (!isLoggedIn) {
            voteButton.setVisible(false);
            statusLabel.setText("Zaloguj się, aby zagłosować.");
            statusLabel.setStyle("-fx-text-fill: grey;");
        } else if (hasVoted) {
            voteButton.setVisible(false);
            statusLabel.setText("Już oddałeś głos w tych wyborach.");
            statusLabel.setStyle("-fx-text-fill: green;");
        } else {
            voteButton.setVisible(true);
            statusLabel.setText("");
        }

        candidatesBox.getChildren().clear();
        candidateGroup = new ToggleGroup();

        if (election.getCandidates() != null) {
            for (Candidate candidate : election.getCandidates()) {
                VBox candidateCard = new VBox(5);
                candidateCard.setStyle("-fx-border-color: #ddd; -fx-border-radius: 5; -fx-padding: 10; -fx-background-color: #f9f9f9;");
                
                Label nameLabel = new Label(candidate.getName());
                nameLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 14px;");
                
                Label descLabel = new Label(candidate.getDescription());
                descLabel.setWrapText(true);
                
                candidateCard.getChildren().addAll(nameLabel, descLabel);
                
                if (isLoggedIn && !hasVoted) {
                    RadioButton rb = new RadioButton("Wybierz");
                    rb.setUserData(candidate);
                    rb.setToggleGroup(candidateGroup);
                    candidateCard.getChildren().add(0, rb); // Add radio at top
                }
                
                candidatesBox.getChildren().add(candidateCard);
            }
        } else {
             candidatesBox.getChildren().add(new Label("Brak kandydatów do wyświetlenia."));
        }
    }
    
    @FXML
    private void handleVote() {
        if (candidateGroup.getSelectedToggle() == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Brak wyboru");
            alert.setHeaderText(null);
            alert.setContentText("Musisz wybrać kandydata, aby zagłosować.");
            alert.showAndWait();
            return;
        }

        Candidate selectedCandidate = (Candidate) candidateGroup.getSelectedToggle().getUserData();
        User user = AppProvider.getInstance().getUserService().getLoggedIn();
        
        try {
            AppProvider.getInstance().getVoteService().castVote(user, currentElection, selectedCandidate);
            
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Sukces");
            alert.setHeaderText(null);
            alert.setContentText("Twój głos został oddany!");
            alert.showAndWait();
            
            // Refresh view
            setElection(currentElection);
            
        } catch (Exception e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Błąd");
            alert.setHeaderText(null);
            alert.setContentText("Wystąpił błąd podczas głosowania: " + e.getMessage());
            alert.showAndWait();
        }
    }
}
