package com.code.fullstack_backend.dao;

import com.code.fullstack_backend.model.Filial;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.code.fullstack_backend.dao.DatabaseConnection.getConnection;

public class FilialDAO {

    // Método para obter todas as filial
    public List<Filial> getAllFilial() throws SQLException {
        List<Filial> filialList = new ArrayList<>();
        String sql = "SELECT * FROM filial";
        try (Connection connection = getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            while (resultSet.next()) {
                Filial filial = new Filial();
                filial.setId(Integer.valueOf(resultSet.getString("id")));
                filial.setNome(resultSet.getString("nome"));
                filial.setCidade(resultSet.getString("cidade"));
                filial.setEstado(resultSet.getString("estado"));
                filial.setRua(resultSet.getString("rua"));
                filial.setNumero(resultSet.getString("numero"));
                filial.setTelefone(resultSet.getString("telefone"));
                filial.setCnpj(resultSet.getString("cnpj"));
                filialList.add(filial);
            }
        }
        return filialList;
    }


    // Método para obter filial por id
    public Filial getFilialById(String id) throws SQLException {
        Filial filial = null;
        String sql = "SELECT * FROM filial WHERE id = ?";
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    filial = new Filial();
                    filial.setId(Integer.valueOf(resultSet.getString("id")));
                    filial.setNome(resultSet.getString("nome"));
                    filial.setCidade(resultSet.getString("cidade"));
                    filial.setEstado(resultSet.getString("estado"));
                    filial.setRua(resultSet.getString("rua"));
                    filial.setNumero(resultSet.getString("numero"));
                    filial.setTelefone(resultSet.getString("telefone"));
                    filial.setCnpj(resultSet.getString("cnpj"));
                }
            }
        }
        return filial;
    }

    // Método para adicionar uma nova filial
    public void addFilial(Filial filial) throws SQLException {
        String sql = "INSERT INTO filial ( nome, cidade, estado, rua, numero, telefone, cnpj) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, filial.getNome());
            statement.setString(2, filial.getCidade());
            statement.setString(3, filial.getEstado());
            statement.setString(4, filial.getRua());
            statement.setString(5, filial.getNumero());
            statement.setString(6, filial.getTelefone());
            statement.setString(7, filial.getCnpj());
            statement.executeUpdate();
        }
    }

    public boolean updateFilial(String id, Filial filial) throws SQLException {
        String sql = "UPDATE filial SET nome = ?, cidade = ?, estado = ?, rua = ?, numero = ?, telefone = ?, cnpj = ? WHERE id = ?";
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, filial.getNome());
            statement.setString(2, filial.getCidade());
            statement.setString(3, filial.getEstado());
            statement.setString(4, filial.getRua());
            statement.setString(5, filial.getNumero());
            statement.setString(6, filial.getTelefone());
            statement.setString(7, filial.getCnpj());
            statement.setString(8, id);

            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0; // Retorna true se alguma linha foi afetada
        }
    }

    // Método para excluir uma filial
    public void deleteFilial(String id) throws SQLException {
        String sql = "DELETE FROM filial WHERE id = ?";
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, id);
            statement.executeUpdate();
        }
    }

    // Método para verificar se o código da filial já existe
    public boolean existsById(String id) throws SQLException {
        String sql = "SELECT 1 FROM filial WHERE id = ?";
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                return resultSet.next();
            }
        }
    }



}
