package com.webandit.webuild.controllers.mouna.front;

import com.webandit.webuild.models.Commentaire;
import com.webandit.webuild.models.Post;
import com.webandit.webuild.services.CommentaireService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;


public class cardcommentaire {
    CommentaireService cs = new CommentaireService();

    @FXML
    private Label contenuCommentaireLabel;

    @FXML
    private Label dateCreationLabel;

    @FXML
    private Label nomCommentateurLabel;
    private Commentaire commentaire;
    public void getData(Commentaire commentaire) {
        this.commentaire = commentaire;
        contenuCommentaireLabel.setText(commentaire.getContenu());

        nomCommentateurLabel.setText(commentaire.getNom());



    }

    int id ;
    public void setId(int id) {
        this.id=id;

    }

    public void deletecomment(ActionEvent actionEvent) {
        if (id != 0) {
            try {
                System.out.println(id+"ahawaaaaa");
                cs.deleteOne(id); // Accessing ServiceChantier methods via 'ps' instance
                // Refresh the table after deleting a chantier
            } catch (SQLException e) {

                throw new RuntimeException(e);
            }
        }

    }

    public void setData(Commentaire commentaire) {
        this.commentaire = commentaire;
    }

    public void onButtonClickCommentFront(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/Editcommentshiha.fxml"));
        Parent root1 = (Parent) fxmlLoader.load();
        editcommentshyh editControllers = fxmlLoader.getController();

        // Passez le post sélectionné à la méthode pour charger les données du post à modifier
        editControllers.loadCommentToUpdate(commentaire);
        Stage stage = new Stage();
        stage.setScene(new Scene(root1));
        stage.show();
    }
}
