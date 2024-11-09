package com.code.fullstack_backend.dao;

import com.code.fullstack_backend.model.Filial;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FilialDAO {

    // Método para obter todas as filiais
    public List<Filial> getAllFiliais() throws SQLException {
        List<Filial> filiais = new ArrayList<>();
        String sql = "SELECT * FROM filiais";
        try (Connection connection = DatabaseConnection.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            while (resultSet.next()) {
                Filial filial = new Filial();
                filial.setId(resultSet.getLong("id"));
                filial.setNome(resultSet.getString("nome"));
                filial.setCidade(resultSet.getString("cidade"));
                filial.setEstado(resultSet.getString("estado"));
                filial.setRua(resultSet.getString("rua"));
                filial.setNumero(resultSet.getString("numero"));
                filial.setTelefone(resultSet.getString("telefone"));
                filial.setCodigoFilial(resultSet.getString("codigoFilial"));
                filial.setCnpj(resultSet.getString("cnpj"));  // Adicionando o campo cnpj
                filiais.add(filial);
            }
        }
        return filiais;
    }

    // Método para obter filial por ID
    public Filial getFilialById(Long id) throws SQLException {
        Filial filial = null;
        String sql = "SELECT * FROM filiais WHERE id = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    filial = new Filial();
                    filial.setId(resultSet.getLong("id"));
                    filial.setNome(resultSet.getString("nome"));
                    filial.setCidade(resultSet.getString("cidade"));
                    filial.setEstado(resultSet.getString("estado"));
                    filial.setRua(resultSet.getString("rua"));
                    filial.setNumero(resultSet.getString("numero"));
                    filial.setTelefone(resultSet.getString("telefone"));
                    filial.setCodigoFilial(resultSet.getString("codigoFilial"));
                    filial.setCnpj(resultSet.getString("cnpj"));  // Adicionando o campo cnpj
                }
            }
        }
        return filial;
    }

    // Método para adicionar uma nova filial
    public void addFilial(Filial filial) throws SQLException {
        String sql = "INSERT INTO filiais (nome, cidade, estado, rua, numero, telefone, codigoFilial, cnpj) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, filial.getNome());
            statement.setString(2, filial.getCidade());
            statement.setString(3, filial.getEstado());
            statement.setString(4, filial.getRua());
            statement.setString(5, filial.getNumero());
            statement.setString(6, filial.getTelefone());
            statement.setString(7, filial.getCodigoFilial());
            statement.setString(8, filial.getCnpj());  // Adicionando o campo cnpj
            statement.executeUpdate();
        }
    }

    // Método para atualizar uma filial existente
    public void updateFilial(Long id, Filial filial) throws SQLException {
        String sql = "UPDATE filiais SET nome = ?, cidade = ?, estado = ?, rua = ?, numero = ?, telefone = ?, codigoFilial = ?, cnpj = ? WHERE id = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, filial.getNome());
            statement.setString(2, filial.getCidade());
            statement.setString(3, filial.getEstado());
            statement.setString(4, filial.getRua());
            statement.setString(5, filial.getNumero());
            statement.setString(6, filial.getTelefone());
            statement.setString(7, filial.getCodigoFilial());
            statement.setString(8, filial.getCnpj());  // Atualizando o campo cnpj
            statement.setLong(9, id);
            statement.executeUpdate();
        }
    }

    // Método para excluir uma filial
    public void deleteFilial(Long id) throws SQLException {
        String sql = "DELETE FROM filiais WHERE id = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, id);
            statement.executeUpdate();
        }
    }

    // Método para verificar se o código da filial já existe
    public boolean existsByCodigoFilial(String codigoFilial) throws SQLException {
        String sql = "SELECT 1 FROM filiais WHERE codigoFilial = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, codigoFilial);
            try (ResultSet resultSet = statement.executeQuery()) {
                return resultSet.next();
            }
        }
    }
}
