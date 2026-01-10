package pl.teamzwyciezcow.najlepszysystemwyborow.controllers.admin.votes;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.util.Callback;
import pl.teamzwyciezcow.najlepszysystemwyborow.AppProvider;
import pl.teamzwyciezcow.najlepszysystemwyborow.models.Vote;
import pl.teamzwyciezcow.najlepszysystemwyborow.services.VoteService;

import java.util.List;

public class IndexController {

    @FXML private TableView<Vote> voteTable;
    @FXML private TableColumn<Vote, String> userColumn;
    @FXML private TableColumn<Vote, String> electionColumn;
    @FXML private TableColumn<Vote, String> candidateColumn;
    @FXML private TableColumn<Vote, Void> actionColumn;

    private final VoteService voteService;

    public IndexController() {
        this.voteService = AppProvider.getInstance().getVoteService();
    }

    @FXML
    public void initialize() {
        userColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getUser().getEmail()));
        electionColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getElection().getTitle()));
        candidateColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCandidate().getName()));

        setupActionColumn();
        loadVotes();
    }

    private void setupActionColumn() {
        Callback<TableColumn<Vote, Void>, TableCell<Vote, Void>> cellFactory = param -> new TableCell<>() {
            private final javafx.scene.control.MenuButton menuButton = new javafx.scene.control.MenuButton("Akcje");
            private final javafx.scene.control.MenuItem editItem = new javafx.scene.control.MenuItem("Edytuj (Przenieś)");
            private final javafx.scene.control.MenuItem deleteItem = new javafx.scene.control.MenuItem("Usuń");

            {
                editItem.setOnAction(event -> {
                    Vote vote = getTableView().getItems().get(getIndex());
                    handleEdit(vote);
                });
                deleteItem.setOnAction(event -> {
                    Vote vote = getTableView().getItems().get(getIndex());
                    handleDelete(vote);
                });
                menuButton.getItems().addAll(editItem, deleteItem);
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(menuButton);
                }
            }
        };
        actionColumn.setCellFactory(cellFactory);
    }

    private void handleEdit(Vote vote) {
        EditController controller = (EditController) AppProvider.getInstance().getMainController().loadViewWithController("admin/votes/edit");
        if (controller != null) {
            controller.setVote(vote);
        }
    }

    private void handleDelete(Vote vote) {
        voteService.getRepository().deleteById(vote.getId());
        loadVotes();
    }

    private void loadVotes() {
        if (voteService != null) {
            List<Vote> votes = voteService.getRepository().findAll();
            ObservableList<Vote> voteList = FXCollections.observableArrayList(votes);
            voteTable.setItems(voteList);
        }
    }
}
