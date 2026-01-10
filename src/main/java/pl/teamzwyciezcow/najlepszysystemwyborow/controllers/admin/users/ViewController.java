package pl.teamzwyciezcow.najlepszysystemwyborow.controllers.admin.users;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import pl.teamzwyciezcow.najlepszysystemwyborow.AppProvider;
import pl.teamzwyciezcow.najlepszysystemwyborow.models.User;

public class ViewController {

    @FXML private Label nameLabel;
    @FXML private Label emailLabel;
    @FXML private Label peselLabel;

    public void setUser(User user) {
        if (user == null) return;
        nameLabel.setText(user.getFullName());
        emailLabel.setText(user.getEmail());
        peselLabel.setText(user.getPesel());
    }

    @FXML
    private void handleBack() {
        AppProvider.getInstance().getMainController().loadView("admin/users/index");
    }
}
