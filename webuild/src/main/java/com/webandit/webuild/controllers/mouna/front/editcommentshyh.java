package com.webandit.webuild.controllers.mouna.front;

import com.webandit.webuild.models.Commentaire;
import com.webandit.webuild.services.CommentaireService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.sql.SQLException;

public class editcommentshyh {
    private Commentaire commentToUpdate;

    @FXML
    private TextArea contenuElementTextArea;

    @FXML
    private TextField nomElementTextField;
    private CommentaireService cs = new CommentaireService();

    private cardcommentaire frontCommentaire;
    public void loadCommentToUpdate(Commentaire commentaire) {
        contenuElementTextArea.setText(commentaire.getContenu());
        nomElementTextField.setText(commentaire.getNom());

        commentToUpdate = commentaire; // Stockez le post à mettre à jour dans un champ de la classe
    }

    public void updateElement(ActionEvent actionEvent) throws SQLException {
        if (commentToUpdate != null) {
            // Récupérez les valeurs modifiées des champs de texte
            String updatedContenu = contenuElementTextArea.getText();
            String updatedNom = nomElementTextField.getText();

            // Mettez à jour les propriétés du post sélectionné avec les nouvelles valeurs
            commentToUpdate.setContenu(updatedContenu);
            commentToUpdate.setNom(updatedNom);

            // Appelez la méthode pour mettre à jour le post dans la base de données
            cs.updateOne(commentToUpdate);
            frontCommentaire.Frontcoment.actualise();
            // Affichez une alerte de succès
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Success");
            alert.setContentText("Post mis à jour.");
            alert.showAndWait();

            // Effacez les champs de saisie après la mise à jour
            clearFieldscomment();
        } else {
            // Affichez un message si aucun post n'est sélectionné pour la modification
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Aucune sélection");
            alert.setContentText("Veuillez sélectionner un post à modifier.");
            alert.showAndWait();
        }
    }

    public void clearFieldscomment() {
        nomElementTextField.clear();
        contenuElementTextArea.clear();
    }

    public void setController(cardcommentaire cardcommentaire) {
        this.frontCommentaire = cardcommentaire;
    }
}
