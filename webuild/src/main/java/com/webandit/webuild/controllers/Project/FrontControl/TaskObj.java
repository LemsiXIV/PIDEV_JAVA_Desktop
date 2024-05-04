package com.webandit.webuild.controllers.Project.FrontControl;

import com.webandit.webuild.models.Chantier;
import com.webandit.webuild.models.Tasks;
import com.webandit.webuild.services.ServiceTasks;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class TaskObj implements Initializable {

    ServiceTasks cs = new ServiceTasks();
    @FXML
    private Label Project_rem1;

    @FXML
    private TextField TASK_Priority;

    @FXML
    private TextField TASK_Status;

    @FXML
    private Label TASK_date;

    @FXML
    private TextField TASK_discription;

    @FXML
    private TextField TASK_name;



    @FXML
    private ImageView img_user;


    int id ;
    public void setId(int id) {
        this.id=id;

    }
    private task_front frontController; // Reference to the task_front controller
    public void setFrontController(task_front frontController) {
        this.frontController = frontController;
    }
    @FXML
    void DeleteTASK(ActionEvent event) {
        if (id != 0) {
            try {
                System.out.println(id + " ahawaaaaa");
                cs.deleteOne(id); // Accessing ServiceChantier methods via 'ps' instance
                if (frontController != null) {
                    frontController.acctualise(); // Call acctualise() method of task_front
                }
            } catch (SQLException e) {
                // Handle the exception gracefully
                Notifications notification = Notifications.create()
                        .title("Delete Task")
                        .text("Error in deleting the task: " + e.getMessage())
                        .graphic(null)
                        .hideAfter(Duration.seconds(5))
                        .position(Pos.BOTTOM_RIGHT);
                notification.show();
            }
        } else {
            Notifications notification = Notifications.create()
                    .title("Delete Task")
                    .text("No task selected.")
                    .graphic(null)
                    .hideAfter(Duration.seconds(5))
                    .position(Pos.BOTTOM_RIGHT);
            notification.show();
        }
    }

    @FXML
    void updateTASK(ActionEvent event) {


        // Collect the updated data from the text fields
        String updatedName = TASK_name.getText();
        String updatedDescription = TASK_discription.getText();
        String updatedPriority = TASK_Priority.getText();
        int updatedStatus = Integer.parseInt(TASK_Status.getText());

        // Update the data via the ServiceTasks class
        try {
            Tasks tasks = new Tasks(id,updatedName, updatedPriority, updatedStatus , updatedDescription);
            cs.updatefront(tasks);
            // If the update is successful, you may want to disable editing and update the UI accordingly
            TASK_name.setEditable(false);
            TASK_discription.setEditable(false);
            TASK_Priority.setEditable(false);
            TASK_Status.setEditable(false);
            Notifications notification = Notifications.create()
                    .title("Update Task")
                    .text(" updating the task Successfully  " )
                    .graphic(null)
                    .hideAfter(Duration.seconds(5))
                    .position(Pos.CENTER);
            notification.show();
        } catch (SQLException e) {
            // Handle the exception gracefully
            Notifications notification = Notifications.create()
                    .title("Update Task")
                    .text("Error in updating the task: " + e.getMessage())
                    .graphic(null)
                    .hideAfter(Duration.seconds(5))
                    .position(Pos.BOTTOM_RIGHT);
            notification.show();
        }


    }

    @FXML
    void unlock(ActionEvent event) {
        // Enable editing for the text fields
        TASK_name.setEditable(true);
        TASK_discription.setEditable(true);
        TASK_Priority.setEditable(true);
        TASK_Status.setEditable(true);
    }

    public void setData(Tasks tasks) {
        TASK_name.setText(tasks.getName());
        TASK_discription.setText(tasks.getDescription());
        TASK_Priority.setText(String.valueOf(tasks.getPriority()));
        TASK_Status.setText(String.valueOf(tasks.getStatus()));
        TASK_date.setText(String.valueOf(tasks.getDue()));
    }



    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

}

