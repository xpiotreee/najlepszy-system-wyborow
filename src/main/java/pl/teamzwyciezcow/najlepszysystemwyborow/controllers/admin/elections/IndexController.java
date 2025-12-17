package pl.teamzwyciezcow.najlepszysystemwyborow.controllers.admin.elections;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.util.Callback;
import pl.teamzwyciezcow.najlepszysystemwyborow.AppProvider;
import pl.teamzwyciezcow.najlepszysystemwyborow.models.Election;
import pl.teamzwyciezcow.najlepszysystemwyborow.services.ElectionService;

import java.time.LocalDateTime;
import java.util.List;

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
    private TableColumn<Election, Void> actionColumn;

    private final ElectionService electionService;

    public IndexController() {
        this.electionService = AppProvider.getInstance().getElectionService();
    }

    @FXML
    public void initialize() {
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        startDateColumn.setCellValueFactory(new PropertyValueFactory<>("startDate"));
        endDateColumn.setCellValueFactory(new PropertyValueFactory<>("endDate"));

        setupActionColumn();
        loadElections();
    }

    @FXML
    private void handleCreateElection() {
        AppProvider.getInstance().getMainController().loadView("admin/elections/create");
    }

    private void setupActionColumn() {
        Callback<TableColumn<Election, Void>, TableCell<Election, Void>> cellFactory = param -> new TableCell<>() {
            private final javafx.scene.control.MenuButton menuButton = new javafx.scene.control.MenuButton("Akcje");
            private final javafx.scene.control.MenuItem viewItem = new javafx.scene.control.MenuItem("Pokaż");
            private final javafx.scene.control.MenuItem editItem = new javafx.scene.control.MenuItem("Edytuj");
            private final javafx.scene.control.MenuItem deleteItem = new javafx.scene.control.MenuItem("Usuń");

            {
                viewItem.setOnAction(event -> {
                    Election election = getTableView().getItems().get(getIndex());
                    handleView(election);
                });
                editItem.setOnAction(event -> {
                    Election election = getTableView().getItems().get(getIndex());
                    handleEdit(election);
                });
                deleteItem.setOnAction(event -> {
                    Election election = getTableView().getItems().get(getIndex());
                    handleDelete(election);
                });
                
                menuButton.getItems().addAll(viewItem, editItem, deleteItem);
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

    private void handleView(Election election) {
        System.out.println("Podgląd wyborów: " + election.getTitle());
    }

    private void handleEdit(Election election) {
        System.out.println("Edycja wyborów: " + election.getTitle());
    }

    private void handleDelete(Election election) {
        System.out.println("Usuwanie wyborów: " + election.getTitle());
        this.electionService.getRepository().deleteById(election.getId());
        loadElections();
    }

    private void loadElections() {
        if (electionService != null) {
            List<Election> elections = electionService.getRepository().findAll(null);
            ObservableList<Election> electionList = FXCollections.observableArrayList(elections);
            electionTable.setItems(electionList);
        }
    }
}