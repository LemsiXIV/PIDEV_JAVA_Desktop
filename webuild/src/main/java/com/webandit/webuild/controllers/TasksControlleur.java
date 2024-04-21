package com.webandit.webuild.controllers;

import com.webandit.webuild.models.Chantier;
import com.webandit.webuild.models.Tasks;
import com.webandit.webuild.services.ServiceTasks;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class TasksControlleur {
    ServiceTasks taskSer = new ServiceTasks();

    @FXML
    private TableView<Tasks> ts_view; // Use the model class here

    @FXML
    private TableColumn<Tasks, String> ts_view_chnt;

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
    @FXML
    private TableColumn<Tasks, Void> ts_view_action;

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


    public void affichertasks(){
        try {
            ts_view.getItems().setAll(taskSer.selectAll());
        }
        catch (SQLException e){
            showAlert("Erreur","erreur lors du load du tabview");
        }
    }


    @FXML
    void initialize() throws SQLException {                         // hethom esmi ely fel models
        ts_view_chnt.setCellValueFactory(new PropertyValueFactory<>("nomchantier"));
        ts_view_name.setCellValueFactory(new PropertyValueFactory<>("name"));
        ts_view_prio.setCellValueFactory(new PropertyValueFactory<>("priority"));
        ts_view_stat.setCellValueFactory(new PropertyValueFactory<>("status"));
        ts_view_desc.setCellValueFactory(new PropertyValueFactory<>("description"));
        ts_view_due.setCellValueFactory(new PropertyValueFactory<>("due"));

        ts_view_action.setCellFactory(param -> new TableCell<>() {
            private final Button updateButton = new Button("Update");
            private final Button deleteButton = new Button("Delete");

            {
                updateButton.setOnAction(event -> {
                    Tasks task = getTableView().getItems().get(getIndex());
                    // Implement update action here
                    System.out.println("Update button clicked for task: " + task.getName());
                });

                deleteButton.setOnAction(event -> {
                    Tasks task = getTableView().getItems().get(getIndex());
                    // Implement delete action here
                    System.out.println("Delete button clicked for task: " + task.getName());
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    HBox buttonBox = new HBox(5);
                    buttonBox.getChildren().addAll(updateButton, deleteButton);
                    setGraphic(buttonBox);
                }
            }
        });


        affichertasks(); // Refresh the TableView
    }




    public void refrech(ActionEvent event) {
        affichertasks(); // Refresh the TableView
    }
}
