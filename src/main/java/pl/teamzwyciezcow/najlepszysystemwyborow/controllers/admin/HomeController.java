package pl.teamzwyciezcow.najlepszysystemwyborow.controllers.admin;

import javafx.fxml.FXML;
import pl.teamzwyciezcow.najlepszysystemwyborow.AppProvider;

public class HomeController {

    @FXML
    private void handleManageElections() {
        AppProvider.getInstance().getMainController().loadView("admin/elections/index");
    }

    @FXML
    private void handleManageCandidates() {
        AppProvider.getInstance().getMainController().loadView("admin/candidates/index");
    }

    @FXML
    private void handleManageUsers() {
        AppProvider.getInstance().getMainController().loadView("admin/users/index");
    }

    @FXML
    private void handleManageVotes() {
        AppProvider.getInstance().getMainController().loadView("admin/votes/index");
    }
}
