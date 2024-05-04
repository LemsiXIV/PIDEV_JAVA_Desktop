package com.webandit.webuild.controllers.mouna;

import com.webandit.webuild.models.Commentaire;
import com.webandit.webuild.services.CommentaireService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.TreeItemPropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;

public class AjouterCommentaireController {

    private final CommentaireService cs = new CommentaireService();

    @FXML
    private AnchorPane mainAnchorPane;

    @FXML
    private TextField contenu;

    @FXML
    private TextField nom;
    @FXML
    private TextField nbrLikes;

    @FXML
    private TextField nbrDislikes;

    @FXML
    private DatePicker date;

    @FXML
    private Label contenuErrorLabel;

    @FXML
    private Label nomErrorLabel;

    @FXML
    private Label nbrLikesErrorLabel;

    @FXML
    private Label nbrDislikesErrorLabel;
    @FXML
    private Label dateErrorLabel;

    @FXML
    private TreeTableView<Commentaire> tvc;



    @FXML
    private TreeTableColumn<Commentaire, Integer> nbrlikes;

    @FXML
    private TreeTableColumn<Commentaire, Integer> nbrdislikes;

    @FXML
    private TreeTableColumn<Commentaire, String> Contenu;

    @FXML
    private TreeTableColumn<Commentaire, String> Nom;

    @FXML
    private TreeTableColumn<Commentaire, Integer> rate;

    @FXML
    private TreeTableColumn<Commentaire, java.sql.Date> Date;

    @FXML
    private Label datteee;

  /*  @FXML
    void AddCommentaireeee(ActionEvent event) {
        if (contenu.getText().isEmpty() || nom.getText().isEmpty() || date.getValue() == null) {
            setupValidation();
            return;
        }

        java.sql.Date currentDate = java.sql.Date.valueOf(date.getValue());

        Commentaire commentaire = new Commentaire( contenu.getText(), nom.getText(), currentDate, 0, 0, 0, 0);

        try {
            cs.insertOne(commentaire);
            showAlert("Success", "Commentaire ajouté.");
            clearFields();
            afficherCommentaires();
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert("Error", "Erreur lors de l'ajout du commentaire.");
        }
    }*/

    public void clearFields() {
        contenu.clear();
        nom.clear();
        date.setValue(null);
    }

    @FXML
    void initialize() {
        afficherCommentaires();
    }

    public void afficherCommentaires() {
        try {
            ObservableList<Commentaire> observableList = FXCollections.observableList(cs.selectAll());

            TreeItem<Commentaire> root = new TreeItem<>(null);
            root.setExpanded(true);

            for (Commentaire commentaire : observableList) {
                TreeItem<Commentaire> item = new TreeItem<>(commentaire);
                root.getChildren().add(item);
            }

            tvc.setRoot(root);
            tvc.setShowRoot(false);


            Contenu.setCellValueFactory(new TreeItemPropertyValueFactory<>("contenu"));
            Nom.setCellValueFactory(new TreeItemPropertyValueFactory<>("nom"));
            nbrlikes.setCellValueFactory(new TreeItemPropertyValueFactory<>("nbrLikes"));
            nbrdislikes.setCellValueFactory(new TreeItemPropertyValueFactory<>("nbrDislikes"));
            rate.setCellValueFactory(new TreeItemPropertyValueFactory<>("rate"));
            Date.setCellValueFactory(new TreeItemPropertyValueFactory<>("dateCreation"));

        } catch (SQLException e) {
            e.printStackTrace();
            showAlert("Error", "Erreur lors du chargement des commentaires.");
        }
    }

    @FXML
    void selectdate(ActionEvent event) {
        if (date.getValue() != null) {
            datteee.setText(date.getValue().toString());
        }
    }

    public void setupValidation() {
        // Validation pour le contenu du commentaire
        if (contenu.getText().isEmpty()) {
            displayError( contenuErrorLabel, "Veuillez entrer du contenu.");
        } else {
            displaySuccess( contenuErrorLabel, "contenu validé");
        }

        // Validation pour le nom de l'auteur du commentaire
        if (nom.getText().isEmpty() || nom.getText().matches("\\d+")) {
            displayError(nomErrorLabel, "Veuillez entrer un nom valide (sans chiffres).");
        } else {
            displaySuccess( nomErrorLabel,"Nom validé");
        }

        // Validation pour la date de création du commentaire (pas de date future)
        if (date.getValue() == null || date.getValue().isAfter(LocalDate.now())) {
            displayError(dateErrorLabel, "Veuillez sélectionner une date valide (pas de date future).");
        } else {
            displaySuccess(dateErrorLabel, "date validé");
        }

        // Validation pour le nombre de likes (doit être un entier)
        try {
            Integer.parseInt(nbrlikes.getText());
            displaySuccess( nbrLikesErrorLabel,"nbr likes validé");
        } catch (NumberFormatException e) {
            displayError(nbrLikesErrorLabel, "Veuillez entrer un nombre entier pour les likes.");
        }

        // Validation pour le nombre de dislikes (doit être un entier)
        try {
            Integer.parseInt(nbrdislikes.getText());
            displaySuccess(nbrDislikesErrorLabel, "");
        } catch (NumberFormatException e) {
            displayError( nbrDislikesErrorLabel, "Veuillez entrer un nombre entier pour les dislikes.");
        }
    }
    private void displaySuccess(Label label, String message) {
        label.setText(message);
        label.setTextFill(Color.GREEN); // Assurez-vous d'importer javafx.scene.paint.Color
    }

    private void displayError(Label label, String message) {
        label.setText(message);
        label.setTextFill(Color.RED); // Assurez-vous d'importer javafx.scene.paint.Color
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }

    public void selectComment(ActionEvent actionEvent) {
    }

    public void updateComment(ActionEvent actionEvent) {
        Commentaire selectCommentaire = tvc.getSelectionModel().getSelectedItem().getValue();
        if (selectCommentaire != null) {
            try {
                // Mettez à jour l'objet Commentaire dans la base de données
                selectCommentaire.setContenu(contenu.getText());
                selectCommentaire.setNom(nom.getText());
                selectCommentaire.setDateCreation(java.sql.Date.valueOf(date.getValue()));



                // Appelez la fonction d'update du service pour mettre à jour la base de données
                cs.updateOne(selectCommentaire);

                // Rafraîchissez la table view après la mise à jour
                afficherCommentaires();
            } catch (SQLException e) {
                e.printStackTrace();
                showAlert("Erreur", "Erreur lors de la mise à jour du commentaire !");
            }
        } else {
            showAlert("Aucune sélection", "Aucun commentaire sélectionné");
        }
    }
    @FXML
    void selectCommentaire(ActionEvent actionEvent) {
        TreeItem<Commentaire> selectedTreeItem = tvc.getSelectionModel().getSelectedItem();
        if (selectedTreeItem != null) {
            Commentaire selectedComment = selectedTreeItem.getValue();
            if (selectedComment != null) {
                contenu.setText(selectedComment.getContenu());
                nom.setText(selectedComment.getNom());
                date.setValue(selectedComment.getDateCreation().toLocalDate());
            }
        } else {
            showAlert("Aucune sélection", "Aucun commentaire sélectionné");
        }
    }

    public void Deletecomment(ActionEvent actionEvent) {
        Commentaire selectCommentaire = tvc.getSelectionModel().getSelectedItem().getValue();
        if (selectCommentaire != null) {
            try {
                // Supprimez le commentaire de la base de données
                cs.deleteOne(selectCommentaire.getId());

                // Affichez une alerte de succès
                showAlert("Success", "Commentaire supprimé.");

                // Rafraîchissez la table view après la suppression
                afficherCommentaires();
            } catch (SQLException e) {
                e.printStackTrace();
                showAlert("Erreur", "Erreur lors de la suppression du commentaire !");
            }
        } else {
            showAlert("Aucune sélection", "Aucun commentaire sélectionné");
        }

    }
    public void ajoutComment(ActionEvent event) throws IOException {


        afficherCommentaires();

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/add_comment.fxml"));
        Parent root1 = (Parent) fxmlLoader.load();
        Stage stage = new Stage();
        stage.setScene(new Scene(root1));
        stage.show();

    }
    public void refrech(ActionEvent event) {
        afficherCommentaires(); // Refresh the TableView
    }
}
