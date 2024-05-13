package com.webandit.webuild.controllers.Project.FrontControl;

import com.webandit.webuild.controllers.Project.BackControl.add_tasks;
import com.webandit.webuild.controllers.SessionManagement;
import com.webandit.webuild.models.Chantier;
import com.webandit.webuild.services.ServiceChantier;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class project_front implements Initializable {
    @FXML
    private VBox Project_Ligne_Info;
    @FXML
    public StackPane contentArea;
    ServiceChantier cs = new ServiceChantier();
    int iduser = SessionManagement.getInstance().getId();
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        List<Chantier> chantiers = null;
        try {
            chantiers = cs.selectAllByUser(iduser);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        for (int i=0; i< chantiers.size();i++){
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("/fxml/Project/front/object.fxml"));

            try {
                HBox hBox = fxmlLoader.load();
                objject obj = fxmlLoader.getController();
                obj.setFrontController(this); // Set reference to
                obj.setArea(contentArea);
                obj.setData(chantiers.get(i));
                obj.setId(chantiers.get(i).getId());
                Project_Ligne_Info.getChildren().add(hBox);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        }

    }

    public void Add_Project(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/Project/front/add_project.fxml"));
        Parent root1 = (Parent) fxmlLoader.load();

        // Get the controller
        add_project controller = fxmlLoader.getController();


        // Set the stage
        Stage stage = new Stage();
        controller.setStage(stage); // Pass the stage to the controller
        stage.setScene(new Scene(root1));
        stage.show();
    }

    public void Refrech_page(ActionEvent event) throws IOException {
        refreche();

    }
    public void refreche() throws IOException {
        Parent fxml = FXMLLoader.load(getClass().getResource("/fxml/Project/front/front_project.fxml"));
        contentArea.getChildren().removeAll();
        contentArea.getChildren().setAll(fxml);

    }
}
