package pl.teamzwyciezcow.najlepszysystemwyborow.controllers;

import javafx.fxml.FXML;
import pl.teamzwyciezcow.najlepszysystemwyborow.AppProvider;

public class HomeController {

    @FXML
    private void handleGoToElections() {
        AppProvider.getInstance().getMainController().loadView("user/elections/index");
    }
}
