package com.webandit.webuild.controllers.Project.FrontControl;

import com.webandit.webuild.models.Chantier;
import com.webandit.webuild.services.ServiceChantier;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class project_front implements Initializable {
    @FXML
    private VBox Project_Ligne_Info;

    ServiceChantier cs = new ServiceChantier();

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        List<Chantier> chantiers = null;
        try {
            chantiers = cs.selectAll();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        for (int i=0; i< chantiers.size();i++){
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("/fxml/Project/front/object.fxml"));

            try {
                HBox hBox = fxmlLoader.load();
                objject obj = fxmlLoader.getController();
                obj.setData(chantiers.get(i));
                obj.setId(chantiers.get(i).getId());
                Project_Ligne_Info.getChildren().add(hBox);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        }

    }

}
