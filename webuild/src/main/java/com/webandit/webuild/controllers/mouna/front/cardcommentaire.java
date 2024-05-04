package com.webandit.webuild.controllers.mouna.front;

import com.webandit.webuild.models.Commentaire;
import com.webandit.webuild.models.Post;
import com.webandit.webuild.services.CommentaireService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;



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

    }

    public void setData(Commentaire commentaire) {
    }
}
