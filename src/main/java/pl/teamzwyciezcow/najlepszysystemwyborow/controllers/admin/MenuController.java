package pl.teamzwyciezcow.najlepszysystemwyborow.controllers.admin;

import javafx.fxml.FXML;
import pl.teamzwyciezcow.najlepszysystemwyborow.AppProvider;

public class MenuController {

    @FXML
    private void handleShowAdminHome() {
        AppProvider.getInstance().getMainController().loadView("admin/home");
    }

    @FXML
    private void handleShowAdminElections() {
        AppProvider.getInstance().getMainController().loadView("admin/elections/index");
    }

    @FXML
    private void handleLogout() {
        AppProvider.getInstance().getMainController().showDefaultMenu();
        AppProvider.getInstance().getMainController().loadView("home");
    }
}
