package com.webandit.webuild.controllers.Project.FrontControl;

import com.webandit.webuild.models.Chantier;
import com.webandit.webuild.models.Tasks;
import com.webandit.webuild.services.ServiceTasks;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class task_front implements Initializable {

    private Stage stage;

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @FXML
    private VBox Project_Ligne_Info;

    @FXML
    private StackPane contentArea;
    @FXML
    private javafx.scene.chart.PieChart PieChart;

    ServiceTasks  cs = new ServiceTasks();

    int id ;
    public void setId(int id) {
        this.id=id;

    }

    public void Add_Task(ActionEvent event) throws IOException {
       // FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/Project/Back/add_tasks.fxml"));
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/Project/front/add_tasks.fxml"));
        Parent root1 = (Parent) fxmlLoader.load();

        // Get the controller
        add_tasks controller = fxmlLoader.getController();
        controller.setFrontController(this); // Set reference to
        // Set the stage
        Stage stage = new Stage();
        controller.setStage(stage); // Pass the stage to the controller
        stage.setScene(new Scene(root1));
        stage.show();

    }
    public void acctualise () throws SQLException {
        // Effacer les éléments actuels de la grille
        Project_Ligne_Info.getChildren().clear();
        System.out.println("ahaya."+id);
        List<Tasks> tasksList = cs.selectAll();
        if (tasksList.isEmpty()) {
            System.out.println("List of Tasks Empty."+id);
            return;
        }
        for (int i=0; i< tasksList.size();i++){
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("/fxml/Project/front/object_task.fxml"));

            try {
                HBox hBox = fxmlLoader.load();
                TaskObj obj = fxmlLoader.getController();

                obj.setFrontController(this); // Set reference to task_front controller

                obj.setData(tasksList.get(i));
                obj.setId(tasksList.get(i).getId());
                Project_Ligne_Info.getChildren().add(hBox);


            } catch (IOException e) {
                throw new RuntimeException(e);
            }

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
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            acctualise();
            updatePieChartData();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    private void updatePieChartData() {
        try {
            // Step 1: Retrieve the list of tasks
            List<Tasks> tasksList = cs.selectAll();

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

        }
    }

}
