package pl.teamzwyciezcow.najlepszysystemwyborow.controllers.user.elections;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button; 
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;
import pl.teamzwyciezcow.najlepszysystemwyborow.AppProvider;
import pl.teamzwyciezcow.najlepszysystemwyborow.models.Election;
import pl.teamzwyciezcow.najlepszysystemwyborow.services.ElectionService;

import java.time.LocalDateTime;
import java.util.List;

import pl.teamzwyciezcow.najlepszysystemwyborow.models.ResultVisibility;
import pl.teamzwyciezcow.najlepszysystemwyborow.models.User;
import pl.teamzwyciezcow.najlepszysystemwyborow.services.VoteService;

public class IndexController {

    @FXML
    private TableView<Election> electionTable;

    @FXML
    private TableColumn<Election, String> titleColumn;

    @FXML
    private TableColumn<Election, LocalDateTime> startDateColumn;

    @FXML
    private TableColumn<Election, LocalDateTime> endDateColumn;

    @FXML
    private TableColumn<Election, String> votesColumn;

    @FXML
    private TableColumn<Election, Void> actionColumn;

    private final ElectionService electionService;
    private final VoteService voteService;

    public IndexController() {
        this.electionService = AppProvider.getInstance().getElectionService();
        this.voteService = AppProvider.getInstance().getVoteService();
    }

    @FXML
    public void initialize() {
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        startDateColumn.setCellValueFactory(new PropertyValueFactory<>("startDate"));
        endDateColumn.setCellValueFactory(new PropertyValueFactory<>("endDate"));
        
        votesColumn.setCellValueFactory(cellData -> {
            Election election = cellData.getValue();
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
                return new SimpleStringProperty(String.valueOf(count));
            } else {
                return new SimpleStringProperty("-");
            }
        });

        setupActionColumn();
        loadElections();
    }

    private void setupActionColumn() {
        Callback<TableColumn<Election, Void>, TableCell<Election, Void>> cellFactory = param -> new TableCell<>() {
            
            private final Button voteButton = new Button("Pokaż");

            {
                
                voteButton.setOnAction(event -> {
                    Election election = getTableView().getItems().get(getIndex());
                    handleShow(election);
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(voteButton);
                }
            }
        };

        actionColumn.setCellFactory(cellFactory);
    }

    
    private void handleShow(Election election) {
        ViewController controller = (ViewController) AppProvider.getInstance().getMainController()
                .loadViewWithController("user/elections/view");
        
        if (controller != null) {
            controller.setElection(election);
        }
    }

    private void loadElections() {
        if (electionService != null) {
            List<Election> elections = electionService.getRepository().findAll(null);
            ObservableList<Election> electionList = FXCollections.observableArrayList(elections);
            electionTable.setItems(electionList);
        }
    }

    
    
}