package pl.teamzwyciezcow.najlepszysystemwyborow.controllers.user.elections;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import pl.teamzwyciezcow.najlepszysystemwyborow.AppProvider;
import pl.teamzwyciezcow.najlepszysystemwyborow.models.Election;
import pl.teamzwyciezcow.najlepszysystemwyborow.models.ResultVisibility;
import pl.teamzwyciezcow.najlepszysystemwyborow.models.User;
import pl.teamzwyciezcow.najlepszysystemwyborow.services.VoteService;
import pl.teamzwyciezcow.najlepszysystemwyborow.utils.DateUtils;

import java.time.LocalDateTime;

public class CardController {

    @FXML
    private Label titleLabel;
    @FXML
    private Label datesLabel;
    @FXML
    private Label statusLabel;
    @FXML
    private Label votesLabel;

    private Election election;

    public void setElection(Election election) {
        this.election = election;
        titleLabel.setText(election.getTitle());
        datesLabel.setText(String.format("%s - %s", 
                DateUtils.format(election.getStartDate()), 
                DateUtils.format(election.getEndDate())));

        // Status Logic
        LocalDateTime now = LocalDateTime.now();
        if (now.isBefore(election.getStartDate())) {
            statusLabel.setText("Nadchodzące");
            statusLabel.setStyle("-fx-text-fill: grey;");
        } else if (now.isAfter(election.getEndDate())) {
            statusLabel.setText("Zakończone");
            statusLabel.setStyle("-fx-text-fill: red;");
        } else {
            statusLabel.setText("Trwające");
            statusLabel.setStyle("-fx-text-fill: green; -fx-font-weight: bold;");
        }

        // Votes Count Logic
        updateVotesVisibility();
    }

    private void updateVotesVisibility() {
        VoteService voteService = AppProvider.getInstance().getVoteService();
        boolean show = false;
        
        if (election.getResultVisibility() == ResultVisibility.ALWAYS) {
            show = true;
        } else if (election.getResultVisibility() == ResultVisibility.AFTER_CLOSE) {
            if (LocalDateTime.now().isAfter(election.getEndDate())) show = true;
        } else if (election.getResultVisibility() == ResultVisibility.AFTER_VOTE) {
             User user = AppProvider.getInstance().getUserService().getLoggedIn();
             if (user != null && voteService.hasVoted(user, election)) show = true;
        }
        
        if (show) {
            int count = voteService.getVoteCount(election.getId());
            votesLabel.setText("Głosów: " + count);
            votesLabel.setVisible(true);
        } else {
            votesLabel.setVisible(false);
        }
    }

    @FXML
    private void handleView() {
        ViewController controller = (ViewController) AppProvider.getInstance().getMainController()
                .loadViewWithController("user/elections/view");
        
        if (controller != null) {
            controller.setElection(election);
        }
    }
}
