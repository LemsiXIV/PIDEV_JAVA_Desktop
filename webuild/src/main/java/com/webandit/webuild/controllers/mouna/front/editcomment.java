package com.webandit.webuild.controllers.mouna.front;

import com.webandit.webuild.models.Post;
import com.webandit.webuild.services.PostService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.sql.SQLException;

public class editcomment {

    private PostService ps = new PostService();
    private Post postToUpdate;

    @FXML
    private TextField auteurTextField;

    @FXML
    private TextArea descriptionTextArea;

    @FXML
    private TextField titreTextField;

    // Méthode pour charger les données du post à mettre à jour
    public void loadPostToUpdate(Post post) {
        titreTextField.setText(post.getTitre());
        descriptionTextArea.setText(post.getDescription());
        auteurTextField.setText(post.getAuteur());
        postToUpdate = post; // Stockez le post à mettre à jour dans un champ de la classe
    }

    // Méthode pour mettre à jour le post
    @FXML
    private void updatePost(ActionEvent event) throws SQLException {
        // Vérifiez d'abord si un post est sélectionné pour la modification
        if (postToUpdate != null) {
            // Récupérez les valeurs modifiées des champs de texte
            String updatedTitre = titreTextField.getText();
            String updatedDescription = descriptionTextArea.getText();

            // Mettez à jour les propriétés du post sélectionné avec les nouvelles valeurs
            postToUpdate.setTitre(updatedTitre);
            postToUpdate.setDescription(updatedDescription);

            // Appelez la méthode pour mettre à jour le post dans la base de données
            ps.update(postToUpdate);

            // Affichez une alerte de succès
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Success");
            alert.setContentText("Post mis à jour.");
            alert.showAndWait();

            // Effacez les champs de saisie après la mise à jour
            clearFields();
        } else {
            // Affichez un message si aucun post n'est sélectionné pour la modification
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Aucune sélection");
            alert.setContentText("Veuillez sélectionner un post à modifier.");
            alert.showAndWait();
        }
    }

    // Méthode pour effacer les champs de saisie
    public void clearFields() {
        titreTextField.clear();
        descriptionTextArea.clear();
        auteurTextField.clear();
    }


}
