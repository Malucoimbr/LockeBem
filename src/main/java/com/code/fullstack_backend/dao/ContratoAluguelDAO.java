package com.code.fullstack_backend.dao;

import com.code.fullstack_backend.model.ContratoAluguel;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;
import static com.code.fullstack_backend.dao.DatabaseConnection.getConnection;

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
                contrato.setDataInicio(resultSet.getDate("data_inicio").toLocalDate());
                contrato.setDataFim(resultSet.getDate("data_fim").toLocalDate());
                contrato.setCarroId(resultSet.getInt("carro_id"));
                contrato.setClienteId(resultSet.getInt("Cliente_id"));
                contrato.setFuncionarioId(resultSet.getInt("Funcionario_id"));
                contrato.setSeguroId(resultSet.getInt("Seguro_id"));
                contrato.setValorPago(resultSet.getDouble("valor_pago"));
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
                    contrato.setDataInicio(resultSet.getDate("data_inicio").toLocalDate());
                    contrato.setDataFim(resultSet.getDate("data_fim").toLocalDate());
                    contrato.setCarroId(resultSet.getInt("carro_id"));
                    contrato.setClienteId(resultSet.getInt("Cliente_id"));
                    contrato.setFuncionarioId(resultSet.getInt("Funcionario_id"));
                    contrato.setSeguroId(resultSet.getInt("Seguro_id"));
                    contrato.setValorPago(resultSet.getDouble("valor_pago"));
                }
            }
        }
        return contrato;
    }

    // Adiciona um novo contrato
    public static void addContrato(ContratoAluguel contrato) throws SQLException {
        ClienteDAO clienteDAO = new ClienteDAO();
        CarroDAO carroDAO = new CarroDAO();
        if (!clienteDAO.existsClienteById(contrato.getClienteId())) {
            throw new SQLException("Erro: Cliente com ID " + contrato.getClienteId() + " não existe.");
        }

        String sql = "INSERT INTO contrato_aluguel (data_inicio, data_fim, valor_pago, Cliente_id, carro_id, Funcionario_id, Seguro_id) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setDate(1, Date.valueOf(contrato.getDataInicio()));
            preparedStatement.setDate(2, Date.valueOf(contrato.getDataFim()));
            preparedStatement.setDouble(3, contrato.getValorPago());
            preparedStatement.setInt(4, contrato.getClienteId());
            preparedStatement.setInt(5, contrato.getCarroId());
            preparedStatement.setInt(6, contrato.getFuncionarioId()); // Inserindo o novo campo
            preparedStatement.setInt(7, contrato.getSeguroId()); // Inserindo o novo campo
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
        String sql = "UPDATE contrato_aluguel SET data_inicio = ?, data_fim = ?, carro_id = ?, Cliente_id = ?, valor_pago = ?, " +
                "Funcionario_id = ?, Seguro_id = ? WHERE id = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setDate(1, Date.valueOf(contrato.getDataInicio()));
            statement.setDate(2, Date.valueOf(contrato.getDataFim()));
            statement.setInt(3, contrato.getCarroId());
            statement.setInt(4, contrato.getClienteId());
            statement.setDouble(5, contrato.getValorPago());
            statement.setInt(6, contrato.getFuncionarioId()); // Atualizando o novo campo
            statement.setInt(7, contrato.getSeguroId()); // Atualizando o novo campo
            statement.setLong(8, id);
            int rowsAffected = statement.executeUpdate();
            if (rowsAffected == 0) {
                throw new SQLException("Falha ao atualizar o contrato. Nenhuma linha foi afetada.");
            }
        } catch (SQLException e) {
            throw new SQLException("Erro ao atualizar contrato. Verifique os detalhes e tente novamente.", e);
        }
    }

    // Exclui um contrato pelo ID
    public void deleteContratoById(Long id) throws SQLException {
        if (!existsById(id)) {
            throw new SQLException("Contrato não encontrado para exclusão.");
        }
        String sql = "DELETE FROM contrato_aluguel WHERE id = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, id);
            int rowsAffected = statement.executeUpdate();
            if (rowsAffected == 0) {
                throw new SQLException("Falha ao excluir o contrato. Nenhuma linha foi afetada.");
            }
        } catch (SQLException e) {
            throw new SQLException("Erro ao excluir contrato. Verifique os detalhes e tente novamente.", e);
        }
    }

    public double getFaturamentoAcumulado() throws SQLException {
        String sql = "SELECT SUM(valor_pago) FROM contrato_aluguel";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            if (resultSet.next()) {
                return resultSet.getDouble(1); // Retorna o valor da soma dos valores pagos
            } else {
                return 0.0; // Caso não haja nenhum contrato
            }
        }
    }

    public int getQuantidadeCarrosAlugadosHoje() throws SQLException {
        String sql = "SELECT COUNT(*) AS total " +
                "FROM Contrato_aluguel " +
                "WHERE CURRENT_DATE BETWEEN data_inicio AND data_fim";
        int total = 0;

        try (Connection connection = getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {

            if (resultSet.next()) {
                total = resultSet.getInt("total"); // Retorna o total de carros alugados hoje
            }
        }

        return total; // Caso nenhum registro seja encontrado
    }

    public int getContratosEmAndamentoHoje() throws SQLException {
        String sql = "SELECT COUNT(*) AS total " +
                "FROM Contrato_aluguel " +
                "WHERE CURRENT_DATE BETWEEN data_inicio AND data_fim";
        int total = 0;

        try (Connection connection = getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {

            if (resultSet.next()) {
                total = resultSet.getInt("total");
            }
        }

        return total;
    }

    public Map<String, Integer> getCarrosAlugadosPorTipo() throws SQLException {
        String sql = "SELECT tipo.carro_tipo, " +
                "COALESCE(COUNT(ca.id), 0) AS total " +
                "FROM (SELECT 'carro_passeio' AS carro_tipo " +
                "UNION ALL " +
                "SELECT 'suv' " +
                "UNION ALL " +
                "SELECT 'caminhonete') AS tipo " +
                "LEFT JOIN Carro c ON tipo.carro_tipo = c.carro_tipo " +
                "LEFT JOIN Contrato_aluguel ca ON c.id = ca.Carro_id " +
                "AND CURRENT_DATE BETWEEN ca.data_inicio AND ca.data_fim " +
                "GROUP BY tipo.carro_tipo";
        Map<String, Integer> carrosAlugadosPorTipo = new HashMap<>();

        try (Connection connection = getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {

            while (resultSet.next()) {
                String tipo = resultSet.getString("carro_tipo");
                int total = resultSet.getInt("total");
                carrosAlugadosPorTipo.put(tipo, total);
            }
        }

        return carrosAlugadosPorTipo;
    }

}
