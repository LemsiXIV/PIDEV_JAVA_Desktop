package com.webandit.webuild.controllers.mouna;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.TextFieldTreeTableCell;
import javafx.scene.control.cell.TreeItemPropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;

import com.webandit.webuild.controllers.mouna.ButtonCell;

import com.webandit.webuild.controllers.mouna.ButtonCellFactory;
import javafx.util.StringConverter;
import javafx.util.converter.IntegerStringConverter;

import com.webandit.webuild.models.Post;

import com.webandit.webuild.services.PostService;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public class AjouterPostController {

    PostService ps = new PostService();



    @FXML
    private AnchorPane mainAnchorPane;

    @FXML
    private TextField titre;

    @FXML
    private TextField description;

    @FXML
    private TextField auteur;

    @FXML
    private DatePicker date;


    @FXML
    private Label titreErrorLabel;

    @FXML
    private Label descriptionErrorLabel;

    @FXML
    private Label auteurErrorLabel;

    @FXML
    private Label dateErrorLabel;




    @FXML
    private TreeTableView<Post> tvc;

    @FXML
    private TreeTableColumn<Post, Integer> id;

    @FXML
    private TreeTableColumn<Post, String> Titre;

    @FXML
    private TreeTableColumn<Post, String> Description;

    @FXML
    private TreeTableColumn<Post, String> Auteur;

    @FXML
    private TreeTableColumn<Post, java.sql.Date> Date;

    @FXML
    private Label datteee;
    @FXML
    void AddPost(ActionEvent event) throws SQLException {
        // Vérifiez si les champs requis sont vides
        if (titre.getText().isEmpty() || description.getText().isEmpty() || auteur.getText().isEmpty() ) {
            setupValidation();
            return;
        }
        java.util.Date x = ps.GetCurentDate();
        // Date
     // java.sql.Date currentDate = java.sql.Date.valueOf(date.getValue());

        // Créez une nouvelle instance de Post avec les données fournies
        Post post = new Post(titre.getText(), description.getText(), auteur.getText(), x);

        if (setupValidationtype() == 0) {
            // Ajoutez le post à la base de données
            ps.create(post);

            // Affichez une alerte de succès
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Success");
            alert.setContentText("Post ajouté.");
            alert.showAndWait();
            clearFields();
            afficherPosts();
        }
    }

    // Méthode pour effacer les champs de saisie après avoir ajouté un post
    public void clearFields() {
        titre.clear();
        description.clear();
        auteur.clear();
        date.setValue(null);
    }

    @FXML
    void initialize() throws SQLException {

        // Appeler la méthode pour afficher les posts
        afficherPosts();


        // Préparation des boutons
        initializetreetableviewbutton();

        // Ajoutez un écouteur d'événements à la sélection de la TreeTableView
        tvc.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                // Récupérez l'objet Post correspondant à la ligne sélectionnée
                Post selectedPost = newSelection.getValue();
                // Activez l'édition des cellules pour cet objet
                activation_des_cellules(selectedPost);
            }
        });
    }

    public void afficherPosts() {
        try {
            ObservableList<Post> observableList = FXCollections.observableList(ps.read());

            TreeItem<Post> root = new TreeItem<>(null);
            root.setExpanded(true);

            for (Post post : observableList) {
                TreeItem<Post> item = new TreeItem<>(post);
                root.getChildren().add(item);
            }

            tvc.setRoot(root);
            tvc.setShowRoot(false);

            id.setCellValueFactory(new TreeItemPropertyValueFactory<>("id"));
            Titre.setCellValueFactory(new TreeItemPropertyValueFactory<>("titre"));
            Description.setCellValueFactory(new TreeItemPropertyValueFactory<>("description"));
            Auteur.setCellValueFactory(new TreeItemPropertyValueFactory<>("auteur"));
            Date.setCellValueFactory(new TreeItemPropertyValueFactory<>("date"));

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void selectdate(ActionEvent event) {
        // Mettez à jour le libellé de la date pour afficher la date sélectionnée
        if (date.getValue() != null) {
            datteee.setText(date.getValue().toString());
        }
    }



    public void setupValidation() {
        // Validation pour le titre du post
        if (titre.getText().isEmpty()) {
            titre.getStyleClass().add("empty-field");
            titreErrorLabel.setText("Veuillez donner le titre du post");
            titreErrorLabel.setTextFill(Color.RED);
        } else {
            titre.getStyleClass().remove("empty-field");
            titreErrorLabel.setText("Validé !");
            titreErrorLabel.setTextFill(Color.GREEN);
        }

        // Validation pour la description du post
        if (description.getText().isEmpty()) {
            description.getStyleClass().add("empty-field");
            descriptionErrorLabel.setText("Veuillez donner la description du post");
            descriptionErrorLabel.setTextFill(Color.RED);
        } else {
            description.getStyleClass().remove("empty-field");
            descriptionErrorLabel.setText("Validé !");
            descriptionErrorLabel.setTextFill(Color.GREEN);
        }

        // Validation pour l'auteur du post
        if (auteur.getText().isEmpty()) {
            auteur.getStyleClass().add("empty-field");
            auteurErrorLabel.setText("Veuillez donner l'auteur du post");
            auteurErrorLabel.setTextFill(Color.RED);
        } else {
            auteur.getStyleClass().remove("empty-field");
            auteurErrorLabel.setText("Validé !");
            auteurErrorLabel.setTextFill(Color.GREEN);
        }

        // Validation pour la date du post
        if (date.getValue() == null) {
            date.getStyleClass().add("empty-field");
            dateErrorLabel.setText("Veuillez sélectionner une date");
            dateErrorLabel.setTextFill(Color.RED);
        } else {
            date.getStyleClass().remove("empty-field");
            dateErrorLabel.setText("Validé !");
            dateErrorLabel.setTextFill(Color.GREEN);
        }
    }

    public int setupValidationtype() {
        int error = 0;
        // Validation pour le titre du post
        if (!isValidString(titre.getText())) {
            displayError(titre, titreErrorLabel,
                    "Le titre du post doit être une chaîne de caractères");
            error++;
        } else {
            displaySuccess(titre, titreErrorLabel);
        }

        // Validation pour la description du post
        if (!isValidString(description.getText())) {
            displayError(description, descriptionErrorLabel, "La description du post doit être une chaîne de caractères");
            error++;
        } else {
            displaySuccess(description, descriptionErrorLabel);
        }

        // Validation pour l'auteur du post
        if (!isValidString(auteur.getText())) {
            displayError(auteur, auteurErrorLabel, "L'auteur du post doit être une chaîne de caractères");
            error++;
        } else {
            displaySuccess(auteur, auteurErrorLabel);
        }



        return error;
    }


    private boolean isValidString(String input) {
        // Vérifiez si la chaîne n'est pas vide
        return !input.isEmpty();
    }

    // Méthode pour vérifier si une date est valide


    // Méthode pour afficher un message d'erreur
    private void displayError(Control control, Label errorLabel, String errorMessage) {
        control.getStyleClass().add("error-field");
        errorLabel.setText(errorMessage);
        errorLabel.setTextFill(Color.RED);
    }

    // Méthode pour afficher un message de succès
    private void displaySuccess(Control control, Label errorLabel) {
        control.getStyleClass().remove("error-field");
        errorLabel.setText("Validé !");
        errorLabel.setTextFill(Color.GREEN);
    }

    // Méthode pour gérer le clic sur le bouton Supprimer
    public Void onDeleteButtonClick(Post item) {
        try {
            // Supprimer le post dans la base de données
            // Remplacez "ps" par votre objet service pour la suppression des posts
            ps.delete(item.getId());
            System.out.println("Supprimer: " + item);
            // Réafficher les posts mis à jour
            afficherPosts();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


    public void activation_des_cellules(Post item) {
        // Activer l'édition des cellules dans chaque colonne
        Titre.setCellFactory(TextFieldTreeTableCell.forTreeTableColumn());
        Description.setCellFactory(TextFieldTreeTableCell.forTreeTableColumn());
        Auteur.setCellFactory(TextFieldTreeTableCell.forTreeTableColumn());
        Date.setCellFactory(TextFieldTreeTableCell.forTreeTableColumn(new StringConverter<java.sql.Date>() {
            @Override
            public String toString(java.sql.Date object) {
                return object != null ? object.toString() : "";
            }

            @Override
            public java.sql.Date fromString(String string) {
                return string != null && !string.isEmpty() ? java.sql.Date.valueOf(string) : null;
            }
        }));

        // Ajouter des écouteurs pour détecter les modifications
        Titre.setOnEditCommit(event -> {
            item.setTitre(event.getNewValue());
        });

        Description.setOnEditCommit(event -> {
            item.setDescription(event.getNewValue());
        });

        Date.setOnEditCommit(event -> {
            item.setDate(java.sql.Date.valueOf(event.getNewValue().toLocalDate()));
        });

        Auteur.setOnEditCommit(event -> {
            item.setAuteur(event.getNewValue());
        });
    }
    public Void onEditButtonClick(Post item) {
        TreeItem<Post> selectedTreeItem = tvc.getSelectionModel().getSelectedItem();
        if (selectedTreeItem != null) {
            Post selectedPost = selectedTreeItem.getValue();
            try {
                // Mettre à jour l'objet Post dans la base de données
                ps.update(selectedPost);
                System.out.println("Modifier: " + selectedPost);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            // Aucune ligne sélectionnée, afficher un message d'erreur ou prendre d'autres mesures
            System.err.println("Aucune ligne sélectionnée.");
        }
        return null;
    }
    // Méthode pour charger les données dans le TreeTableView
    private void initializetreetableviewbutton() {
        TreeTableColumn<Post, Void> actionColumn = new TreeTableColumn<>("Actions");
        actionColumn.setCellFactory(new ButtonCellFactory(tvc, this::onDeleteButtonClick, this::onEditButtonClick));

        // Ajouter la colonne d'actions à votre TreeTableView
        tvc.getColumns().add(actionColumn);
    }
}

