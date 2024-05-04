package com.webandit.webuild.controllers.mouna.front;
import com.webandit.webuild.models.Commentaire;
import com.webandit.webuild.services.CommentaireService;
import com.webandit.webuild.services.PostService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import com.webandit.webuild.models.Post;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.cell.TreeItemPropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class cardpost {

    PostService ps = new PostService();

    @FXML
    private Label titre;

    @FXML
    private Label description;
    @FXML
    private ImageView Post_img;

    @FXML
    private Label auteur;
    @FXML
    private Label date;

    private Post post;


    public void getData(Post post) {
        this.post = post;
        titre.setText(post.getTitre());
        auteur.setText(post.getAuteur());
        description.setText(post.getDescription());
        System.out.println(post.getImg());
        Image imageprofile = new Image(post.getImg());
        Post_img.setImage(imageprofile);
        date.setText(String.valueOf(post.getDate()));



    }

    int id ;
    public void setId(int id) {
        this.id=id;

    }

    public void onDeleteButtonClickFront(ActionEvent actionEvent) {
        if (id != 0) {
            try {
                System.out.println(id+"ahawaaaaa");
                ps.delete(id); // Accessing ServiceChantier methods via 'ps' instance
                // Refresh the table after deleting a chantier
            } catch (SQLException e) {

                throw new RuntimeException(e);
            }
        }
    }
    public void comment(ActionEvent actionEvent) throws IOException, SQLException {
        System.out.println("Bouton Comment cliqué");
        CommentaireService cs = new CommentaireService();
        List<Commentaire> commentaires = cs.getCommentairesByPostId(post.getId());
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/gridcommentaire.fxml"));
        Parent root1 = (Parent) fxmlLoader.load();


        // Get the controller
        FrontCommentaire controller = fxmlLoader.getController();
        controller.setData(commentaires, post);

        // Set the stage
        Stage stage = new Stage();
        controller.setStage(stage); // Pass the stage to the controller
        stage.setScene(new Scene(root1));
        stage.show();
    }

    @FXML
    private void onEditButtonClickFront(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/editcomment.fxml"));
        Parent root1 = (Parent) fxmlLoader.load();

        // Obtenez le contrôleur de la fenêtre d'édition
        editcomment editController = fxmlLoader.getController();

        // Passez le post sélectionné à la méthode pour charger les données du post à modifier
        editController.loadPostToUpdate(post); // Utilisez le post actuel ou le post sélectionné selon votre implémentation

        Stage stage = new Stage();
        stage.setScene(new Scene(root1));
        stage.show();
    }


}
