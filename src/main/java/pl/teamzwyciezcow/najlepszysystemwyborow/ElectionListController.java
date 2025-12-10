package pl.teamzwyciezcow.najlepszysystemwyborow;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;
import javafx.application.Platform;
import pl.teamzwyciezcow.najlepszysystemwyborow.models.Election;
import pl.teamzwyciezcow.najlepszysystemwyborow.services.ElectionService;

import java.time.LocalDateTime;
import java.util.List;

public class ElectionListController {

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

    public ElectionListController() {
        this.electionService = AppProvider.getInstance().getElectionService();
    }

    @FXML
    public void initialize() {
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        startDateColumn.setCellValueFactory(new PropertyValueFactory<>("startDate"));
        endDateColumn.setCellValueFactory(new PropertyValueFactory<>("endDate"));
        votesColumn.setCellValueFactory(cellData -> new SimpleStringProperty("0")); 

        setupActionColumn();
        loadElections();
    }

    private void setupActionColumn() {
        Callback<TableColumn<Election, Void>, TableCell<Election, Void>> cellFactory = param -> new TableCell<>() {
            private final ComboBox<String> comboBox = new ComboBox<>();

            {
                comboBox.getItems().addAll("Zobacz", "Edytuj", "Usuń");
                comboBox.setPromptText("Opcje");
                comboBox.setMaxWidth(Double.MAX_VALUE);
                
                comboBox.setOnAction(event -> {
                    String selectedOption = comboBox.getSelectionModel().getSelectedItem();
                    Election election = getTableView().getItems().get(getIndex());

                    if (selectedOption != null) {
                        switch (selectedOption) {
                            case "Zobacz":
                                System.out.println("Zobacz election: " + election.getTitle());
                                break;
                            case "Edytuj":
                                System.out.println("Edytuj election: " + election.getTitle());
                                break;
                            case "Usuń":
                                System.out.println("Usuń election: " + election.getTitle());
                                deleteElection(election);
                                break;
                        }

                        Platform.runLater(() -> comboBox.getSelectionModel().clearSelection());
                    }
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(comboBox);
                }
            }
        };

        actionColumn.setCellFactory(cellFactory);
    }

    private void deleteElection(Election election) {
        if (electionService != null) {
            electionService.getRepository().deleteById(election.getId());
            loadElections();
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
