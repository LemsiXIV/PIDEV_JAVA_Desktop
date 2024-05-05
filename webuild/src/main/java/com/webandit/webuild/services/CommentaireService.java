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

    public List<Integer> getAvailablePostIds() {
        List<Integer> availableIds = new ArrayList<>();
        String query = "SELECT id FROM Post"; // Modifier la requête SQL en fonction de votre structure de base de données
        try (PreparedStatement pst = cnx.prepareStatement(query);
             ResultSet rs = pst.executeQuery()) {
            while (rs.next()) {
                availableIds.add(rs.getInt("post_id"));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la récupération des ID de post depuis la base de données", e);
        }
        return availableIds;
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
    public List<Commentaire> rechercherCommentaires(String searchTerm) throws SQLException {
        List<Commentaire> searchResults = new ArrayList<>();
        String query = "SELECT * FROM commentaire WHERE contenu LIKE ?";
        try (PreparedStatement preparedStatement = cnx.prepareStatement(query)) {
            String searchPattern = "%" + searchTerm + "%";
            preparedStatement.setString(1, searchPattern);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    Commentaire commentaire = new Commentaire();
                    commentaire.setId(resultSet.getInt("id"));

                    commentaire.setContenu(resultSet.getString("contenu"));
                    searchResults.add(commentaire);
                }
            }
        }
        return searchResults;
    }
    public List<Commentaire> getCommentsPostId(int post_id) throws SQLException {
        List<Commentaire> commentList = new ArrayList<>();
        String query = "SELECT * FROM commentaire WHERE post_id = ?"; // Remplacez comments et idblog par les noms réels de vos tables et colonnes

        try (PreparedStatement statement = cnx.prepareStatement(query)) {
            statement.setInt(1, post_id);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int idComment = resultSet.getInt("id");
                String contentComment = resultSet.getString("contenu");

                // Créez un objet Comments à partir des données récupérées
                Commentaire comment = new Commentaire (idComment, contentComment,post_id);
                commentList.add(comment);
            }
        }

        return commentList;
    }
    public List<Commentaire> selectAllv2(int id) throws SQLException {
        List<Commentaire> commentaires = new ArrayList<>();
        String req = "SELECT * FROM commentaire WHERE post_id = ?";

        try (PreparedStatement statement = cnx.prepareStatement(req)) {
            statement.setInt(1, id);
            ResultSet rs = statement.executeQuery();

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
        }
        return commentaires;
    }
}
