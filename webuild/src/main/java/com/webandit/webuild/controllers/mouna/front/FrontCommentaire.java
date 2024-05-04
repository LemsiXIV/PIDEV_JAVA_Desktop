package com.webandit.webuild.controllers.mouna.front;

import com.webandit.webuild.models.Commentaire;

import com.webandit.webuild.models.Post;
import com.webandit.webuild.services.CommentaireService;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;

import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class FrontCommentaire {
    public ScrollPane scroll;
    private Stage stage;

    @FXML
    private GridPane grid;

    @FXML
    private Label contenuCommentaireLabel;

    @FXML
    private Label dateCreationLabel;

    @FXML
    private Label nomCommentateurLabel;

    @FXML
    private TextField searchField;

    public void setStage(Stage stage) {
        this.stage = stage;


    }
    private Commentaire commentaire;

    private final CommentaireService cs = new CommentaireService();
    private int postId;

    @FXML
    void initialize() {
        try {

            actualise();
            loadCommentairesByPostId(postId);
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
    private void loadCommentairesByPostId(int postId) throws SQLException {
        grid.getChildren().clear(); // Effacer les éléments actuels de la grille

        List<Commentaire> commentaires = cs.getCommentairesByPostId(postId);
        if (commentaires.isEmpty()) {
            System.out.println("Aucun commentaire trouvé pour le post avec l'ID : " + postId);
            return;
        }

        // Ajouter les commentaires à la grille...
    }
    private void actualise() throws SQLException {
        // Effacer les éléments actuels de la grille
        grid.getChildren().clear();

        List<Commentaire> commentaires = cs.selectAll();
        if (commentaires.isEmpty()) {
            System.out.println("post est vide.");
            return;
        }

        int column = 0;
        int row = 0;

        grid.setVgap(120);

        for (Commentaire commentaire : commentaires) {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/commentfront.fxml"));
                AnchorPane anchorPane = fxmlLoader.load();

                cardcommentaire c = fxmlLoader.getController();
                if (c == null) {
                    System.out.println("Le contrôleur de l'élément n'a pas été initialisé.");
                    continue;
                }

                c.getData(commentaire);
                c.setId(commentaire.getId());



                grid.add(anchorPane, column++, row);
                GridPane.setMargin(anchorPane, new Insets(10));
                if (column == 1) {
                    column = 0;
                    row++;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    public void setData(List<Commentaire> commentaires, Post post) {
        // Effacer les éléments actuels de la grille
        grid.getChildren().clear();

        // Afficher les commentaires du post dans la grille
        int row = 0;
        for (Commentaire commentaire : commentaires) {
            // Créer et ajouter un élément d'interface utilisateur pour chaque commentaire dans la grille
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/commentfront.fxml"));
            try {
                AnchorPane anchorPane = fxmlLoader.load();
                grid.add(anchorPane, 0, row++);
                // Passer le commentaire à chaque élément d'interface utilisateur de commentaire
                cardcommentaire controller = fxmlLoader.getController();
                controller.setData(commentaire);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    private void actualiseWithSearch(String searchTerm) throws SQLException {
        grid.getChildren().clear();

        List<Commentaire> commentaires = cs.rechercherCommentaires(searchTerm);
        if (commentaires.isEmpty()) {
            System.out.println("Aucun post trouvé pour le terme de recherche : " + searchTerm);
            return;
        }

        int column = 0;
        int row = 2;
        grid.setHgap(60);

        for (Commentaire commentaire : commentaires) {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/commentfront.fxml"));
                AnchorPane anchorPane = fxmlLoader.load();

                cardcommentaire c = fxmlLoader.getController();
                if (c == null) {
                    System.out.println("Le contrôleur de l'élément n'a pas été initialisé.");
                    continue;
                }

                c.getData(commentaire);



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




    public void addcommentairefront(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/zydcomment.fxml"));
        Parent root1 = (Parent) fxmlLoader.load();
        Stage stage = new Stage();
        stage.setScene(new Scene(root1));
        stage.show();
    }
}
