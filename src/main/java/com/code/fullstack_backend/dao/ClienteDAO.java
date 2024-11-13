package com.code.fullstack_backend.dao;

import com.code.fullstack_backend.model.Cliente;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClienteDAO {

    public List<Cliente> getAllCliente() throws SQLException {
        List<Cliente> clienteList = new ArrayList<>();
        String sql = "SELECT * FROM Cliente";
        try (Connection connection = DatabaseConnection.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            while (resultSet.next()) {
                Cliente cliente = new Cliente();
                cliente.setId(resultSet.getInt("id")); // Nova chave primária
                cliente.setRg(resultSet.getInt("rg"));
                cliente.setNome(resultSet.getString("nome"));
                cliente.setEmail(resultSet.getString("email"));
                cliente.setTelefone(resultSet.getString("telefone"));
                cliente.setRua(resultSet.getString("rua"));
                cliente.setBairro(resultSet.getString("bairro"));
                cliente.setNumero(resultSet.getInt("numero"));
                clienteList.add(cliente);
            }
        }
        return clienteList;
    }


    public Cliente getClienteById(Integer id) throws SQLException {
        Cliente cliente = null;
        String sql = "SELECT * FROM Cliente WHERE id = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    cliente = new Cliente();
                    cliente.setId(resultSet.getInt("id"));
                    cliente.setRg(resultSet.getInt("rg"));
                    cliente.setNome(resultSet.getString("nome"));
                    cliente.setEmail(resultSet.getString("email"));
                    cliente.setTelefone(resultSet.getString("telefone"));
                    cliente.setRua(resultSet.getString("rua"));
                    cliente.setBairro(resultSet.getString("bairro"));
                    cliente.setNumero(resultSet.getInt("numero"));
                }
            }
        }
        return cliente;
    }

    public boolean existsClienteById(int id) throws SQLException {
        String sql = "SELECT COUNT(*) FROM Cliente WHERE id = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                resultSet.next();
                return resultSet.getInt(1) > 0;
            }
        }
    }


    public void addCliente(Cliente cliente) throws SQLException {
        String sql = "INSERT INTO Cliente (rg, nome, email, telefone, rua, bairro, numero) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            statement.setInt(1, cliente.getRg());
            statement.setString(2, cliente.getNome());
            statement.setString(3, cliente.getEmail());
            statement.setString(4, cliente.getTelefone());
            statement.setString(5, cliente.getRua());
            statement.setString(6, cliente.getBairro());
            statement.setInt(7, cliente.getNumero());

            statement.executeUpdate();

            // Obtém o ID gerado automaticamente e define no objeto
            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    cliente.setId(generatedKeys.getInt(1));
                }
            }
        }
    }




    public void updateCliente(Integer id, Cliente cliente) throws SQLException {
        String sql = "UPDATE Cliente SET rg = ?, nome = ?, email = ?, telefone = ?, rua = ?, bairro = ?, numero = ? WHERE id = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, cliente.getRg());
            statement.setString(2, cliente.getNome());
            statement.setString(3, cliente.getEmail());
            statement.setString(4, cliente.getTelefone());
            statement.setString(5, cliente.getRua());
            statement.setString(6, cliente.getBairro());
            statement.setInt(7, cliente.getNumero());
            statement.setInt(8, id);

            statement.executeUpdate();
        }
    }


    public void deleteCliente(Integer id) throws SQLException {
        String sql = "DELETE FROM Cliente WHERE id = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            statement.executeUpdate();
        }
    }


    public boolean existsByRg(Integer rg) throws SQLException {
        String sql = "SELECT 1 FROM Cliente WHERE rg = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, rg);
            try (ResultSet resultSet = statement.executeQuery()) {
                return resultSet.next();
            }
        }
    }
}