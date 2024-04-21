package com.webandit.webuild.controllers;

import com.webandit.webuild.models.Assurance;
import com.webandit.webuild.utils.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

public class ShowAssurance implements Initializable {

    @FXML
    private ListView<Assurance> showlist ;
    private Connection cnx;
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


    public void showAssur() {
        ObservableList<Assurance> list = getAssurances();
        showlist.setItems(list);

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        showAssur();
    }
}
