package com.webandit.webuild.services;

import com.webandit.webuild.models.Commentaire;
import com.webandit.webuild.models.Post;
import com.webandit.webuild.utils.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CommentaireService implements CRUD<Commentaire> {
    private Connection cnx;

    public CommentaireService() {
        cnx = DBConnection.getInstance().getCnx();
    }

    @Override
    public void insertOne(Commentaire commentaire) throws SQLException {
        String req = "INSERT INTO commentaire (contenu, nom, datecreation, nbrlikes, nbrdislikes, rate, post_id) VALUES (?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement ps = cnx.prepareStatement(req);

        ps.setString(1, commentaire.getContenu());
        ps.setString(2, commentaire.getNom());
        ps.setDate(3, commentaire.getDateCreation());
        ps.setInt(4, commentaire.getNbrLikes());
        ps.setInt(5, commentaire.getNbrDislikes());
        ps.setInt(6, commentaire.getRate());
        ps.setInt(7, commentaire.getPost_id());

        ps.executeUpdate();
        System.out.println("Commentaire ajouté !");
    }

    @Override
    public void updateOne(Commentaire commentaire) throws SQLException {
        String req = "UPDATE commentaire SET contenu=?, nom=?, datecreation=?, nbrlikes=?, nbrdislikes=?, rate=? WHERE id=?";
        PreparedStatement ps = cnx.prepareStatement(req);

        ps.setString(1, commentaire.getContenu());
        ps.setString(2, commentaire.getNom());
        ps.setDate(3, commentaire.getDateCreation());
        ps.setInt(4, commentaire.getNbrLikes());
        ps.setInt(5, commentaire.getNbrDislikes());
        ps.setInt(6, commentaire.getRate());
        ps.setInt(7, commentaire.getId());

        ps.executeUpdate();
        System.out.println("Commentaire mis à jour !");
    }

    @Override
    public void deleteOne(int id) throws SQLException {
        String req = "DELETE FROM commentaire WHERE id=?";
        PreparedStatement ps = cnx.prepareStatement(req);
        ps.setInt(1, id);
        ps.executeUpdate();
        System.out.println("Commentaire supprimé !");
    }

    @Override
    public List<Commentaire> selectAll() throws SQLException {
        List<Commentaire> commentaires = new ArrayList<>();
        String req = "SELECT * FROM commentaire";
        Statement st = cnx.createStatement();
        ResultSet rs = st.executeQuery(req);

        while (rs.next()) {
            Commentaire commentaire = new Commentaire();
            commentaire.setId(rs.getInt("id"));
            commentaire.setContenu(rs.getString("contenu"));
            commentaire.setNom(rs.getString("nom"));
            commentaire.setDateCreation(rs.getDate("datecreation"));
            commentaire.setNbrLikes(rs.getInt("nbrlikes"));
            commentaire.setNbrDislikes(rs.getInt("nbrdislikes"));
            commentaire.setRate(rs.getInt("rate"));
            commentaire.setPost_id(rs.getInt("post_id"));
            commentaires.add(commentaire);
        }

        return commentaires;
    }
}
