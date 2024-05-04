package com.webandit.webuild.controllers.Assurance.front;

import com.webandit.webuild.controllers.Assurance.front.CardDem;
import com.webandit.webuild.models.Demande;
import com.webandit.webuild.services.ServiceDemande;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class ShowDemandeFront {



    @FXML
    private GridPane grid;

    @FXML
    private ScrollPane scroll;

    private final ServiceDemande serviceDemande = new ServiceDemande();
    private String selectedAssuranceName;

    @FXML
    void initialize() {
        try {
            actualise();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void actualise() throws SQLException {
        // Clear current elements from the grid
        grid.getChildren().clear();

        List<Demande> demandes = serviceDemande.selectAll();
        if (demandes.isEmpty()) {
            System.out.println("the demande list is empty.");
            return;
        }

        int column = 0;
        int row = 3;

        grid.setHgap(50);

        for (Demande demande : demandes) {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/CardDem.fxml"));
                AnchorPane anchorPane = fxmlLoader.load();

                 CardDem controller = fxmlLoader.getController();
                if (controller == null) {
                    System.out.println("The controller for the item was not initialized.");
                    continue;
                }

                controller.setData(demande);

                // Add event handler to handle card click


                grid.add(anchorPane, column++, row);
                GridPane.setMargin(anchorPane, new Insets(10));
                if (column == 3) {
                    column = 0;
                    row++;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }




}
