package com.webandit.webuild.controllers.mouna.front;

import com.webandit.webuild.controllers.mouna.AjouterCommentaireController;
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

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import static com.webandit.webuild.models.EmailSender.AjoutCommentaireEmail;


public class add_commentaire {

    PostService ps = new PostService();
    private  FrontCommentaire frontCommentaire;
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
                    rateTextField.getText().isEmpty() ) {
                setupValidation();
                setupValidationtype();
                return;
            }



            java.sql.Date sqlDate = cs.GetCurentDate();
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

            // Vérifier si le commentaire contient des mots inappropriés
            if (contientMotsInterdits(contenu)) {
                showAlert("Erreur", "Le contenu du commentaire contient des mots inappropriés.");
                return;
            }

            // Créer une instance de Commentaire avec les valeurs récupérées
            Commentaire commentaire = new Commentaire(contenu, nom, sqlDate, nbrLikes, nbrDislikes, rate, postid);
            if (setupValidationtype() == 0) {
                // Ajouter le commentaire à la base de données
                cs.insertOne(commentaire);
                frontCommentaire.actualise();

                AjouterCommentaireController.actualise();
                System.out.println("ooooooooooooooo");

                EmailSender.AjoutCommentaireEmail("jomaamouna@gmail.com", "test");

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

    private boolean contientMotsInterdits(String contenu) {
        // Liste de mots interdits
        List<String> motsInterdits = Arrays.asList("java", "esprit","pidev");

        // Vérifier si le contenu contient des mots interdits
        for (String mot : motsInterdits) {
            if (contenu.toLowerCase().contains(mot)) {
                return true;
            }
        }
        return false;
    }


    // Méthode pour effacer les champs après l'ajout d'un commentaire
    @FXML
    private void clearFields() {
        contenuTextArea.clear();
        nomTextField.clear();
        nbrLikesTextField.clear();
        nbrDislikesTextField.clear();
        rateTextField.clear();

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



        // Validation pour le nombre de likes

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

        // Validation pour la date du commentaire (accepte les dates futures et passées)


        return error;
    }


    public void setControllerAct(FrontCommentaire frontCommentaire) {
        this. frontCommentaire = frontCommentaire;
    }
}
