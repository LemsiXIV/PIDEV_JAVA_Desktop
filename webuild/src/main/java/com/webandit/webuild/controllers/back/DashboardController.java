package com.webandit.webuild.controllers.back;

import com.webandit.webuild.models.Utilisateur;
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

public class DashboardController implements Initializable {

    @FXML
    private Button addUserButton;

    @FXML
    private TableColumn<Utilisateur, String> adresseColumn;

    @FXML
    private TableColumn<Utilisateur, String> emailColumn;

    @FXML
    private TableColumn<Utilisateur, String> nomColumn;

    @FXML
    private TableColumn<Utilisateur, String> prénomColumn;

    @FXML
    private TableColumn<Utilisateur, Integer> telephoneColumn;
    @FXML
    private TableView<Utilisateur> userTable;
    @FXML
    private TableColumn<Utilisateur, Boolean> statusColumn;
    @FXML
    private TableColumn<Utilisateur, Void> actionColumn;


    serviceUtilisateur sp = new serviceUtilisateur();

    @FXML
    void addUser(MouseEvent event) {

    }

    private void loadData() {
        nomColumn.setCellValueFactory(new PropertyValueFactory<>("nom"));
        prénomColumn.setCellValueFactory(new PropertyValueFactory<>("prenom"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        adresseColumn.setCellValueFactory(new PropertyValueFactory<>("adresse"));
        telephoneColumn.setCellValueFactory(new PropertyValueFactory<>("telephone"));

        try {
            List<Utilisateur> users = sp.selectAll();
            ObservableList<Utilisateur> userList = FXCollections.observableList(users);
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

                        updateButton.setOnAction(event -> {
                            Utilisateur utilisateur = getTableView().getItems().get(getIndex());
                            String userEmail = utilisateur.getEmail();
                            try {
                                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/back/UpdateUser.fxml"));
                                Parent root = loader.load();
                                UpdateUser userUpdate = loader.getController();
                                userUpdate.setEmail(userEmail);
                                UpdateUser userUpdate1 = loader.getController();
                                userUpdate1.setEmail(userEmail);
                                Scene scene = new Scene(root);
                                Stage stage = (Stage) updateButton.getScene().getWindow();
                                stage.setScene(scene);
                                stage.show();
                            } catch (IOException e) {
                                Alert alert = new Alert(Alert.AlertType.ERROR);
                                alert.setTitle("Erreur de saisie");
                                alert.setContentText("Vous avez une erreur dans la saisie de vos données!");
                                alert.show();
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



