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
    private frontpost frontcontroller;

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
                ps.delete(id);
                frontcontroller.actualise(); // Accessing ServiceChantier methods via 'ps' instance
                // Refresh the table after deleting a chantier
            } catch (SQLException e) {

                throw new RuntimeException(e);
            }
        }
    }

    private void actualise() {
        getData(post);
    }

    public void comment(ActionEvent actionEvent) throws IOException, SQLException {

        if (post != null && post.getId() != 0) {
            // Assurez-vous que post est défini et que son identifiant n'est pas 0
            CommentaireService cs = new CommentaireService();
            List<Commentaire> commentaires = cs.getCommentsPostId(post.getId());
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/gridcommentaire.fxml"));
            Parent root1 = fxmlLoader.load();
            PostDetails.getInstance().setIdPost(post.getId());

            int newid =PostDetails.getInstance().getIdPost();
            System.out.println(newid  + "\t ahaaawaaa cardpost controller");
            // Get the controller
            FrontCommentaire controller = fxmlLoader.getController();
            System.out.println(post.getId() + "\t ahaaawaaa cardpost controller");
            controller.setData(post.getId());

            // Set the stage
            Scene scene = new Scene(root1);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();
        } else {
            System.out.println("L'identifiant du post n'est pas correctement défini.");
        }
    }


    public void onEditButtonClickFront(ActionEvent actionEvent) throws IOException {


        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/editcomment.fxml"));
        Parent root1 = (Parent) fxmlLoader.load();
        editcomment editController = fxmlLoader.getController();

        // Passez le post sélectionné à la méthode pour charger les données du post à modifier
        editController.loadPostToUpdate(post); //
        Stage stage = new Stage();
        stage.setScene(new Scene(root1));
        stage.show();
        stage.setOnCloseRequest(event -> {
            actualise();
        });

    }

    public void setControllerAct(frontpost frontpost) {
        this.frontcontroller =frontpost;
    }
}
