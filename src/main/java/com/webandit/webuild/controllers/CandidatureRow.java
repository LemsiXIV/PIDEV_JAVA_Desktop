package com.webandit.webuild.controllers;

import com.webandit.webuild.models.Candidature;
import com.webandit.webuild.services.ServiceCandidature;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import javafx.fxml.Initializable;

import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class CandidatureRow implements Initializable {

    @FXML
    private TextField Cv;

    @FXML
    private TextField Description;

    @FXML
    private TextField Email;

    @FXML
    private TextField Offre;

    @FXML
    private ImageView img_user;
    private StackPane area;

    ServiceCandidature cs = new ServiceCandidature();

    int id ;
    public void setId(int id) {
        this.id=id;

    }
    private FrontCandidature frontController; // Reference to the task_front controller
    public void setFrontController(FrontCandidature frontController) {
        this.frontController = frontController;
    }

    public void setArea(StackPane area) {
        this.area = area;
    }
    public void setData(Candidature candidature){

        // Image imageprofile = new Image(String.valueOf(getClass().getResource("image/admin-22.jpg")));
        //  img_user.setImage(imageprofile);
        Offre.setText(candidature.getOffreTitle());
        Description.setText(candidature.getDescription());
        Email.setText(String.valueOf(candidature.getEmail()));
        Cv.setText(String.valueOf(candidature.getCv()));

    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }


    @FXML
    void unlock(ActionEvent event) {
        // Enable editing for the text fields
        Offre.setEditable(false);
        Cv.setEditable(false);
        Email.setEditable(true);
        Description.setEditable(true);

    }

    public void update(ActionEvent event) {
        // Collect the updated data from the text fields

        String updatedDescription = Description.getText();
        String updatedemail = Email.getText();
        String updatedcv = Cv.getText();


        // Update the data via the ServiceTasks class
        try {
            Candidature tasks = new Candidature(id, updatedDescription, updatedemail,updatedcv );
            cs.updatepro(tasks);
            // If the update is successful, you may want to disable editing and update the UI accordingly

            Cv.setEditable(false);
            Description.setEditable(false);
            Email.setEditable(false);



        } catch (SQLException e) {
            // Handle the exception gracefully

          }

    }



    public void Delete(ActionEvent event ) {

        if (id != 0) {
            try {
                System.out.println(id+"ahawaaaaa");
                cs.deleteCan(id); // Accessing ServiceChantier methods via 'ps' instance
                if (frontController != null) {
                    frontController.refreche(); // Call acctualise() method of task_front
                }// Refresh the table after deleting a chantier
            } catch (SQLException e) {




                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {


        }

    }

}