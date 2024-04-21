package com.webandit.webuild.services;

import com.webandit.webuild.models.Chantier;
import com.webandit.webuild.models.Tasks;
import com.webandit.webuild.utils.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ServiceTasks implements CRUD<Tasks> {
    private Connection cnx;

    public ServiceTasks() {
        cnx = DBConnection.getInstance().getCnx();
    }

    @Override
    public void insertOne(Tasks tasks) throws SQLException {
        String req = "INSERT INTO `task`(`name`, `priority`, `status`, `description`, `due`, `id_chantier_id`) VALUES " +
                "('"+tasks.getName()+"','"+tasks.getPriority()+"',"+tasks.getStatus()+",'"+tasks.getDescription()+"','"+tasks.getDue()+"','"+tasks.getId_chantier().getId()+"')";
        Statement st = cnx.createStatement();
        st.executeUpdate(req);
        System.out.println("Task Added !");
    }

    @Override
    public void updateOne(Tasks tasks) throws SQLException {
        String sql = "UPDATE task SET name=?, priority=?, status=?, description=?, due=? WHERE id=?";
        try (PreparedStatement preparedStatement = cnx.prepareStatement(sql)) {
            preparedStatement.setString(1, tasks.getName());
            preparedStatement.setString(2, tasks.getPriority());
            preparedStatement.setInt(3, tasks.getStatus());
            preparedStatement.setString(4, tasks.getDescription());
            preparedStatement.setDate(5, tasks.getDue());
            preparedStatement.setInt(6, tasks.getId()); // Set the ID parameter


            preparedStatement.executeUpdate();
        }
    }

    @Override
    public void deleteOne(int id) throws SQLException {
        String sql = "DELETE FROM task WHERE id = ?";
        try (PreparedStatement preparedStatement = cnx.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        }
    }

    @Override
    public List<Tasks> selectAll() throws SQLException {
        List<Tasks> tasksList = new ArrayList<>();
        String req = "SELECT * FROM `task` t INNER JOIN `chantier` c ON t.id_chantier_id = c.id ";
        Statement  st = cnx.createStatement();
            ResultSet result = st.executeQuery(req);
            while (result.next()) {
                Tasks ts = new Tasks();
                ts.setId(result.getInt("t.id"));
                ts.setNomchantier(result.getString("c.nom"));
                ts.setName(result.getString("t.name"));
                ts.setPriority(result.getString("t.priority"));
                ts.setStatus(result.getInt("t.status"));
                ts.setDescription(result.getString("t.description"));
                ts.setDue(result.getDate("t.due"));
                tasksList.add(ts);
            }

        System.out.println(tasksList);
        return tasksList;
    }

    @Override
    public List<Tasks> selectListDerou() throws SQLException {
        return null;
    }
}
