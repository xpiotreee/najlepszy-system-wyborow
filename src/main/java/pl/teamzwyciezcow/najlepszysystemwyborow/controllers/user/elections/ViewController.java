package pl.teamzwyciezcow.najlepszysystemwyborow.controllers.user.elections;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.VBox;
import javafx.scene.Node;
import pl.teamzwyciezcow.najlepszysystemwyborow.AppProvider;
import pl.teamzwyciezcow.najlepszysystemwyborow.models.Candidate;
import pl.teamzwyciezcow.najlepszysystemwyborow.models.Election;
import pl.teamzwyciezcow.najlepszysystemwyborow.models.ResultVisibility;
import pl.teamzwyciezcow.najlepszysystemwyborow.models.User;
import pl.teamzwyciezcow.najlepszysystemwyborow.models.Vote;
import pl.teamzwyciezcow.najlepszysystemwyborow.services.VoteService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
        List<Long> votedCandidateIds = new ArrayList<>();
        if (hasVoted) {
            votedCandidateIds = voteService.getUserVotes(user, election).stream()
                    .map(v -> v.getCandidate().getId())
                    .collect(Collectors.toList());
        }
        
        boolean showVotes = false;
        if (election.getResultVisibility() == ResultVisibility.ALWAYS) {
            showVotes = true;
        } else if (election.getResultVisibility() == ResultVisibility.AFTER_CLOSE) {
            if (LocalDateTime.now().isAfter(election.getEndDate())) showVotes = true;
        } else if (election.getResultVisibility() == ResultVisibility.AFTER_VOTE) {
             if (hasVoted) showVotes = true;
        }
        
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
                
                String style = "-fx-border-color: #ddd; -fx-border-radius: 5; -fx-padding: 10; -fx-background-color: #f9f9f9;";
                if (votedCandidateIds.contains(candidate.getId())) {
                    style = "-fx-border-color: green; -fx-border-width: 2; -fx-border-radius: 5; -fx-padding: 10; -fx-background-color: #e8f5e9;";
                }
                candidateCard.setStyle(style);
                
                String labelText = candidate.getName();
                if (showVotes) {
                    int votes = voteService.getCandidateVoteCount(candidate.getId(), election.getId());
                    labelText += " - Głosów: " + votes;
                }
                
                Label nameLabel = new Label(labelText);
                nameLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 14px;");
                
                Label descLabel = new Label(candidate.getDescription());
                descLabel.setWrapText(true);
                
                candidateCard.getChildren().addAll(nameLabel, descLabel);
                
                if (isLoggedIn && !hasVoted) {
                    if (election.getElectionType() == Election.ElectionType.MULTIPLE_CHOICE) {
                        CheckBox cb = new CheckBox("Wybierz");
                        cb.setUserData(candidate);
                        candidateCard.getChildren().add(0, cb);
                    } else {
                        RadioButton rb = new RadioButton("Wybierz");
                        rb.setUserData(candidate);
                        rb.setToggleGroup(candidateGroup);
                        candidateCard.getChildren().add(0, rb);
                    }
                }
                
                candidatesBox.getChildren().add(candidateCard);
            }
        } else {
             candidatesBox.getChildren().add(new Label("Brak kandydatów do wyświetlenia."));
        }
    }
    
    @FXML
    private void handleVote() {
        List<Candidate> selectedCandidates = new ArrayList<>();
        
        if (currentElection.getElectionType() == Election.ElectionType.MULTIPLE_CHOICE) {
            for (Node node : candidatesBox.getChildren()) {
                if (node instanceof VBox) {
                    VBox card = (VBox) node;
                    if (!card.getChildren().isEmpty() && card.getChildren().get(0) instanceof CheckBox) {
                        CheckBox cb = (CheckBox) card.getChildren().get(0);
                        if (cb.isSelected()) {
                            selectedCandidates.add((Candidate) cb.getUserData());
                        }
                    }
                }
            }
        } else {
             if (candidateGroup.getSelectedToggle() != null) {
                 selectedCandidates.add((Candidate) candidateGroup.getSelectedToggle().getUserData());
             }
        }

        if (selectedCandidates.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Brak wyboru");
            alert.setHeaderText(null);
            alert.setContentText("Musisz wybrać przynajmniej jednego kandydata.");
            alert.showAndWait();
            return;
        }

        User user = AppProvider.getInstance().getUserService().getLoggedIn();
        
        try {
            AppProvider.getInstance().getVoteService().castVotes(user, currentElection, selectedCandidates);
            
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
