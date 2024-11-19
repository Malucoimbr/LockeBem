package com.code.fullstack_backend.dao;

import com.code.fullstack_backend.model.Manutencao;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.code.fullstack_backend.dao.DatabaseConnection.getConnection;

public class ManutencaoDAO {

    public List<Manutencao> getAllManutencao() throws SQLException {
        List<Manutencao> manutencaoList = new ArrayList<>();
        String sql = "SELECT * FROM Manutencao";
        try (Connection connection = DatabaseConnection.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            while (resultSet.next()) {
                Manutencao manutencao = new Manutencao();
                manutencao.setId(resultSet.getInt("id"));
                manutencao.setDataMan(resultSet.getObject("dataMan", LocalDate.class));
                manutencao.setTipoMan(resultSet.getString("tipoMan"));
                manutencao.setCustoMan(resultSet.getDouble("custoMan"));
                manutencao.setFuncionarioId(resultSet.getInt("funcionarioId"));
                manutencao.setCarroId(resultSet.getInt("carroId"));
                manutencaoList.add(manutencao);
            }
        }
        return manutencaoList;
    }

    public Manutencao getManutencaoById(Integer id) throws SQLException {
        Manutencao manutencao = null;
        String sql = "SELECT * FROM Manutencao WHERE id = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    manutencao = new Manutencao();
                    manutencao.setId(resultSet.getInt("id"));
                    manutencao.setDataMan(resultSet.getObject("dataMan", LocalDate.class));
                    manutencao.setTipoMan(resultSet.getString("tipoMan"));
                    manutencao.setCustoMan(resultSet.getDouble("custoMan"));
                    manutencao.setFuncionarioId(resultSet.getInt("funcionarioId"));
                    manutencao.setCarroId(resultSet.getInt("carroId"));
                }
            }
        }
        return manutencao;
    }

    public boolean existsManutencaoById(int id) throws SQLException {
        String sql = "SELECT COUNT(*) FROM Manutencao WHERE id = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                resultSet.next();
                return resultSet.getInt(1) > 0;
            }
        }
    }

    public void addManutencao(Manutencao manutencao) throws SQLException {
        String sql = "INSERT INTO Manutencao (dataMan, tipoMan, custoMan, funcionarioId, carroId) VALUES (?, ?, ?, ?, ?)";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            statement.setObject(1, manutencao.getDataMan());
            statement.setString(2, manutencao.getTipoMan());
            statement.setDouble(3, manutencao.getCustoMan());
            statement.setInt(4, manutencao.getFuncionarioId());
            statement.setInt(5, manutencao.getCarroId());

            statement.executeUpdate();

            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    manutencao.setId(generatedKeys.getInt(1));
                }
            }
        }
    }

    public void updateManutencao(Integer id, Manutencao manutencao) throws SQLException {
        String sql = "UPDATE Manutencao SET dataMan = ?, tipoMan = ?, custoMan = ?, funcionarioId = ?, carroId = ? WHERE id = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setObject(1, manutencao.getDataMan());
            statement.setString(2, manutencao.getTipoMan());
            statement.setDouble(3, manutencao.getCustoMan());
            statement.setInt(4, manutencao.getFuncionarioId());
            statement.setInt(5, manutencao.getCarroId());
            statement.setInt(6, id);

            statement.executeUpdate();
        }
    }

    public void deleteManutencao(Integer id) throws SQLException {
        String sql = "DELETE FROM Manutencao WHERE id = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            statement.executeUpdate();
        }
    }

    public List<Manutencao> getManutencaoByFuncionarioId(Integer funcionarioId) throws SQLException {
        List<Manutencao> manutencaoList = new ArrayList<>();
        String sql = "SELECT * FROM Manutencao WHERE funcionarioId = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, funcionarioId);
        }
        return manutencaoList;
    }

    public List<Manutencao> getManutencaoByCarroId(Integer carroId) throws SQLException {
        List<Manutencao> manutencaoList = new ArrayList<>();
        String sql = "SELECT * FROM Manutencao WHERE carroId = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, carroId);
        }
        return manutencaoList;
    }

    public boolean existsManutencaoByCarroId(Integer carroId) throws SQLException {
        String sql = "SELECT 1 FROM Manutencao WHERE carroId = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, carroId);
            try (ResultSet resultSet = statement.executeQuery()) {
                return resultSet.next();
            }
        }
    }

    public double getCustoTotalManutencao() throws SQLException {
        double custoTotal = 0.0;
        String sql = "SELECT SUM(custoMan) AS custoTotal FROM Manutencao";

        try (Connection connection = DatabaseConnection.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {

            if (resultSet.next()) {
                custoTotal = resultSet.getDouble("custoTotal");
            }
        }
        return custoTotal;
    }

    public List<Object[]> getCustoTotalPorTipoCarro() throws SQLException {
        List<Object[]> custosPorTipoCarro = new ArrayList<>();
        String sql =
                "SELECT carroId, tipoMan, " +
                        "(SELECT SUM(custoMan) FROM Manutencao WHERE carroId = m.carroId) AS custoTotal " +
                        "FROM Manutencao m GROUP BY carroId, tipoMan";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                Integer carroId = resultSet.getInt("carroId");
                String tipoMan = resultSet.getString("tipoMan");
                Double custoTotal = resultSet.getDouble("custoTotal");

                custosPorTipoCarro.add(new Object[] { carroId, tipoMan, custoTotal });
            }
        }
        return custosPorTipoCarro;
    }

    public int getQuantidadeCarrosManutencao() throws SQLException {
        String sql = "SELECT COUNT(*) AS total " +
                "FROM Manutencao " +
                "WHERE dataMan = CURRENT_DATE";
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

    public List<Map<String, Object>> getTop5ManutencaoComMaiorCusto() throws SQLException {
        String sql =
                "SELECT m.tipoMan, SUM(m.custoMan) AS total_custo " +
                        "FROM Manutencao m " +
                        "GROUP BY m.tipoMan " +
                        "ORDER BY total_custo DESC " +
                        "LIMIT 5";

        List<Map<String, Object>> manutencaoComMaiorCusto = new ArrayList<>();

        try (Connection connection = getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {

            while (resultSet.next()) {
                Map<String, Object> manutencao = new HashMap<>();
                manutencao.put("tipoMan", resultSet.getString("tipoMan"));
                manutencao.put("total_custo", resultSet.getDouble("total_custo"));
                manutencaoComMaiorCusto.add(manutencao);
            }
        }

        return manutencaoComMaiorCusto;
    }

}
