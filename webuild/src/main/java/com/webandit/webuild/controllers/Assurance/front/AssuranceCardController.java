package com.webandit.webuild.controllers.Assurance.front;



import javafx.fxml.FXML;
import javafx.scene.control.Label;
import com.webandit.webuild.models.Assurance;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class AssuranceCardController {
    @FXML
    private Label titre;

    @FXML
    private Label description;

    @FXML
    private Label conditions;

    @FXML
    private Label franchise;

    @FXML
    private Label prime;


    private Assurance assurance;
    @FXML
    private ImageView img;

    public void setData(Assurance assurance) {
        this.assurance = assurance;
        // Set image
img.setImage(new Image(assurance.getImage()));
        titre.setText(assurance.getNom());
        description.setText(assurance.getDescription());
        conditions.setText("Conditions: " + assurance.getCondition_age() + ", " + assurance.getCondition_medicale() + ", " + assurance.getCondition_financiere());
        franchise.setText("Franchise: " + assurance.getFranchise());
        prime.setText("Prime: " + assurance.getPrime());
    }
}
