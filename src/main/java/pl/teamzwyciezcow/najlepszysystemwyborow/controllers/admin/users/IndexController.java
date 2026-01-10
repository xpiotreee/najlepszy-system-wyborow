package pl.teamzwyciezcow.najlepszysystemwyborow.controllers.admin.users;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.util.Callback;
import pl.teamzwyciezcow.najlepszysystemwyborow.AppProvider;
import pl.teamzwyciezcow.najlepszysystemwyborow.models.User;
import pl.teamzwyciezcow.najlepszysystemwyborow.services.UserService;

import java.util.List;

public class IndexController {

    @FXML private TableView<User> userTable;
    @FXML private TableColumn<User, String> nameColumn;
    @FXML private TableColumn<User, String> emailColumn;
    @FXML private TableColumn<User, String> peselColumn;
    @FXML private TableColumn<User, Void> actionColumn;

    private final UserService userService;

    public IndexController() {
        this.userService = AppProvider.getInstance().getUserService();
    }

    @FXML
    public void initialize() {
        nameColumn.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getFullName()));
        emailColumn.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getEmail()));
        peselColumn.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getPesel()));

        setupActionColumn();
        loadUsers();
    }

    @FXML
    private void handleCreateUser() {
        AppProvider.getInstance().getMainController().loadView("admin/users/create");
    }

    private void setupActionColumn() {
        Callback<TableColumn<User, Void>, TableCell<User, Void>> cellFactory = param -> new TableCell<>() {
            private final javafx.scene.control.MenuButton menuButton = new javafx.scene.control.MenuButton("Akcje");
            private final javafx.scene.control.MenuItem viewItem = new javafx.scene.control.MenuItem("Pokaż");
            private final javafx.scene.control.MenuItem editItem = new javafx.scene.control.MenuItem("Edytuj");
            private final javafx.scene.control.MenuItem deleteItem = new javafx.scene.control.MenuItem("Usuń");

            {
                viewItem.setOnAction(event -> {
                    User user = getTableView().getItems().get(getIndex());
                    handleView(user);
                });
                editItem.setOnAction(event -> {
                    User user = getTableView().getItems().get(getIndex());
                    handleEdit(user);
                });
                deleteItem.setOnAction(event -> {
                    User user = getTableView().getItems().get(getIndex());
                    handleDelete(user);
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

    private void handleView(User user) {
        ViewController controller = (ViewController) AppProvider.getInstance().getMainController().loadViewWithController("admin/users/view");
        if (controller != null) {
            controller.setUser(user);
        }
    }

    private void handleEdit(User user) {
        CreateController controller = (CreateController) AppProvider.getInstance().getMainController().loadViewWithController("admin/users/create");
        if (controller != null) {
            controller.setUser(user);
        }
    }

    private void handleDelete(User user) {
        userService.getRepository().deleteById(user.getId());
        loadUsers();
    }

    private void loadUsers() {
        if (userService != null) {
            List<User> users = userService.getRepository().findAll(null, null);
            ObservableList<User> userList = FXCollections.observableArrayList(users);
            userTable.setItems(userList);
        }
    }
}
