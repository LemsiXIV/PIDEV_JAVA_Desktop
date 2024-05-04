package com.webandit.webuild.controllers.mouna;

import com.webandit.webuild.models.Commentaire;
import com.webandit.webuild.models.EmailSender;
import com.webandit.webuild.models.Post;
import com.webandit.webuild.services.CommentaireService;
import com.webandit.webuild.services.PostService;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import com.webandit.webuild.controllers.mouna.AjouterCommentaireController;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import static com.webandit.webuild.models.EmailSender.AjoutCommentaireEmail;

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
    @FXML
    private Label ContenuLabelError;
    @FXML
    private Label LikesLabelError;
    @FXML
    private Label DislikesLabelError;
    @FXML
    private Label NomLabelError;

    @FXML
    private Label DateLabelError;

    // Méthode appelée lors de l'ajout d'un commentaire
    public void AddCommentaire(ActionEvent event) {
        try {
            // Vérifier si tous les champs requis sont remplis
            if (contenuTextArea.getText().isEmpty() || nomTextField.getText().isEmpty() ||
                    nbrLikesTextField.getText().isEmpty() || nbrDislikesTextField.getText().isEmpty() ||
                    rateTextField.getText().isEmpty() || dateCreationDatePicker.getValue() == null) {
                setupValidation();
                setupValidationtype();

                return;
            }

            // Récupérer les valeurs des champs
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

            System.out.println(nom + "\t" + contenu + "\t" + nbrLikes + "\t" + nbrDislikes + "\t" + rate + "\t" + postid + "\t" + sqlDate);

            // Créer une instance de Commentaire avec les valeurs récupérées
            Commentaire commentaire = new Commentaire(contenu, nom, sqlDate, nbrLikes, nbrDislikes, rate, postid);
            if (setupValidationtype() == 0) {
                // Ajouter le commentaire à la base de données

                cs.insertOne(commentaire);
                EmailSender.AjoutCommentaireEmail("jomaamouna@gmail.com","test");

                // Afficher une alerte de succès
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Succès");
                alert.setContentText("Commentaire ajouté avec succès.");
                alert.showAndWait();
            }
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


    public void setupValidation() {
        // Validation pour le contenu du commentaire
        if (!isValidString(contenuTextArea.getText())) {
            displayError(contenuTextArea, ContenuLabelError, "Le contenu du commentaire ne peut pas être vide");
        } else {
            displaySuccess(contenuTextArea, ContenuLabelError);
        }

        // Validation pour le nom de l'auteur du commentaire
        if (!isValidString(nomTextField.getText())) {
            displayError(nomTextField, NomLabelError, "Le nom de l'auteur ne peut pas être vide");
        } else {
            displaySuccess(nomTextField, NomLabelError);
        }

        // Validation pour la date de création du commentaire
        if (dateCreationDatePicker.getValue() == null || !dateCreationDatePicker.getValue().isEqual(LocalDate.now())) {
            displayError(dateCreationDatePicker, DateLabelError, "La date de création du commentaire doit être sélectionnée et ne peut pas être dans le futur");
        } else {
            displaySuccess(dateCreationDatePicker, DateLabelError);
        }

        // Validation pour le nombre de likes
        if (!isValidNumber(nbrLikesTextField.getText())) {
            displayError(nbrLikesTextField, LikesLabelError, "Le nombre de likes doit être un entier positif");
        } else {
            displaySuccess(nbrLikesTextField, LikesLabelError);
        }

        // Validation pour le nombre de dislikes
        if (!isValidNumber(nbrDislikesTextField.getText())) {
            displayError(nbrDislikesTextField, DislikesLabelError, "Le nombre de dislikes doit être un entier positif");
        } else {
            displaySuccess(nbrDislikesTextField, DislikesLabelError);
        }
    }


    private boolean isValidNumber(String input) {
        try {
            int number = Integer.parseInt(input);
            return number >= 0;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private boolean isValidString(String text) {
        return text.matches("[a-zA-Z\\s]+");
    }

    private void displayError(Control control, Label errorLabel, String errorMessage) {
        control.getStyleClass().add("error-field");
        errorLabel.setText(errorMessage);
        errorLabel.setTextFill(Color.RED);
    }
    private void displaySuccess(Control control, Label errorLabel) {
        control.getStyleClass().remove("error-field");
        errorLabel.setText("Validé !");
        errorLabel.setTextFill(Color.GREEN);
    }
    public int setupValidationtype() {
        int error = 0;

        // Validation pour le contenu du commentaire
        if (!isValidString(contenuTextArea.getText())) {
            displayError(contenuTextArea, ContenuLabelError, "Le contenu du commentaire doit être une chaîne de caractères");
            error++;
        } else {
            displaySuccess(contenuTextArea, ContenuLabelError);
        }

        // Validation pour le nom de l'utilisateur
        if (!isValidString(nomTextField.getText())) {
            displayError(nomTextField, NomLabelError, "Le nom de l'utilisateur doit être une chaîne de caractères");
            error++;
        } else {
            displaySuccess(nomTextField, NomLabelError);
        }

        // Validation pour le nombre de likes
        //int nbrlikes = Integer.parseInt(nbrLikesTextField.getText());
        if (!isValidNumber(nbrLikesTextField.getText())) {
            displayError(nbrLikesTextField, LikesLabelError, "Le nombre de likes doit être un entier");
            error++;
        } else {
            displaySuccess(nbrLikesTextField, LikesLabelError);
        }

        // Validation pour le nombre de dislikes
        //int nbrqislikes = Integer.parseInt(nbrDislikesTextField.getText());
        if (!isValidNumber(nbrDislikesTextField.getText())) {
            displayError(nbrDislikesTextField, DislikesLabelError, "Le nombre de dislikes doit être un entier");
            error++;
        } else {
            displaySuccess(nbrDislikesTextField, DislikesLabelError);
        }

        // Validation pour la date du commentaire (accepte les dates futures et passées)
        LocalDate commentDate = dateCreationDatePicker.getValue();
        if (!commentDate.isEqual(LocalDate.now())) {
            displayError(dateCreationDatePicker, DateLabelError, "Veuillez sélectionner une date valide");
            error++;
        } else {
            displaySuccess(dateCreationDatePicker, DateLabelError);
        }

        return error;
    }





}
