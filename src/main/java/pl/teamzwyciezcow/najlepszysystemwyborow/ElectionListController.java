package pl.teamzwyciezcow.najlepszysystemwyborow;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
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
