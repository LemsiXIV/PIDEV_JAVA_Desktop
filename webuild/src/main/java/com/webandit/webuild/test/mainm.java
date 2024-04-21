package com.webandit.webuild.test;
import com.webandit.webuild.services.PostService;
import com.webandit.webuild.utils.DBConnection;
import com.webandit.webuild.models.Post;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class mainm {
    public static void main(String[] args) {
        DBConnection db1 = new DBConnection().getInstance();

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        try {
            Date utilDate = dateFormat.parse("12/02/2024");
            // Convert java.util.Date to java.sql.Date
            java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
            Post p1 = new Post("ranya", "des1", "auteur1", sqlDate);
            Post p2 = new Post(49, "lemsi", "des0", "auteur0", sqlDate); // Use sqlDate// Use sqlDate
            PostService ps1 = new PostService();
            ps1.create(p1);
            ps1.update(p2);
            /* ps1.delete(50);*/
            List<Post> l1 = ps1.read();
            l1.stream()
                    .map(Post::toString)
                    .forEach(System.out::println);

        } catch (ParseException | SQLException e) {
            e.printStackTrace();
        }
    }
}
