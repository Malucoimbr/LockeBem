package com.code.fullstack_backend.dao;

import com.code.fullstack_backend.model.Seguro;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SeguroDAO {

    // Método para listar todos os seguros
    public List<Seguro> getAllSeguro() throws SQLException {
        List<Seguro> seguroList = new ArrayList<>();
        String sql = "SELECT * FROM Seguro";
        try (Connection connection = DatabaseConnection.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            while (resultSet.next()) {
                Seguro seguro = new Seguro();
                seguro.setId(resultSet.getInt("id"));
                seguro.setCobertura(resultSet.getString("cobertura"));
                seguroList.add(seguro);
            }
        }
        return seguroList;
    }

    // Método para adicionar um novo seguro
    public void addSeguro(Seguro seguro) throws SQLException {
        String sql = "INSERT INTO Seguro (cobertura) VALUES (?)";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, seguro.getCobertura());
            statement.executeUpdate();

            // Obtém o ID gerado automaticamente
            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    seguro.setId(generatedKeys.getInt(1));
                }
            }
        }
    }

    // Método para deletar um seguro por ID
    public void deleteSeguro(Integer id) throws SQLException {
        String sql = "DELETE FROM Seguro WHERE id = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            statement.executeUpdate();
        }
    }

    public Seguro findById(int id) {
        String sql = "SELECT * FROM Seguro WHERE id = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                // Se encontrar o seguro, cria o objeto e retorna
                Seguro seguro = new Seguro();
                seguro.setId(resultSet.getInt("id"));
                seguro.setCobertura(resultSet.getString("cobertura"));
                return seguro;
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Lidar com exceções adequadas
        }
        return null; // Retorna null se o seguro não for encontrado
    }

    public boolean updateSeguro(Seguro seguro) {
        String sql = "UPDATE Seguro SET cobertura = ? WHERE id = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, seguro.getCobertura());
            statement.setInt(2, seguro.getId());

            int rowsUpdated = statement.executeUpdate();
            return rowsUpdated > 0;  // Retorna true se a atualização foi bem-sucedida
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
