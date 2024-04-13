package com.webandit.webuild.controllers;

import com.webandit.webuild.models.Chantier;
import com.webandit.webuild.services.ServiceTasks;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class Tasks {
    ServiceTasks cs = new ServiceTasks();

    @FXML
    private TableView<Tasks> ts_view;

    @FXML
    private TableColumn<Tasks, Chantier> ts_view_chnt;

    @FXML
    private TableColumn<Tasks, String> ts_view_desc;

    @FXML
    private TableColumn<Tasks, java.util.Date> ts_view_due;

    @FXML
    private TableColumn<Tasks, String> ts_view_name;

    @FXML
    private TableColumn<Tasks, String> ts_view_prio;

    @FXML
    private TableColumn<Tasks, Integer> ts_view_stat;

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.show();
    }

    public void add_Task(ActionEvent event) throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/add_tasks.fxml"));
        Parent root1 = (Parent) fxmlLoader.load();
        Stage stage = new Stage();
        stage.setScene(new Scene(root1));
        stage.show();
    }

    public void afficherTasks() {
        try {
            List<com.webandit.webuild.models.Tasks> tasks = cs.selectAll();
            ObservableList<Tasks> observableTasks = FXCollections.observableArrayList();
            ts_view.setItems(observableTasks);
        } catch (SQLException e) {
            showAlert("Erreur", "Erreur lors de la récupération des chantiers!");
        }
    }

    @FXML
    void initialize() {
        ts_view_chnt.setCellValueFactory(new PropertyValueFactory<>("id_chantier"));

        ts_view_name.setCellValueFactory(new PropertyValueFactory<>("name"));
        ts_view_prio.setCellValueFactory(new PropertyValueFactory<>("Priority"));
        ts_view_stat.setCellValueFactory(new PropertyValueFactory<>("Status"));
        ts_view_desc.setCellValueFactory(new PropertyValueFactory<>("Description"));
        ts_view_due.setCellValueFactory(new PropertyValueFactory<>("Due"));
        afficherTasks(); //refrech el tableview
    }
}
