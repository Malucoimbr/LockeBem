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

    public Cliente getClienteByRg(Integer rg) throws SQLException {
        Cliente cliente = null;
        String sql = "SELECT * FROM Cliente WHERE rg = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, rg);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    cliente = new Cliente();
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

    public void addCliente(Cliente cliente) throws SQLException {
        // Verificar se o RG já existe
        if (existsByRg(cliente.getRg())) {
            throw new SQLException("RG já existe no banco de dados!"); // Lançar exceção se já existir
        }

        String sql = "INSERT INTO Cliente (rg, nome, email, telefone, rua, bairro, numero) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, cliente.getRg());
            statement.setString(2, cliente.getNome());
            statement.setString(3, cliente.getEmail());
            statement.setString(4, cliente.getTelefone());
            statement.setString(5, cliente.getRua());
            statement.setString(6, cliente.getBairro());
            statement.setInt(7, cliente.getNumero());

            statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Erro ao adicionar cliente: " + e.getMessage());
            throw e;  // Propaga a exceção para o controlador
        }
    }



    public void updateCliente(Integer rg, Cliente cliente) throws SQLException {
        // Verifica se o cliente existe
        if (!existsByRg(rg)) {
            System.out.println("Cliente com RG " + rg + " não encontrado.");
            return; // Não tenta atualizar se não encontrar o cliente
        }

        String sql = "UPDATE Cliente SET nome = ?, email = ?, telefone = ?, rua = ?, bairro = ?, numero = ? WHERE rg = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            // Preenche os parâmetros da query
            statement.setString(1, cliente.getNome());
            statement.setString(2, cliente.getEmail());
            statement.setString(3, cliente.getTelefone());
            statement.setString(4, cliente.getRua());
            statement.setString(5, cliente.getBairro());
            statement.setInt(6, cliente.getNumero());
            statement.setInt(7, rg);

            // Executa a atualização e captura o número de registros afetados
            int rowsUpdated = statement.executeUpdate();

            // Exibe uma mensagem dependendo do número de registros afetados
            if (rowsUpdated > 0) {
                System.out.println("Cliente com RG " + rg + " atualizado com sucesso.");
            } else {
                System.out.println("Nenhuma linha foi atualizada para o RG: " + rg);
            }
        }
    }


    public void deleteCliente(Integer rg) throws SQLException {
        String sql = "DELETE FROM Cliente WHERE rg = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, rg);
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
