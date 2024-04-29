package com.webandit.webuild.controllers.Project.BackControl;

import com.webandit.webuild.models.Tasks;
import com.webandit.webuild.services.ServiceTasks;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Shape;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.util.converter.IntegerStringConverter;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javafx.scene.chart.PieChart;

import javafx.util.converter.DateStringConverter;
import org.controlsfx.control.Notifications;

public class TasksControlleur {
    ServiceTasks taskSer = new ServiceTasks();

    @FXML
    private PieChart PieChart;


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
    private void showAlertSuc(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.show();
    }

    private void showAlertWar(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.show();
    }
    public void add_Task(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/Project/Back/add_tasks.fxml"));
        Parent root1 = (Parent) fxmlLoader.load();

        // Get the controller
        add_tasks controller = fxmlLoader.getController();

        // Set the stage
        Stage stage = new Stage();
        controller.setStage(stage); // Pass the stage to the controller
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
    private void updateTask(Tasks task) {
        try {
            taskSer.updateOne(task); // Update the modified task using the service
            showAlertSuc("Success ", "Task updated!");
            affichertasks(); // Refresh the table after updating the task
        } catch (SQLException e) {
            showAlert("Error", "Error updating the task: " + e.getMessage());
            e.printStackTrace(); // Print the stack trace for debugging
        }
    }
    public List<Integer> countTasksByStatus(List<Tasks> tasksList) {
        List<Integer> taskCounts = new ArrayList<>(3); // Initialize with three elements for each status

        int doneCount = 0;
        int inProgressCount = 0;
        int toDoCount = 0;

        for (Tasks task : tasksList) {
            int status = task.getStatus();
            if (status == 2) {
                doneCount++;
            } else if (status == 1) {
                inProgressCount++;
            } else if (status == 0) {
                toDoCount++;
            }
        }

        taskCounts.add(doneCount);
        taskCounts.add(inProgressCount);
        taskCounts.add(toDoCount);

        return taskCounts;
    }


    @FXML
    void initialize() throws SQLException {


        // Column bindings
        ts_view_chnt.setCellValueFactory(new PropertyValueFactory<>("nomchantier"));
        ts_view_name.setCellValueFactory(new PropertyValueFactory<>("name"));
        ts_view_prio.setCellValueFactory(new PropertyValueFactory<>("priority"));
        ts_view_stat.setCellValueFactory(new PropertyValueFactory<>("status"));
        ts_view_desc.setCellValueFactory(new PropertyValueFactory<>("description"));
        ts_view_due.setCellValueFactory(new PropertyValueFactory<>("due"));

        // Enable editing for each cell
        ts_view_chnt.setCellFactory(TextFieldTableCell.forTableColumn());
        ts_view_name.setCellFactory(TextFieldTableCell.forTableColumn());
        ts_view_prio.setCellFactory(TextFieldTableCell.forTableColumn());
        ts_view_stat.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        ts_view_desc.setCellFactory(TextFieldTableCell.forTableColumn());
        ts_view_due.setCellFactory(TextFieldTableCell.forTableColumn(new DateStringConverter()));

        // Handle edit commit events
        ts_view_name.setOnEditCommit(event -> handleEditCommit(event, "name"));
        ts_view_prio.setOnEditCommit(event -> handleEditCommit(event, "priority"));
        ts_view_stat.setOnEditCommit(event -> handleEditCommit(event, "status"));
        ts_view_desc.setOnEditCommit(event -> handleEditCommit(event, "description"));
        ts_view_due.setOnEditCommit(event -> handleEditCommit(event, "due"));

        // Update button action
        ts_view_action.setCellFactory(param -> new TableCell<>() {
        //    private final Button updateButton = new Button("Update");
            private final Button deleteButton = new Button("Delete");

            {


                deleteButton.setOnAction(event -> {
                    Tasks task = getTableView().getItems().get(getIndex());
                    int var = task.getId();
                    if (var != 0 ) {
                        try {
                            taskSer.deleteOne(var);
                            affichertasks();
                            Notifications notification = Notifications.create()
                                    .title("DB_Updated")
                                    .text("TASK DELETED SUCCUSFULY")
                                    .graphic(null) // You can set a graphic if needed
                                    .hideAfter(Duration.seconds(5))// Set how long the notification will be shown
                                    .darkStyle()
                                    .position(Pos.BOTTOM_RIGHT);

                            notification.show();
                            showAlertSuc("Success ", "Tache supprimer !");
                        } catch (SQLException e) {
                            showAlert("Erreur", "Erreur lors de la suppression de la tache !");
                        }
                    } else {
                        showAlertWar("Aucune sélection", "Aucun tache sélectionné");
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
                    buttonBox.getChildren().addAll(/*updateButton, */deleteButton);
                    setGraphic(buttonBox);
                }
            }
        });

        // Refresh the TableView
        affichertasks();
        //PieChart section

        updatePieChartData();
    }

    private void updatePieChartData() {
        try {
            // Step 1: Retrieve the list of tasks
            List<Tasks> tasksList = taskSer.selectAll();

            // Step 2: Get the counts for tasks in different statuses
            List<Integer> taskCounts = countTasksByStatus(tasksList);
            // Calculate total tasks count
            int totalTasks = taskCounts.stream().mapToInt(Integer::intValue).sum(); // Declare and initialize totalTasks
            // Step 3: Update PieChart data
            ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList(
                    new PieChart.Data("Todo", taskCounts.get(2)), // Index 2 for Todo count
                    new PieChart.Data("In Progress", taskCounts.get(1)), // Index 1 for In Progress count
                    new PieChart.Data("Complete", taskCounts.get(0)) // Index 0 for Complete count
            );

            PieChart.setData(pieChartData);
            PieChart.setStartAngle(90);

        } catch (SQLException e) {
            e.printStackTrace();
            showAlert("Error", "Error updating PieChart data: " + e.getMessage());
        }
    }

    // Method to handle edit commit events
    private void handleEditCommit(TableColumn.CellEditEvent<Tasks, ?> event, String propertyName) {
        Tasks task = event.getRowValue();
        if (propertyName.equals("due")) {
            task.setDue(java.sql.Date.valueOf(event.getNewValue().toString()));
        } else {
            switch (propertyName) {
                case "name":
                    task.setName(event.getNewValue().toString());
                    break;
                case "priority":
                    task.setPriority(event.getNewValue().toString());
                    break;
                case "status":
                    task.setStatus(Integer.parseInt(event.getNewValue().toString()));
                    break;
                case "description":
                    task.setDescription(event.getNewValue().toString());
                    break;
            }
        }

        updateTask(task);
    }

    public void refrech(ActionEvent event) {
        affichertasks(); // Refresh the TableView
    }








}
