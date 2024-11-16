package com.code.fullstack_backend.dao;

import com.code.fullstack_backend.model.Multa;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class MultaDAO {
    public List<Multa> getAllMulta() throws SQLException {
        List<Multa> multaList = new ArrayList<>();
        String sql = "SELECT * FROM Multa";
        try (Connection connection = DatabaseConnection.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            while (resultSet.next()) {
                Multa multa = new Multa();
                multa.setId(resultSet.getInt("id"));
                multa.setDataMulta(resultSet.getObject("dataMulta", LocalDate.class));
                multa.setTipoInfracao(resultSet.getString("tipoInfracao"));
                multa.setValorMulta(resultSet.getDouble("valorMulta"));
                multa.setContratoId(resultSet.getInt("contratoId"));
                multaList.add(multa);
            }
        }
        return multaList;
    }

    public Multa getMultaById(Integer id) throws SQLException {
        Multa multa = null;
        String sql = "SELECT * FROM Multa WHERE id = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    multa = new Multa();
                    multa.setId(resultSet.getInt("id"));
                    multa.setDataMulta(resultSet.getObject("dataMulta", LocalDate.class));
                    multa.setTipoInfracao(resultSet.getString("tipoInfracao"));
                    multa.setValorMulta(resultSet.getDouble("valorMulta"));
                    multa.setContratoId(resultSet.getInt("contratoId"));
                }
            }
        }
        return multa;
    }

    public boolean existsMultaById(int id) throws SQLException {
        String sql = "SELECT COUNT(*) FROM Multa WHERE id = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                resultSet.next();
                return resultSet.getInt(1) > 0;
            }
        }
    }

    public void addMulta(Multa multa) throws SQLException {
        String sql = "INSERT INTO Multa (dataMulta, tipoInfracao, valorMulta, contratoId) VALUES (?, ?, ?, ?)";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            statement.setObject(1, multa.getDataMulta());
            statement.setString(2, multa.getTipoInfracao());
            statement.setDouble(3, multa.getValorMulta());
            statement.setInt(4, multa.getContratoId());

            statement.executeUpdate();

            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    multa.setId(generatedKeys.getInt(1));
                }
            }
        }
    }

    public void updateMulta(Integer id, Multa multa) throws SQLException {
        String sql = "UPDATE Multa SET dataMulta = ?, tipoInfracao = ?, valorMulta = ?, contratoId = ? WHERE id = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setObject(1, multa.getDataMulta());
            statement.setString(2, multa.getTipoInfracao());
            statement.setDouble(3, multa.getValorMulta());
            statement.setInt(4, multa.getContratoId());
            statement.setInt(5, id);

            statement.executeUpdate();
        }
    }

    public void deleteMulta(Integer id) throws SQLException {
        String sql = "DELETE FROM Multa WHERE id = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            statement.executeUpdate();
        }
    }

    public List<Multa> getMultasByContratoId(Integer contratoId) throws SQLException {
        List<Multa> multaList = new ArrayList<>();
        String sql = "SELECT * FROM Multa WHERE contratoId = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, contratoId);
        }
        return multaList;
    }

    public boolean existsMultaByContratoId(Integer contratoId) throws SQLException {
        String sql = "SELECT 1 FROM Multa WHERE contratoId = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, contratoId);
            try (ResultSet resultSet = statement.executeQuery()) {
                return resultSet.next();
            }
        }
    }

}
