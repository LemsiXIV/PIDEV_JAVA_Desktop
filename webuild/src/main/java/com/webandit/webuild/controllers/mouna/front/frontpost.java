package com.webandit.webuild.controllers.mouna.front;


import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.webandit.webuild.models.Post;
import com.webandit.webuild.services.PostService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import javafx.geometry.Insets;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class frontpost{
    @FXML
    private GridPane grid;

    @FXML
    private ScrollPane scroll;

    @FXML
    private TextField searchField;

    private final PostService ps = new PostService();

    @FXML
    void initialize() {
        try {
            actualise();
            // Ajouter un écouteur d'événements au champ de recherche
            searchField.textProperty().addListener((observable, oldValue, newValue) -> {
                try {
                    if (newValue.isEmpty()) {
                        // Si le champ de recherche est vide, afficher tous les articles
                        actualise();
                    } else {
                        // Sinon, actualiser la liste des articles en fonction du terme de recherche
                        actualiseWithSearch(newValue);
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            });
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void actualise() throws SQLException {
        // Effacer les éléments actuels de la grille
        grid.getChildren().clear();

        List<Post> posts = ps.read();
        if (posts.isEmpty()) {
            System.out.println("post est vide.");
            return;
        }

        int column = 0;
        int row = 3;

        grid.setHgap(50);

        for (Post post : posts) {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/forum/cardfront.fxml"));
                AnchorPane anchorPane = fxmlLoader.load();

                cardpost p = fxmlLoader.getController();
                if (p == null) {
                    System.out.println("Le contrôleur de l'élément n'a pas été initialisé.");
                    continue;
                }

                p.getData(post);
                p.setId(post.getId());
                p.setControllerAct(this);


                grid.add(anchorPane, column++, row);
                GridPane.setMargin(anchorPane, new Insets(10));
                if (column == 3) {
                    column = 0;
                    row++;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void actualiseWithSearch(String searchTerm) throws SQLException {
        grid.getChildren().clear();

        List<Post> posts = ps.rechercherPosts(searchTerm);
        System.out.println("searchraniala trabhek" + searchTerm+"posts."+posts);
        if (posts.isEmpty()) {
            System.out.println("Aucun post trouvé pour le terme de recherche : " + searchTerm);
            return;
        }

        int column = 0;
        int row = 3;
        grid.setHgap(50);

        for (Post post : posts) {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/forum/cardfront.fxml"));
                AnchorPane anchorPane = fxmlLoader.load();

                cardpost c = fxmlLoader.getController();
                if (c == null) {
                    System.out.println("Le contrôleur de l'élément n'a pas été initialisé.");
                    continue;
                }

                c.getData(post);


                grid.add(anchorPane, column++, row);
                GridPane.setMargin(anchorPane, new Insets(10));
                if (column == 3) {
                    column = 0;
                    row++;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    public void goadd(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/forum/add_post.fxml"));
        Parent root1 = (Parent) fxmlLoader.load();

        addPostFront controller = fxmlLoader.getController();
        controller.setFrontController(this);
        Stage stage = new Stage();
        stage.setScene(new Scene(root1));
        stage.show();
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
            table.addCell("Image");

// Add each post to the table
            for (Post post : posts) {
                table.addCell(String.valueOf(post.getId()));
                table.addCell(post.getTitre());
                table.addCell(post.getDescription());
                table.addCell(post.getAuteur());

                // Convert java.sql.Date to java.util.Date
                java.util.Date utilDate = new java.util.Date(post.getDate().getTime());
                table.addCell(utilDate.toInstant().atZone(ZoneId.systemDefault()).format(formatter));

                table.addCell(post.getImg());
            }
            // Add the table to the document
            document.add(table);

            System.out.println("PDF generated successfully!");
        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }
    }

}
