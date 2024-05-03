package com.webandit.webuild.controllers.Project.FrontControl;

import com.webandit.webuild.models.Chantier;
import com.webandit.webuild.services.ServiceChantier;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class objject implements Initializable {

    @FXML
    private Label Project_date;

    @FXML
    private Label Project_discription;

    @FXML
    private Label Project_name;

    @FXML
    private Label Project_rem;

    @FXML
    private ImageView img_user;
   private StackPane area;

    ServiceChantier cs = new ServiceChantier();

    int id ;
    public void setId(int id) {
        this.id=id;

    }
    private project_front frontController; // Reference to the task_front controller
    public void setFrontController(project_front frontController) {
        this.frontController = frontController;
    }

    public void setArea(StackPane area) {
        this.area = area;
    }
    public void setData(Chantier chantier){

       // Image imageprofile = new Image(String.valueOf(getClass().getResource("image/admin-22.jpg")));
      //  img_user.setImage(imageprofile);
        Project_name.setText(chantier.getNom());
        Project_discription.setText(chantier.getDescription());
        Project_rem.setText(String.valueOf(chantier.getRemuneration()));
        Project_date.setText(String.valueOf(chantier.getDate()));

    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void Manage(ActionEvent event) throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/Project/front/Task_front.fxml"));

        try {
            Parent fxml = fxmlLoader.load();
            frontController.contentArea.getChildren().removeAll();
            frontController.contentArea.getChildren().setAll(fxml);

            // Accessing the controller instance after loading the fxml
            task_front taskFrontController = fxmlLoader.getController();
            taskFrontController.setId(id);
            System.out.println(id + "\t ahwaaaaaaa");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }


    public void update(ActionEvent event) {

    }



    public void Delete(ActionEvent event ) {

        if (id != 0) {
            try {
                System.out.println(id+"ahawaaaaa");
                cs.deleteOne(id); // Accessing ServiceChantier methods via 'ps' instance
                if (frontController != null) {
                    frontController.refreche(); // Call acctualise() method of task_front
                }// Refresh the table after deleting a chantier
            } catch (SQLException e) {

                Notifications notification = Notifications.create()
                        .title("Delete Project")
                        .text("Error in the SQL for the delete ")
                        .graphic(null) // You can set a graphic if needed
                        .hideAfter(Duration.seconds(5)) // Set how long the notification will be shown
                        .position(Pos.BOTTOM_RIGHT);
                notification.show();


                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {

            Notifications notification = Notifications.create()
                    .title("Delete Project")
                    .text("No data is recorded ")
                    .graphic(null) // You can set a graphic if needed
                    .hideAfter(Duration.seconds(5)) // Set how long the notification will be shown
                    .position(Pos.BOTTOM_RIGHT);
            notification.show();
        }

    }

}
