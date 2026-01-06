package pl.teamzwyciezcow.najlepszysystemwyborow.controllers.admin.candidates;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;
import pl.teamzwyciezcow.najlepszysystemwyborow.AppProvider;
import pl.teamzwyciezcow.najlepszysystemwyborow.models.Candidate;
import pl.teamzwyciezcow.najlepszysystemwyborow.services.CandidateService;

import java.util.stream.Collectors;
import java.util.List;

public class IndexController {

    @FXML
    private TableView<Candidate> candidateTable;

    @FXML
    private TableColumn<Candidate, String> nameColumn;

    @FXML
    private TableColumn<Candidate, String> electionColumn;

    @FXML
    private TableColumn<Candidate, Void> actionColumn;

    private final CandidateService candidateService;

    public IndexController() {
        this.candidateService = AppProvider.getInstance().getCandidateService();
    }

    @FXML
    public void initialize() {
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        electionColumn.setCellValueFactory(cellData -> {
            if (cellData.getValue().getElections() != null && !cellData.getValue().getElections().isEmpty()) {
                List<String> titles = cellData.getValue().getElections().stream()
                        .map(e -> e.getTitle())
                        .toList();
                return new SimpleStringProperty(String.join(", ", titles));
            } else {
                return new SimpleStringProperty("Brak przypisania");
            }
        });

        setupActionColumn();
        loadCandidates();
    }

    @FXML
    private void handleCreateCandidate() {
        AppProvider.getInstance().getMainController().loadView("admin/candidates/create");
    }

    private void setupActionColumn() {
        Callback<TableColumn<Candidate, Void>, TableCell<Candidate, Void>> cellFactory = param -> new TableCell<>() {
            private final javafx.scene.control.MenuButton menuButton = new javafx.scene.control.MenuButton("Akcje");
            private final javafx.scene.control.MenuItem viewItem = new javafx.scene.control.MenuItem("Pokaż");
            private final javafx.scene.control.MenuItem editItem = new javafx.scene.control.MenuItem("Edytuj");
            private final javafx.scene.control.MenuItem deleteItem = new javafx.scene.control.MenuItem("Usuń");

            {
                viewItem.setOnAction(event -> {
                    Candidate candidate = getTableView().getItems().get(getIndex());
                    handleView(candidate);
                });
                editItem.setOnAction(event -> {
                    Candidate candidate = getTableView().getItems().get(getIndex());
                    handleEdit(candidate);
                });
                deleteItem.setOnAction(event -> {
                    Candidate candidate = getTableView().getItems().get(getIndex());
                    handleDelete(candidate);
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

    private void handleView(Candidate candidate) {
        ViewController controller = (ViewController) AppProvider.getInstance().getMainController().loadViewWithController("admin/candidates/view");
        if (controller != null) {
            controller.setCandidate(candidate);
        }
    }

    private void handleEdit(Candidate candidate) {
        CreateController controller = (CreateController) AppProvider.getInstance().getMainController().loadViewWithController("admin/candidates/create");
        if (controller != null) {
            controller.setCandidate(candidate);
        }
    }

    private void handleDelete(Candidate candidate) {
        this.candidateService.getRepository().deleteById(candidate.getId());
        loadCandidates();
    }

    private void loadCandidates() {
        if (candidateService != null) {
            List<Candidate> candidates = candidateService.getRepository().findAll(null);
            ObservableList<Candidate> candidateList = FXCollections.observableArrayList(candidates);
            candidateTable.setItems(candidateList);
        }
    }
}
