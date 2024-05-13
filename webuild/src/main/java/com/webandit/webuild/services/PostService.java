package com.webandit.webuild.services;


import com.webandit.webuild.models.Post;


import com.webandit.webuild.utils.DBConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PostService implements ICRUD<Post> {
    private Connection cnx;

    public PostService() {
        cnx = DBConnection.getInstance().getCnx();
    }

    @Override
    public void create(Post post) throws SQLException, SQLIntegrityConstraintViolationException {
        String sql = "INSERT INTO Post (titre, description, auteur, date, img,user_id) VALUES (?, ?, ?, ? ,?,?)";
        PreparedStatement statement = cnx.prepareStatement(sql);

        statement.setString(1, post.getTitre());
        statement.setString(2, post.getDescription());
        statement.setString(3, post.getAuteur());
        statement.setDate(4, new java.sql.Date(post.getDate().getTime()));
        statement.setString(5, post.getImg());
        statement.setInt(6, post.getIdclient());

        statement.executeUpdate();
    }

    @Override
    public void update(Post post) throws SQLException {
        String sql = "UPDATE Post SET titre = ?, description = ?, auteur = ?, date = ? WHERE id = ?";
        PreparedStatement ps = null;
        try {
            ps = cnx.prepareStatement(sql);
            ps.setString(1, post.getTitre());
            ps.setString(2, post.getDescription());
            ps.setString(3, post.getAuteur());
            ps.setDate(4, new java.sql.Date(post.getDate().getTime()));
            ps.setInt(5, post.getId());
            ps.executeUpdate();
        } finally {
            if (ps != null) {
                ps.close();
            }
        }
    }
    public List<Integer> getAllPostIds() throws SQLException {
        List<Integer> postIds = new ArrayList<>();

        String sql = "SELECT id FROM Post";
        try (PreparedStatement statement = cnx.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                int postId = resultSet.getInt("id");
                postIds.add(postId);
            }
        }

        return postIds;
    }

    @Override
    public void delete(int id) throws SQLException {
        String sql = "DELETE FROM Post WHERE id = ?";
        try (PreparedStatement preparedStatement = cnx.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        }
    }

    @Override
    public List<Post> read() throws SQLException {
        String sql = "SELECT * FROM Post";
        Statement statement = cnx.createStatement();
        ResultSet rs = statement.executeQuery(sql);
        List<Post> posts = new ArrayList<>();
        while (rs.next()) {
            Post post = new Post();
            post.setId(rs.getInt("id"));
            post.setTitre(rs.getString("titre"));
            post.setDescription(rs.getString("description"));
            post.setAuteur(rs.getString("auteur"));
            post.setDate(rs.getDate("date"));
            post.setImg(rs.getString("img"));
            posts.add(post);
        }
        return posts;
    }

    public Post readPost(int postId) throws SQLException {
        String sql = "SELECT * FROM Post WHERE id = ?";
        PreparedStatement statement = cnx.prepareStatement(sql);
        statement.setInt(1, postId);

        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()) {
            Post post = new Post();
            post.setId(resultSet.getInt("id"));
            post.setTitre(resultSet.getString("titre"));
            post.setDescription(resultSet.getString("description"));
            post.setAuteur(resultSet.getString("auteur"));
            post.setDate(resultSet.getDate("date"));

            return post;
        } else {
            // Post with the given ID not found
            return null;
        }
    }

    public List<Post> rechercherPosts(String searchTerm) throws SQLException {
        List<Post> searchResults = new ArrayList<>();
        String query = "SELECT id, titre, description, img, date FROM post WHERE titre LIKE ? OR description LIKE ?";
        try (PreparedStatement preparedStatement = cnx.prepareStatement(query)) {
            String searchPattern = "%" + searchTerm + "%";
            preparedStatement.setString(1, searchPattern);
            preparedStatement.setString(2, searchPattern);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    Post post = new Post();
                    post.setId(resultSet.getInt("id"));
                    post.setTitre(resultSet.getString("titre"));
                    post.setDescription(resultSet.getString("description"));
                    post.setImg(resultSet.getString("img")); // Set the image URL
                    post.setDate(resultSet.getDate("date")); // Set the date
                    searchResults.add(post);
                }
            }
        }
        return searchResults;
    }




    public java.util.Date GetCurentDate() throws SQLException {
        String sql = "SELECT CURRENT_DATE";
        Statement statement = cnx.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);

        java.sql.Date currentDate = null;
        if (resultSet.next()) {
            currentDate = resultSet.getDate(1);
        }
        return currentDate;
    }
}
