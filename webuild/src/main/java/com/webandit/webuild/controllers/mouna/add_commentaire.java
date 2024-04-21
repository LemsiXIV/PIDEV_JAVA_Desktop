package com.webandit.webuild.controllers.mouna;

import com.webandit.webuild.models.Commentaire;
import com.webandit.webuild.models.Post;
import com.webandit.webuild.services.CommentaireService;
import com.webandit.webuild.services.PostService;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.paint.Color;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class add_commentaire {

    PostService ps = new PostService();

    CommentaireService cs =new CommentaireService();
    // Méthode pour afficher une alerte en cas d'erreur
    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.show();
    }

    @FXML
    private ChoiceBox<Post> postChoiceBox;

    @FXML
    private TextArea contenuTextArea;

    @FXML
    private DatePicker dateCreationDatePicker;

    @FXML
    private TextField nomTextField;

    @FXML
    private TextField nbrLikesTextField;

    @FXML
    private TextField nbrDislikesTextField;

    @FXML
    private TextField rateTextField;

    // Méthode appelée lors de l'ajout d'un commentaire
    public void AddCommentaire(ActionEvent event) {
        try {
            LocalDate localDate = dateCreationDatePicker.getValue();
            java.sql.Date sqlDate = java.sql.Date.valueOf(localDate);

            // Récupérer le post sélectionné dans le ChoiceBox
            Post selectedPost = postChoiceBox.getValue();
            if (selectedPost == null) {
                showAlert("Erreur", "Veuillez sélectionner un post.");
                return;
            }

            String contenu = contenuTextArea.getText();
            String nom = nomTextField.getText();
            int nbrLikes = Integer.parseInt(nbrLikesTextField.getText());
            int nbrDislikes = Integer.parseInt(nbrDislikesTextField.getText());
            int rate = Integer.parseInt(rateTextField.getText());
            int postid = selectedPost.getId();
            System.out.println( nom+ "\t"+contenu+ "\t"+nbrLikes+ "\t"+nbrDislikes+ "\t"+rate+ "\t"+postid + "\t"+sqlDate);

            // Créer une instance de Commentaire avec les valeurs récupérées
            Commentaire commentaire = new Commentaire(contenu, nom, sqlDate, nbrLikes, nbrDislikes, rate,postid);

            // Ajouter le commentaire à la base de données
            // Remplacez la ligne suivante par l'appel à la méthode de votre service pour insérer le commentaire dans la base de données
             cs.insertOne(commentaire);


            // Afficher une alerte de succès
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Succès");
            alert.setContentText("Commentaire ajouté avec succès.");
            alert.showAndWait();

            // Effacer les champs après l'ajout du commentaire
            clearFields();
        } catch (NumberFormatException e) {
            // Afficher une alerte en cas d'erreur de saisie
            showAlert("Erreur", "Veuillez saisir des données valides.");

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    // Méthode pour effacer les champs après l'ajout d'un commentaire
    @FXML
    private void clearFields() {
        contenuTextArea.clear();
        nomTextField.clear();
        nbrLikesTextField.clear();
        nbrDislikesTextField.clear();
        rateTextField.clear();
        dateCreationDatePicker.setValue(null);
        postChoiceBox.setValue(null);
    }

    // Méthode appelée lors de l'initialisation du contrôleur
    public void initialize() {
        try {
            // Load Chantiers into the ChoiceBox
            postChoiceBox.setItems(FXCollections.observableArrayList(ps.read()));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
