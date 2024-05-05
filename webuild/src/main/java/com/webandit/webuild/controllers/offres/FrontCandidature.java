package com.webandit.webuild.controllers.offres;

import com.webandit.webuild.models.Candidature;
import com.webandit.webuild.services.ServiceCandidature;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class FrontCandidature implements Initializable {
    @FXML
    private VBox Candidature_Ligne_Info;
    @FXML
    public StackPane contentArea;
    ServiceCandidature cs = new ServiceCandidature();

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        List<Candidature> condidature = null;
        try {
            condidature = cs.selectAll();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        for (int i=0; i< condidature.size();i++){
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("/fxml/CandidatureRow.fxml"));

            try {
                HBox hBox = fxmlLoader.load();
                CandidatureRow obj = fxmlLoader.getController();
                obj.setFrontController(this); // Set reference to
                obj.setArea(contentArea);
                obj.setData(condidature.get(i));
                obj.setId(condidature.get(i).getId());
                Candidature_Ligne_Info.getChildren().add(hBox);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        }

    }



    public void Refrech_page(ActionEvent event) throws IOException {
        refreche();

    }
    public void refreche() throws IOException {
        Parent fxml = FXMLLoader.load(getClass().getResource("/fxml/FrontCandidature.fxml"));
        contentArea.getChildren().removeAll();
        contentArea.getChildren().setAll(fxml);

    }
}