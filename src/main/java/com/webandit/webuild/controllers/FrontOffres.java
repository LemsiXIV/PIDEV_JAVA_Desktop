package com.webandit.webuild.controllers;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import javafx.geometry.Insets;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import com.webandit.webuild.models.Offre;
import com.webandit.webuild.services.ServiceOffre;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import javafx.scene.Scene;
import javafx.stage.Stage;
public class FrontOffres {
    @FXML
    private GridPane grid;

    @FXML
    private ScrollPane scroll;

    @FXML
    private TextField searchField;
    @FXML
    private Button btnMaps;

    private final ServiceOffre sa = new ServiceOffre();

    @FXML
    void initialize() {
        try {
            actualise();
            // Ajouter un écouteur d'événements au champ de recherche
            searchField.textProperty().addListener((observable, oldValue, newValue) -> {
                try {
                    if (newValue.isEmpty()) {
                        // Si le champ de recherche est vide, afficher tous les articles
                        actualise();
                    } else {
                        // Sinon, actualiser la liste des articles en fonction du terme de recherche
                        actualiseWithSearch(newValue);
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            });
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void actualise() throws SQLException {
        // Effacer les éléments actuels de la grille
        grid.getChildren().clear();

        List<Offre> offres = sa.selectAll();
        if (offres.isEmpty()) {
            System.out.println("La liste des articles est vide.");
            return;
        }

        int column = 0;
        int row = 3;

        grid.setHgap(50);

        for (Offre offre : offres) {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/card.fxml"));
                AnchorPane anchorPane = fxmlLoader.load();

                card c = fxmlLoader.getController();
                if (c == null) {
                    System.out.println("Le contrôleur de l'élément n'a pas été initialisé.");
                    continue;
                }

                c.getData(offre);



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

    private void actualiseWithSearch(String searchTerm) throws SQLException {
        grid.getChildren().clear();

        List<Offre> offres = sa.rechercherOffres(searchTerm);
        if (offres.isEmpty()) {
            System.out.println("Aucun offre trouvé pour le terme de recherche : " + searchTerm);
            return;
        }

        int column = 0;
        int row = 3;
        grid.setHgap(50);

        for (Offre offre : offres) {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/card.fxml"));
                AnchorPane anchorPane = fxmlLoader.load();

                card c = fxmlLoader.getController();
                if (c == null) {
                    System.out.println("Le contrôleur de l'élément n'a pas été initialisé.");
                    continue;
                }

                c.getData(offre);



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
    @FXML
    void showMap(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/fxml/map.fxml"));
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("OpenStreetMap");
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    }

