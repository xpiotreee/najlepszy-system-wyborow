package pl.teamzwyciezcow.najlepszysystemwyborow.controllers.user.profile;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.util.Callback;
import pl.teamzwyciezcow.najlepszysystemwyborow.AppProvider;
import pl.teamzwyciezcow.najlepszysystemwyborow.controllers.user.elections.ViewController;
import pl.teamzwyciezcow.najlepszysystemwyborow.models.Election;
import pl.teamzwyciezcow.najlepszysystemwyborow.models.User;
import pl.teamzwyciezcow.najlepszysystemwyborow.models.Vote;
import pl.teamzwyciezcow.najlepszysystemwyborow.services.UserService;
import pl.teamzwyciezcow.najlepszysystemwyborow.services.VoteService;

public class ProfileController {

    @FXML private TextField fullNameField;
    @FXML private TextField emailField;
    @FXML private TextField peselField;
    @FXML private PasswordField passwordField;
    
    @FXML private Button editButton;
    @FXML private Button saveButton;
    
    @FXML private TableView<Vote> votesTable;
    @FXML private TableColumn<Vote, String> electionColumn;
    @FXML private TableColumn<Vote, String> candidateColumn;
    @FXML private TableColumn<Vote, Void> actionColumn;

    private User currentUser;
    private boolean isEditing = false;

    @FXML
    public void initialize() {
        UserService userService = AppProvider.getInstance().getUserService();
        currentUser = userService.getLoggedIn();
        
        if (currentUser == null) return;

        fullNameField.setText(currentUser.getFullName());
        emailField.setText(currentUser.getEmail());
        peselField.setText(currentUser.getPesel());
        
        setupTable();
        loadVotes();
    }
    
    private void setupTable() {
        electionColumn.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getElection().getTitle()));
        candidateColumn.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getCandidate().getName()));
        
        Callback<TableColumn<Vote, Void>, TableCell<Vote, Void>> cellFactory = param -> new TableCell<>() {
            private final Button btn = new Button("Pokaż");

            {
                btn.getStyleClass().add("guzikl");
                btn.setOnAction(event -> {
                    Vote vote = getTableView().getItems().get(getIndex());
                    handleViewElection(vote.getElection());
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(btn);
                }
            }
        };
        actionColumn.setCellFactory(cellFactory);
    }
    
    private void handleViewElection(Election election) {
        ViewController controller = (ViewController) AppProvider.getInstance().getMainController()
                .loadViewWithController("user/elections/view");
        
        if (controller != null) {
            controller.setElection(election);
        }
    }
    
    private void loadVotes() {
        VoteService voteService = AppProvider.getInstance().getVoteService();
        votesTable.setItems(FXCollections.observableArrayList(voteService.getVotesByUser(currentUser)));
    }

    @FXML
    private void handleEditToggle() {
        isEditing = !isEditing;
        setFieldsEditable(isEditing);
        
        if (isEditing) {
            editButton.setVisible(false);
            editButton.setManaged(false);
            saveButton.setVisible(true);
            saveButton.setManaged(true);
        } else {
            // Cancel/Reset logic could go here
            initialize(); // Reset fields to current user data
            editButton.setVisible(true);
            editButton.setManaged(true);
            saveButton.setVisible(false);
            saveButton.setManaged(false);
        }
    }
    
    private void setFieldsEditable(boolean editable) {
        fullNameField.setDisable(!editable);
        emailField.setDisable(!editable);
        peselField.setDisable(!editable);
        passwordField.setDisable(!editable);
    }

    @FXML
    private void handleSave() {
        String fullName = fullNameField.getText();
        String email = emailField.getText();
        String pesel = peselField.getText();
        String password = passwordField.getText();

        if (fullName.isEmpty() || email.isEmpty() || pesel.isEmpty()) {
            showAlert("Błąd", "Pola Imię i Nazwisko, Email oraz PESEL są wymagane.");
            return;
        }

        try {
            UserService userService = AppProvider.getInstance().getUserService();
            userService.updateUser(currentUser.getId(), fullName, email, password, pesel);
            
            showAlert("Sukces", "Profil został zaktualizowany.");
            AppProvider.getInstance().getMainController().refreshMenu();
            
            // Exit edit mode
            isEditing = false;
            handleEditToggle(); // Toggles buttons back
            
        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Błąd", "Wystąpił błąd podczas zapisu: " + e.getMessage());
        }
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
