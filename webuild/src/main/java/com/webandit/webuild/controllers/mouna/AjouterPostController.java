package com.webandit.webuild.controllers.mouna;


import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.webandit.webuild.controllers.SessionManagement;
import com.webandit.webuild.models.Post;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.TextFieldTreeTableCell;
import javafx.scene.control.cell.TreeItemPropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.StringConverter;

import com.webandit.webuild.services.PostService;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.SQLException;

import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.time.LocalDate;

public class AjouterPostController {

    PostService ps = new PostService();



    @FXML
    private AnchorPane mainAnchorPane;

    @FXML
    private TextField titre;

    @FXML
    private TextField description;






    @FXML
    private Label titreErrorLabel;

    @FXML
    private Label descriptionErrorLabel;

    @FXML
    private Label auteurErrorLabel;

    @FXML
    private Label dateErrorLabel;

    @FXML
    private Button Parcourir;
    @FXML
    private TextField imgField;


    @FXML
    private TreeTableView<Post> tvc;

    @FXML
    private TableView<Post> tableView; // Assurez-vous de remplacer "VotreObjet" par le type d'objet que vous utilisez dans votre TableView

    @FXML
    private TableColumn<Post, String> TitreFront;

    @FXML
    private TableColumn<Post, String> DescriptionFront;

    @FXML
    private TableColumn<Post, String> AuteurFront;

    @FXML
    private TableColumn<Post, String> DateFront;

    @FXML
    private TableColumn<Post, String> ImgtabFront;

    @FXML
    private TreeTableColumn<Post, String> Titre;

    @FXML
    private TreeTableColumn<Post, String> Description;

    @FXML
    private TreeTableColumn<Post, String> Auteur;

    @FXML
    private TreeTableColumn<Post, java.sql.Date> Date;
    @FXML
    private TreeTableColumn<Post, String> Imgtab;

    @FXML
    private ImageView image;

    @FXML
    private Label datteee;

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


    @FXML
    void AddPost(ActionEvent event) throws SQLException {
        // Vérifiez si les champs requis sont vides
        if (titre.getText().isEmpty() || description.getText().isEmpty()  ) {
            setupValidation();

            return;
        }
        java.util.Date x = ps.GetCurentDate();
        // Date
       // java.sql.Date currentDate = java.sql.Date.valueOf(date.getValue());
        String auther = SessionManagement.getInstance().getNom();
        int idclient = SessionManagement.getInstance().getId();
        // Créez une nouvelle instance de Post avec les données fournies
        Post post = new Post(titre.getText(), description.getText(), auther, x,imgField.getText(),idclient);

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


    }

    @FXML
    void initialize() {

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


            Titre.setCellValueFactory(new TreeItemPropertyValueFactory<>("titre"));
            Description.setCellValueFactory(new TreeItemPropertyValueFactory<>("description"));
            Auteur.setCellValueFactory(new TreeItemPropertyValueFactory<>("auteur"));
            Date.setCellValueFactory(new TreeItemPropertyValueFactory<>("date"));
            Imgtab.setCellValueFactory(new TreeItemPropertyValueFactory<>("img"));


        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }



    @FXML
    void handleGeneratePdfButton(ActionEvent event) {
        try (PdfWriter writer = new PdfWriter("posts.pdf");
             PdfDocument pdf = new PdfDocument(writer);
             Document document = new Document(pdf)) {

            // Set the title for the PDF document
            document.add(new Paragraph("List of Posts"));

            // Retrieve posts data from the database
            PostService postService = new PostService();
            List<Post> posts = postService.read();

            // Debugging: Print retrieved posts to console
            for (Post post : posts) {
                System.out.println(post);
            }

            // Format for date
            // Format for date
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

// Create a table with 6 columns for post information
            Table table = new Table(6);

// Add table headers
            table.addCell("ID");
            table.addCell("Title");
            table.addCell("Description");
            table.addCell("Author");
            table.addCell("Date");


// Add each post to the table
            for (Post post : posts) {
                table.addCell(String.valueOf(post.getId()));
                table.addCell(post.getTitre());
                table.addCell(post.getDescription());
                table.addCell(post.getAuteur());

                // Convert java.sql.Date to java.util.Date
                java.util.Date utilDate = new java.util.Date(post.getDate().getTime());
                table.addCell(utilDate.toInstant().atZone(ZoneId.systemDefault()).format(formatter));


            }
            // Add the table to the document
            document.add(table);

            System.out.println("PDF generated successfully!");
        } catch (IOException | SQLException e) {
            e.printStackTrace();
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



        // Validation pour la date du post

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


        return error;
    }

    private boolean isValidDate(LocalDate value) {
        // Vérifiez si la date est la date actuelle
        return value.isEqual(LocalDate.now());
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
    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.show();
    }
    @FXML
    void selectPost(ActionEvent event) {


        TreeItem<Post> selectedTreeItem = tvc.getSelectionModel().getSelectedItem();
        if (selectedTreeItem != null) {
            Post selectedPost = selectedTreeItem.getValue();
            if (selectedPost != null) {
                titre.setText(selectedPost.getTitre());
                description.setText(selectedPost.getDescription());

               /* date.setValue(selectedPost.getDate());*/
            }
        } else {
            showAlert("Aucune sélection", "Aucun post sélectionné");
        }
    }

    @FXML
    void updatePost(ActionEvent event) {
        TreeItem<Post> selectedTreeItem = tvc.getSelectionModel().getSelectedItem();
        if (selectedTreeItem != null) {
            Post selectedPost = selectedTreeItem.getValue();
            if (selectedPost != null) {
                try {
                    // Mettre à jour l'objet Post dans la base de données
                    selectedPost.setTitre(titre.getText());
                    selectedPost.setDescription(description.getText());

                  /*  selectedPost.setDate(java.sql.Date.valueOf(date.getValue()));*/

                    // Call the update function from the service to update the database
                    ps.update(selectedPost);

                    // Refresh the table view after updating
                    afficherPosts();
                } catch (Exception e) {
                    showAlert("Erreur", "Erreur lors de la mise à jour du post!");
                    e.printStackTrace();
                }
            } else {
                showAlert("Aucune sélection", "Aucun post sélectionné");
            }
        }
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
    @FXML
    private StackPane contentArea;


        public void commentaire_page(ActionEvent actionEvent)throws IOException {
            Parent fxml = FXMLLoader.load(getClass().getResource("/fxml/forum/CommentaireXml.fxml"));
            contentArea.getChildren().removeAll();
            contentArea.getChildren().setAll(fxml);
        }


    public void selectDate(ActionEvent actionEvent) {
    }

    public void addPost(ActionEvent actionEvent) {
    }
}

