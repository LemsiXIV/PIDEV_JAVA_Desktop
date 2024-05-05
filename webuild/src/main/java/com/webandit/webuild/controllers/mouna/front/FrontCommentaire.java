
package com.webandit.webuild.controllers.mouna.front;

import com.sun.mail.imap.protocol.INTERNALDATE;
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
    private int postId;
    public ScrollPane scroll;
    private Stage stage;

    @FXML
    private GridPane grid;

    @FXML
    private TextField searchField;


    List<Commentaire> id_post;
    public void setStage(Stage stage) {
        this.stage = stage;

    }
    private Commentaire commentaire;

    private final CommentaireService cs = new CommentaireService();
    public void setData(int id) {
        this.postId = id;
        try {
            actualise();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    @FXML
    void initialize() {
        System.out.println(postId+"\t ahwaaaaaa22222");
        //  actualise();

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
    }

    public void actualise() throws SQLException {
        // Effacer les éléments actuels de la grille
        grid.getChildren().clear();

        List<Commentaire> commentaires = cs.selectAllv2(postId);
        System.out.println(postId+"\t ahwaaaaaa");
        if (commentaires.isEmpty()) {
            System.out.println("post est vide.");
            return;
        }

        int column = 0;
        int row = 3;

        grid.setVgap(50);

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
                c.setController(this);


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

    private void actualiseWithSearch(String searchTerm) throws SQLException {
        grid.getChildren().clear();

        List<Commentaire> commentaires = cs.rechercherCommentaires(searchTerm);
        System.out.println("searchraniala trabhek comment " + searchTerm+"commentaires."+commentaires);
        if (commentaires.isEmpty()) {
            System.out.println("Aucun post trouvé pour le terme de recherche : " + searchTerm);
            return;
        }

        int column = 0;
        int row = 3;
        grid.setHgap(50);

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




    public void addcommentairefront(ActionEvent actionEvent) throws IOException, SQLException {
      FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/zydcomment.fxml"));
        Parent root1 = (Parent) fxmlLoader.load();
         add_commentaire controller = fxmlLoader.getController();
        controller.setControllerAct(this);
        Stage stage = new Stage();
        stage.setScene(new Scene(root1));
        stage.show();

    }



}
