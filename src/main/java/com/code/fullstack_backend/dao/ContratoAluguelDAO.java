package com.code.fullstack_backend.dao;

import com.code.fullstack_backend.model.ContratoAluguel;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ContratoAluguelDAO {

    // Verifica se o contrato já existe pelo ID
    public boolean existsById(Long id) throws SQLException {
        String sql = "SELECT COUNT(*) FROM contrato_aluguel WHERE id = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                return resultSet.next() && resultSet.getInt(1) > 0;
            }
        }
    }

    // Recupera todos os contratos
    public List<ContratoAluguel> getAllContratos() throws SQLException {
        List<ContratoAluguel> contratos = new ArrayList<>();
        String sql = "SELECT * FROM contrato_aluguel";
        try (Connection connection = DatabaseConnection.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            while (resultSet.next()) {
                ContratoAluguel contrato = new ContratoAluguel();
                contrato.setId(resultSet.getInt("id"));
                contrato.setData_inicio(resultSet.getDate("data_inicio").toLocalDate());
                contrato.setData_fim(resultSet.getDate("data_fim").toLocalDate());
                contrato.setCarro_id(resultSet.getInt("carro_id"));
                contrato.setCliente_id(resultSet.getInt("cliente_id"));
                contrato.setValor_pago(resultSet.getDouble("valor_pago"));
                contratos.add(contrato);
            }
        }
        return contratos;
    }

    // Recupera um contrato por ID
    public ContratoAluguel getContratoById(Long id) throws SQLException {
        ContratoAluguel contrato = null;
        String sql = "SELECT * FROM contrato_aluguel WHERE id = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    contrato = new ContratoAluguel();
                    contrato.setId(resultSet.getInt("id"));
                    contrato.setData_inicio(resultSet.getDate("data_inicio").toLocalDate());
                    contrato.setData_fim(resultSet.getDate("data_fim").toLocalDate());
                    contrato.setCarro_id(resultSet.getInt("carro_id"));
                    contrato.setCliente_id(resultSet.getInt("cliente_id"));
                    contrato.setValor_pago(resultSet.getDouble("valor_pago"));
                }
            }
        }
        return contrato;
    }

    public static void addContrato(ContratoAluguel contrato) throws SQLException {
        ClienteDAO clienteDAO = new ClienteDAO();
        CarroDAO carroDAO = new CarroDAO();

        if (!clienteDAO.existsClienteById(contrato.getCliente_id())) {
            throw new SQLException("Erro: Cliente com ID " + contrato.getCliente_id() + " não existe.");
        }
        if (!carroDAO.existsCarroById(contrato.getCarro_id())) {
            throw new SQLException("Erro: Carro com ID " + contrato.getCarro_id() + " não existe.");
        }

        String sql = "INSERT INTO contrato_aluguel (data_inicio, data_fim, valor_pago, Cliente_id, Carro_id) " +
                "VALUES (?, ?, ?, ?, ?)";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setDate(1, Date.valueOf(contrato.getData_inicio()));
            preparedStatement.setDate(2, Date.valueOf(contrato.getData_fim()));
            preparedStatement.setDouble(3, contrato.getValor_pago());
            preparedStatement.setInt(4, contrato.getCliente_id());
            preparedStatement.setInt(5, contrato.getCarro_id());

            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected == 0) {
                throw new SQLException("Erro ao salvar contrato. Nenhuma linha foi afetada.");
            }
        } catch (SQLException e) {
            throw new SQLException("Erro ao salvar contrato", e);
        }
    }





    // Atualiza um contrato existente
    public void updateContrato(Long id, ContratoAluguel contrato) throws SQLException {
        if (!existsById(id)) {
            throw new SQLException("Contrato não encontrado para atualização.");
        }

        String sql = "UPDATE contrato_aluguel SET data_inicio = ?, data_fim = ?, carro_id = ?, cliente_id = ?, valor_pago = ? WHERE id = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setDate(1, Date.valueOf(contrato.getData_inicio()));
            statement.setDate(2, Date.valueOf(contrato.getData_fim()));
            statement.setInt(3, contrato.getCarro_id());
            statement.setInt(4, contrato.getCliente_id());
            statement.setDouble(5, contrato.getValor_pago());
            statement.setLong(6, id);

            int rowsAffected = statement.executeUpdate();
            if (rowsAffected == 0) {
                throw new SQLException("Falha ao atualizar o contrato. Nenhuma linha foi afetada.");
            }
        } catch (SQLException e) {
            throw new SQLException("Erro ao atualizar contrato. Verifique os detalhes e tente novamente.", e);
        }
    }
}
