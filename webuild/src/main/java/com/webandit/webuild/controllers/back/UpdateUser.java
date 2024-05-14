package com.webandit.webuild.controllers.back;

import com.webandit.webuild.controllers.SessionManagement;
import com.webandit.webuild.models.User;
import com.webandit.webuild.services.serviceUtilisateur;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.SwipeEvent;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
//import java.util.Date;
import java.sql.Date;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.Collection;

public class UpdateUser {

    @FXML
    private Slider activation;

    @FXML
    private TextField adresse;

    @FXML
    private TextField email;
    @FXML
    private StackPane contentArea;
    @FXML
    private Button deleteButton;
    @FXML
    private TextField biotxt;

    @FXML
    private TextField cintxt;

    @FXML
    private DatePicker datetxt;

    @FXML
    private TextField fonctiontxt;

    @FXML
    private TextField verifiedtxt;

    @FXML
    private TextField nom;

    @FXML
    private TextField prenom;

    @FXML
    private TextField telephone;

    @FXML
    private Button updateButton;
    @FXML
    private ImageView backButton;
    serviceUtilisateur sp=new serviceUtilisateur();

    @FXML
    private TextField bannedtxt;
    @FXML
    private TextField roletxt;



    @FXML
    void active_false(SwipeEvent event) {

    }

    @FXML
    void update(MouseEvent event) {
        LocalDate localDate = datetxt.getValue();
        java.util.Date utilDate = java.util.Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        User u = new User(email.getText(),nom.getText(),prenom.getText(),telephone.getText(),cintxt.getText(),fonctiontxt.getText(),adresse.getText(),utilDate,biotxt.getText(), Arrays.asList("ROLE_USER"),Integer.parseInt(bannedtxt.getText()),Integer.parseInt(verifiedtxt.getText()));
        // Update the user information in the database
        try {
            sp.updateOne(u);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("updated");
            alert.setHeaderText(null);
            alert.setContentText("update successfully");
            alert.showAndWait();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/back/HomeAdmin.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = (Stage) updateButton.getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println(e);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    private String userEmail;

    public void setEmail(String email) {
        this.userEmail = email;
    }



    public void initialize(){

        System.out.println(userEmail);
        try {
            ResultSet resultSet=sp.selectData(userEmail);
            if(resultSet.next()){
                email.setText(resultSet.getString("email"));
                nom.setText(resultSet.getString("nom"));
                prenom.setText(resultSet.getString("prenom"));
                adresse.setText(resultSet.getString("address"));
                telephone.setText(String.valueOf(resultSet.getInt("telephone")));
                Date dateFromDB = resultSet.getDate("date");
                if (dateFromDB != null) {
                    LocalDate localDate = dateFromDB.toLocalDate();
                    datetxt.setValue(localDate);
                }
                cintxt.setText(resultSet.getString("cin"));
                biotxt.setText(resultSet.getString("bio"));
                String rolesString = resultSet.getString("roles");
                System.out.println(rolesString);
                // Transformez la chaîne des rôles en une collection
                Collection<String> roles = Arrays.asList(rolesString.split(","));

                // Vérifiez si l'utilisateur a le rôle "ROLE_USER"
                if (roles.contains("[ROLE_USER]")) {
                    roletxt.setText("User");
                } else {
                    roletxt.setText("Admin");
                }

                fonctiontxt.setText(resultSet.getString("fonction"));
                String isVerified = resultSet.getString("is_verified");

                if ("1".equals(isVerified)) {
                    verifiedtxt.setText("vérifié");
                } else {
                    verifiedtxt.setText("non vérifié");
                }

                String isBanned = resultSet.getString("is_Banned");

                if ("1".equals(isBanned)) {
                    bannedtxt.setText("désactivé");
                } else {
                    bannedtxt.setText("activé");
                }


            }else {

                System.out.println("User not found!");
            }
        }catch (SQLException e) {
            System.out.println(e);
            // Handle exception, show error message, or log the error
        }
    }
    @FXML
    void delete(MouseEvent event) {
        LocalDate localDate = datetxt.getValue();
        java.util.Date utilDate = java.util.Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());

        User u = new User(email.getText(), nom.getText(), prenom.getText(), telephone.getText(), cintxt.getText(),
                fonctiontxt.getText(), adresse.getText(), utilDate, biotxt.getText(),
                Arrays.asList(roletxt.getText()),
                Integer.parseInt(bannedtxt.getText()),
                Integer.parseInt(verifiedtxt.getText()));
        try {
            sp.deleteOne(u);
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/back/HomeAdmin.fxml"));
                Parent root = loader.load();
                Scene scene = new Scene(root);
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setScene(scene);
                stage.show();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    @FXML
    void back(MouseEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/back/HomeAdmin.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage stage = (Stage) backButton.getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }
}



