package com.webandit.webuild.controllers.back;

import com.webandit.webuild.models.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;
import com.webandit.webuild.services.serviceUtilisateur;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import org.json.JSONException;

public class DashboardController implements Initializable {

    @FXML
    private Button addUserButton;

    @FXML
    private TableColumn<User, String> adresseColumn;

    @FXML
    private TableColumn<User, String> emailColumn;

    @FXML
    private TableColumn<User, String> nomColumn;

    @FXML
    private TableColumn<User, String> prénomColumn;

    @FXML
    private TableColumn<User, Integer> telephoneColumn;
    @FXML
    private TableView<User> userTable;
    /* @FXML
     private TableColumn<Utilisateur, Boolean> statusColumn;*/
    @FXML
    private TableColumn<User, Void> actionColumn;


    serviceUtilisateur sp = new serviceUtilisateur();

    @FXML
    void addUser(MouseEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/back/AddUser.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage stage = (Stage) addUserButton.getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    private void loadData() {
        nomColumn.setCellValueFactory(new PropertyValueFactory<>("nom"));
        prénomColumn.setCellValueFactory(new PropertyValueFactory<>("prenom"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        adresseColumn.setCellValueFactory(new PropertyValueFactory<>("address"));
        telephoneColumn.setCellValueFactory(new PropertyValueFactory<>("telephone"));
        //statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));

        try {
            List<User> users = sp.selectAll();
            ObservableList<User> userList = FXCollections.observableList(users);
            userTable.setItems(userList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadData();

        actionColumn.setCellFactory(param -> new TableCell<>() {
            //    private final Button updateButton = new Button("Update");
            private final Button updateButton = new Button("Update");




            {
                updateButton.setStyle("-fx-background-color: #BA0607;");

                updateButton.setOnAction(event -> {
                    User utilisateur = getTableView().getItems().get(getIndex());
                    String userEmail = utilisateur.getEmail();
                    System.out.println("Email: " + userEmail); // Print out the email
                    try {
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/back/UpdateUser.fxml"));
                        Parent root = loader.load();
                        UpdateUser userUpdate = loader.getController();
                        userUpdate.setEmail(userEmail);
                        userUpdate.initialize();
                        Scene scene = new Scene(root);
                        Stage stage = (Stage) updateButton.getScene().getWindow();
                        stage.setScene(scene);
                        stage.show();


                    } catch (IOException e) {
                                /*Alert alert = new Alert(Alert.AlertType.ERROR);
                                alert.setTitle("Erreur de saisie");
                                alert.setContentText("Vous avez une erreur dans la saisie de vos données!");
                                alert.show();*/
                        System.out.println(e);
                    }

                });
            }
            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    HBox buttonBox = new HBox(5);
                    buttonBox.getChildren().addAll(updateButton/*, deleteButton*/);
                    setGraphic(buttonBox);
                }
            }
        });
    }
}



