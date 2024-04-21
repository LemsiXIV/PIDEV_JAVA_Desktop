/*package com.webandit.webuild.test;

import com.webandit.webuild.models.Commentaire;
import com.webandit.webuild.services.CommentaireService;
import com.webandit.webuild.utils.DBConnection;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class MainCommentaire {
    public static void main(String[] args) {
        DBConnection db1 = new DBConnection().getInstance();

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        try {
            Date utilDate = dateFormat.parse("12/02/2024");
            // Convert java.util.Date to java.sql.Date
            java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
            Commentaire c1 = new Commentaire(49, "contenu1", "nom1", sqlDate, 10, 5, 1);
            Commentaire c2 = new Commentaire(49, "contenu2", "nom2", sqlDate, 20, 8, 2); // Utilisez sqlDate
            CommentaireService cs1 = new CommentaireService();
            cs1.create(c1);
            cs1.update(c2);
            /* cs1.delete(50);
            List<Commentaire> l1 = cs1.read();
            l1.stream()
                    .map(Commentaire::toString)
                    .forEach(System.out::println);

        } catch (ParseException | SQLException e) {
            e.printStackTrace();
        }
    }
}
*/