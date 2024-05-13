package com.webandit.webuild.controllers.mouna.front;

import com.webandit.webuild.controllers.SessionManagement;
import com.webandit.webuild.models.Post;
import com.webandit.webuild.services.PostService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.SQLException;

public class addPostFront {

    private PostService ps = new PostService();

    @FXML
    private TextField titreTextField;

    @FXML
    private TextArea descriptionTextArea;

    @FXML
    private TextField auteurTextField;


    @FXML
    private ImageView image;

    @FXML
    private Button Parcourir;
    @FXML
    private TextField imgField;

    private frontpost frontpost;

    private String xamppFolderPath="c:/xampp/xampp/htdocs/img/";

    public void parcourirImage(ActionEvent event) {
        FileChooser fileChooser=new FileChooser();
        fileChooser.setTitle("Choisi une image");
        Stage stage = new Stage();
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("JPG","*.jpg"),
                new FileChooser.ExtensionFilter("JPEG","*.jpeg"),
                new FileChooser.ExtensionFilter("PNG","*.png")
        );
        File file = fileChooser.showOpenDialog(stage);

        if (file != null) {
            Path source = file.toPath();
            String fileName = file.getName();
            Path destination = Paths.get(xamppFolderPath + fileName);
            String imgURL=xamppFolderPath+fileName;
            try {
                Files.copy(source, destination, StandardCopyOption.REPLACE_EXISTING);
                imgField.setText(imgURL);
                Image image1= new Image("file:" +imgURL);
                image.setImage(image1);


            } catch (IOException ex) {
                System.out.println("Could not get the image");
                ex.printStackTrace();
            }
        } else {
            System.out.println("No file selected");
        }

    }

    // Méthode appelée lors de l'ajout d'un post

    public void addPostFront(ActionEvent event) throws SQLException {


        java.util.Date x = ps.GetCurentDate();

        String auther = SessionManagement.getInstance().getNom();
        int idclient = SessionManagement.getInstance().getId();
        // Créez une nouvelle instance de Post avec les données fournies
        Post post = new Post(titreTextField.getText(), descriptionTextArea.getText(), auther, x,imgField.getText(),idclient);

            // Ajoutez le post à la base de données
            ps.create(post);
        frontpost.actualise();
        System.out.println("this is the data realte to the post " +post);
            // Affichez une alerte de succès
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Success");
            alert.setContentText("Post ajouté.");
            alert.showAndWait();
            clearFields();

        }

    // Méthode pour effacer les champs après l'ajout d'un post
    @FXML
    private void clearFields() {
        titreTextField.clear();
        descriptionTextArea.clear();
        auteurTextField.clear();

    }

    // Méthode pour la validation des champs
    private void setupValidation() {
        if (titreTextField.getText().isEmpty()) {
            displayError(titreTextField, "Le titre ne peut pas être vide");
        } else {
            displaySuccess(titreTextField);
        }

        if (descriptionTextArea.getText().isEmpty()) {
            displayError(descriptionTextArea, "La description ne peut pas être vide");
        } else {
            displaySuccess(descriptionTextArea);
        }

        if (auteurTextField.getText().isEmpty()) {
            displayError(auteurTextField, "L'auteur ne peut pas être vide");
        } else {
            displaySuccess(auteurTextField);
        }


    }

    // Méthode pour afficher un message d'erreur pour un champ donné
    private void displayError(Control control, String errorMessage) {
        control.getStyleClass().add("error-field");
        displayMessage(errorMessage, Color.RED);
    }

    // Méthode pour afficher un message de succès pour un champ donné
    private void displaySuccess(Control control) {
        control.getStyleClass().remove("error-field");
        displayMessage("Validé !", Color.GREEN);
    }

    // Méthode pour afficher un message dans une étiquette donnée avec une certaine couleur
    private void displayMessage(String message, Color color) {
        // Ici, vous pouvez afficher le message où vous le souhaitez, peut-être dans une étiquette de votre interface utilisateur
    }

    public void setFrontController(frontpost frontpost) {
        this.frontpost = frontpost;
    }
}
