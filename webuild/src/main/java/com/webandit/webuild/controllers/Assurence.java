package com.webandit.webuild.controllers;

import com.webandit.webuild.models.Assurance;
import com.webandit.webuild.services.ServiceAssurance;
import com.webandit.webuild.utils.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class Assurence implements Initializable {
    private Connection cnx;
    int id = 0;
    @FXML
    private Button add;
    @FXML
    private Button delAssu;
    @FXML
    private Button UpAssu;
    @FXML
    private Button addAssu;
    @FXML
    private Button deml;
    @FXML
    private TableColumn<Assurance, String> colage;

    @FXML
    private TableColumn<Assurance, String> coldesc;

    @FXML
    private TableColumn<Assurance, String> colfinanaciere;

    @FXML
    private TableColumn<Assurance, Integer> colfranchise;

    @FXML
    private TableColumn<Assurance, Integer> colid;

    @FXML
    private TableColumn<Assurance, String> colimg;

    @FXML
    private TableColumn<Assurance, String> colmedicale;

    @FXML
    private TableColumn<Assurance, String> colnom;

    @FXML
    private TableColumn<Assurance, Integer> colprime;


    @FXML
    private TableView<Assurance> tableAssu;



    @FXML
    private TextField txtCondition_financiere;

    @FXML
    private TextField txtCondition_medicale;

    @FXML
    private TextArea txtDescription;

    @FXML
    private TextField txtFranchise;

    @FXML
    private TextField txtImage;

    @FXML
    private TextField txtNom;

    @FXML
    private TextField txtPrime;

    @FXML
    private TextField xtCondition_age;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

       txtFranchise.setTextFormatter(new TextFormatter<>(change -> {
            String newText = change.getControlNewText();
            if (newText.matches("\\d*")) { // Vérifier si le nouveau texte contient uniquement des chiffres
                return change; // Autoriser le changement
            }
            return null; // Bloquer le changement
        }));
        txtPrime.setTextFormatter(new TextFormatter<>(change -> {
            String newText = change.getControlNewText();
            if (newText.matches("\\d*")) { // Vérifier si le nouveau texte contient uniquement des chiffres
                return change; // Autoriser le changement
            }
            return null; // Bloquer le changement
        }));
       showAssu();
    }

    public ObservableList<Assurance> getAssurances() {
        ObservableList<Assurance> assurances = FXCollections.observableArrayList();
        String query = "SELECT * FROM assurance";
        cnx = DBConnection.getInstance().getCnx();
        try {
            Statement st = cnx.createStatement();
            ResultSet rs = st.executeQuery(query);
            while (rs.next()) {
                Assurance a = new Assurance();
                a.setId(rs.getInt("id"));
                a.setNom(rs.getString("nom"));
                a.setDescription(rs.getString("description"));
                a.setImage(rs.getString("image"));
                a.setCondition_age(rs.getString("condition_age"));
                a.setCondition_medicale(rs.getString("condition_medicale"));
                a.setCondition_financiere(rs.getString("condition_financiere"));
                a.setFranchise(rs.getInt("franchise"));
                a.setPrime(rs.getInt("prime"));
                assurances.add(a); // Ajouter l'assurance à la liste

                System.out.println("Assurance récupérée : " + a);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return assurances;
    }


    public void showAssu() {
        ObservableList<Assurance> list = getAssurances();
        tableAssu.setItems(list);

        colid.setCellValueFactory(new PropertyValueFactory<Assurance, Integer>("id"));
        colnom.setCellValueFactory(new PropertyValueFactory<Assurance, String>("nom"));
        coldesc.setCellValueFactory(new PropertyValueFactory<Assurance, String>("description"));
        colimg.setCellValueFactory(new PropertyValueFactory<Assurance, String>("image"));
        colage.setCellValueFactory(new PropertyValueFactory<Assurance, String>("condition_age"));
        colmedicale.setCellValueFactory(new PropertyValueFactory<Assurance, String>("condition_medicale"));
        colfinanaciere.setCellValueFactory(new PropertyValueFactory<Assurance, String>("condition_financiere"));
        colfranchise.setCellValueFactory(new PropertyValueFactory<Assurance, Integer>("franchise"));
        colprime.setCellValueFactory(new PropertyValueFactory<Assurance, Integer>("prime"));
    }

    @FXML
    void AddAssurance(ActionEvent event) {
        String nom = txtNom.getText();
        String description = txtDescription.getText();
        String image = txtImage.getText();
        String conditionAge = xtCondition_age.getText();
        String conditionMedicale = txtCondition_medicale.getText();
        String conditionFinanciere = txtCondition_financiere.getText();
        int franchise = Integer.parseInt(txtFranchise.getText());
        int prime = Integer.parseInt(txtPrime.getText());

        Assurance newAssurance = new Assurance();
        newAssurance.setNom(nom);
        newAssurance.setDescription(description);
        newAssurance.setImage(image);
        newAssurance.setCondition_age(conditionAge);
        newAssurance.setCondition_medicale(conditionMedicale);
        newAssurance.setCondition_financiere(conditionFinanciere);
        newAssurance.setFranchise(franchise);
        newAssurance.setPrime(prime);

        try {
            ServiceAssurance serviceAssurance = new ServiceAssurance();
            // Utilisez le service pour ajouter l'assurance
            serviceAssurance.insertOne(newAssurance);
            System.out.println("Assurance ajoutée avec succès !");
            showAssu();
            // Effacez les champs de texte après l'ajout
            clearFields();
        } catch (SQLException e) {
            e.printStackTrace();
            // Gérez l'erreur de manière appropriée (par exemple, affichez un message d'erreur à l'utilisateur)
        }
    }

    // Méthode pour effacer les champs de texte
    private void clearFields() {
        txtNom.clear();
        txtDescription.clear();
        txtImage.clear();
        xtCondition_age.clear();
        txtCondition_medicale.clear();
        txtCondition_financiere.clear();
        txtFranchise.clear();
        txtPrime.clear();
    }

    @FXML
    void UpdateAssurance(ActionEvent event) {
        String query = "UPDATE assurance SET nom = ?, description = ?, image = ?, "
                + "condition_age = ?, condition_medicale = ?, condition_financiere = ?, "
                + "franchise = ?, prime = ? WHERE id = ?";
        cnx = DBConnection.getInstance().getCnx();

        try (PreparedStatement pstmt = cnx.prepareStatement(query)) {
            pstmt.setString(1, txtNom.getText());
            pstmt.setString(2, txtDescription.getText());
            pstmt.setString(3, txtImage.getText());
            pstmt.setString(4, xtCondition_age.getText());
            pstmt.setString(5, txtCondition_medicale.getText());
            pstmt.setString(6, txtCondition_financiere.getText());
            pstmt.setInt(7, Integer.parseInt(txtFranchise.getText()));
            pstmt.setInt(8, Integer.parseInt(txtPrime.getText()));
            pstmt.setInt(9, id);


            pstmt.executeUpdate();
            showAssu();
            clearFields();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @FXML
    void getData(MouseEvent event) {
        Assurance assurance = tableAssu.getSelectionModel().getSelectedItem();
        id = assurance.getId();
        txtNom.setText(assurance.getNom());
        txtDescription.setText(assurance.getDescription());
        txtImage.setText(assurance.getImage());
        xtCondition_age.setText(assurance.getCondition_age());
        txtCondition_medicale.setText(assurance.getCondition_medicale());
        txtCondition_financiere.setText(assurance.getCondition_financiere());
        txtFranchise.setText(String.valueOf(assurance.getFranchise()));
        txtPrime.setText(String.valueOf(assurance.getPrime()));

    }

    @FXML
    void DeleteAssurance(ActionEvent event) {
        String query = "DELETE FROM assurance WHERE id = ?";

        try (PreparedStatement pstmt = cnx.prepareStatement(query)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
            showAssu();
            clearFields();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
    @FXML
    void GoToAdd(ActionEvent event) throws IOException {
        Parent tableViewParent = FXMLLoader.load(getClass().getResource("/fxml/addAssu.fxml"));
        Scene tableViewScene = new Scene(tableViewParent);
        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
        window.setScene(tableViewScene);
        window.show();
    }
    @FXML
    void demlist(ActionEvent event)throws IOException {
        Parent tableViewParent = FXMLLoader.load(getClass().getResource("/fxml/Backdemande.fxml"));
        Scene tableViewScene = new Scene(tableViewParent);
        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
        window.setScene(tableViewScene);
        window.show();
    }


    }


