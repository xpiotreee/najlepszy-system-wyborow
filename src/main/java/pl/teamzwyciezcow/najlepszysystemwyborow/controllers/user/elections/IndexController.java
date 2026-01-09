package pl.teamzwyciezcow.najlepszysystemwyborow.controllers.user.elections;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.VBox;
import pl.teamzwyciezcow.najlepszysystemwyborow.AppProvider;
import pl.teamzwyciezcow.najlepszysystemwyborow.Main;
import pl.teamzwyciezcow.najlepszysystemwyborow.models.Election;
import pl.teamzwyciezcow.najlepszysystemwyborow.services.ElectionService;

import java.io.IOException;
import java.util.List;

public class IndexController {

    @FXML
    private VBox cardsContainer;

    private final ElectionService electionService;

    public IndexController() {
        this.electionService = AppProvider.getInstance().getElectionService();
    }

    @FXML
    public void initialize() {
        loadElections();
    }

    private void loadElections() {
        if (electionService != null) {
            List<Election> elections = electionService.getRepository().findAll(null);
            cardsContainer.getChildren().clear();
            
            for (Election election : elections) {
                try {
                    FXMLLoader loader = new FXMLLoader(Main.class.getResource("views/user/elections/card.fxml"));
                    Node cardNode = loader.load();
                    CardController controller = loader.getController();
                    controller.setElection(election);
                    cardsContainer.getChildren().add(cardNode);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}