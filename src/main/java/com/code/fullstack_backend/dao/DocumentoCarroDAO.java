package com.code.fullstack_backend.dao;

import com.code.fullstack_backend.model.Carro;
import com.code.fullstack_backend.model.DocumentoCarro;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.code.fullstack_backend.dao.DatabaseConnection.getConnection;

public class DocumentoCarroDAO {

    // Método para adicionar um documento de carro no banco de dados
    public void addDocumentoCarro(DocumentoCarro documentoCarro) throws SQLException {
        String sql = "INSERT INTO Documento_carro (ano_fab, chassi, modelo, placa) VALUES (?, ?, ?, ?)";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {


            stmt.setInt(1, documentoCarro.getAnoFab()); // ano_fab
            stmt.setString(2, documentoCarro.getChassi()); // chassi
            stmt.setString(3, documentoCarro.getModelo()); // modelo
            stmt.setString(4, documentoCarro.getPlaca()); // placa

            int rowsInserted = stmt.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("Documento do carro foi inserido com sucesso!");
            }
        } catch (SQLException e) {
            System.out.println("Erro ao adicionar DocumentoCarro: " + e.getMessage());
            throw e; // Re-throw para que o controller possa capturar o erro
        } catch (IllegalArgumentException e) {
            System.out.println("Erro de validação: " + e.getMessage());
            throw e; // Re-throw para que o controller possa capturar o erro
        }
    }


    public DocumentoCarro getDocumentoCarroById(Integer id) throws SQLException {
        DocumentoCarro documentoCarro = null;
        String sql = "SELECT * FROM documento_carro WHERE id = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    documentoCarro = new DocumentoCarro();
                    documentoCarro.setId(resultSet.getInt("id"));
                    documentoCarro.setAnoFab(resultSet.getInt("ano_fab"));
                    documentoCarro.setChassi(resultSet.getString("chassi"));
                    documentoCarro.setModelo(resultSet.getString("modelo"));
                    documentoCarro.setPlaca(resultSet.getString("placa"));
                }
            }
        }
        return documentoCarro;
    }


    public void updateDocumentoCarro(Integer id, DocumentoCarro documentoCarro) throws SQLException {
        String sql = "UPDATE Documento_carro SET ano_fab = ?, chassi = ?, modelo = ?, placa = ? WHERE id = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, documentoCarro.getAnoFab());
            statement.setString(2, documentoCarro.getChassi());
            statement.setString(3, documentoCarro.getModelo());
            statement.setString(4, documentoCarro.getPlaca());
            statement.setInt(5, id);

            statement.executeUpdate();
        }
    }

    public void deleteDocumentoCarro(Integer id) throws SQLException {
        String sql = "DELETE FROM documento_carro WHERE id = ?";

        try (Connection connection = getConnection(); // Obtendo a conexão
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException("Erro ao deletar o documento do carro: " + e.getMessage());
        }
    }

    public List<DocumentoCarro> getAllDocumentoCarro() throws SQLException {
        List<DocumentoCarro> documentosCarro = new ArrayList<>();
        String sql = "SELECT * FROM Documento_carro";

        try (Connection connection = getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {

            while (resultSet.next()) {
                // Criando o objeto DocumentoCarro com todos os parâmetros, incluindo o id
                DocumentoCarro documentoCarro = new DocumentoCarro(
                        resultSet.getInt("id"),           // Adicionando o id
                        resultSet.getInt("ano_fab"),
                        resultSet.getString("chassi"),
                        resultSet.getString("modelo"),
                        resultSet.getString("placa")
                );
                documentosCarro.add(documentoCarro);
            }
        }
        return documentosCarro;
    }

}
