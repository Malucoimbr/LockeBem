package com.code.fullstack_backend.dao;

import com.code.fullstack_backend.model.Carro;
import com.code.fullstack_backend.model.Filial;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CarroDAO {

    public boolean existsByPlaca(String placa) throws SQLException {
        String sql = "SELECT COUNT(*) FROM carros WHERE placa = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, placa);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt(1) > 0;
                }
            }
        }
        return false;
    }

    public boolean existsByCodigoFilial(String codigoFilial) throws SQLException {
        String sql = "SELECT COUNT(*) FROM filiais WHERE codigoFilial = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, codigoFilial);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt(1) > 0;
                }
            }
        }
        return false;
    }

    public List<Carro> getAllCarros() throws SQLException {
        List<Carro> carros = new ArrayList<>();
        String sql = "SELECT * FROM carros";
        try (Connection connection = DatabaseConnection.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            while (resultSet.next()) {
                Carro carro = new Carro();
                carro.setId(resultSet.getLong("id"));
                carro.setPlaca(resultSet.getString("placa"));
                carro.setModelo(resultSet.getString("modelo"));
                carro.setAnoFab(resultSet.getInt("ano_fab"));
                carro.setKm(resultSet.getInt("km"));
                carro.setTipoCarro(resultSet.getString("tipo_carro"));
                carro.setCodigoFilial(resultSet.getString("codigoFilial")); // Atribuindo o código da filial
                carros.add(carro);
            }
        }
        return carros;
    }

    public Carro getCarroById(Long id) throws SQLException {
        Carro carro = null;
        String sql = "SELECT * FROM carros WHERE id = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    carro = new Carro();
                    carro.setId(resultSet.getLong("id"));
                    carro.setPlaca(resultSet.getString("placa"));
                    carro.setModelo(resultSet.getString("modelo"));
                    carro.setAnoFab(resultSet.getInt("ano_fab"));
                    carro.setKm(resultSet.getInt("km"));
                    carro.setTipoCarro(resultSet.getString("tipo_carro"));
                    carro.setCodigoFilial(resultSet.getString("codigoFilial")); // Atribuindo o código da filial
                }
            }
        }
        return carro;
    }

    public void addCarro(Carro carro) throws SQLException {
        try {
            if (existsByPlaca(carro.getPlaca())) {
                throw new SQLException("Já existe um carro com a mesma placa.");
            }

            if (!existsByCodigoFilial(carro.getCodigoFilial())) {
                throw new SQLException("Código de filial inválido.");
            }

            String sql = "INSERT INTO carros (placa, modelo, ano_fab, km, tipo_carro, codigoFilial) VALUES (?, ?, ?, ?, ?, ?)";

            try (Connection connection = DatabaseConnection.getConnection();
                 PreparedStatement statement = connection.prepareStatement(sql)) {

                statement.setString(1, carro.getPlaca());
                statement.setString(2, carro.getModelo());
                statement.setInt(3, carro.getAnoFab());
                statement.setInt(4, carro.getKm());
                statement.setString(5, carro.getTipoCarro());
                statement.setString(6, carro.getCodigoFilial()); // Associando o código da filial

                statement.executeUpdate();
            }
        } catch (SQLException e) {
            System.out.println("Erro ao adicionar carro: " + e.getMessage());
            throw new SQLException("Erro ao adicionar carro. Verifique os detalhes e tente novamente.", e);
        }
    }



    public void updateCarro(Long id, Carro carro) throws SQLException {

        if (existsByPlaca(carro.getPlaca()) && !getCarroById(id).getPlaca().equals(carro.getPlaca())) {
            throw new SQLException("Já existe um carro com a mesma placa.");
        }


        if (!existsByCodigoFilial(carro.getCodigoFilial())) {
            throw new SQLException("Código de filial inválido.");
        }

        String sql = "UPDATE carros SET placa = ?, modelo = ?, ano_fab = ?, km = ?, tipo_carro = ?, codigoFilial = ? WHERE id = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, carro.getPlaca());
            statement.setString(2, carro.getModelo());
            statement.setInt(3, carro.getAnoFab());
            statement.setInt(4, carro.getKm());
            statement.setString(5, carro.getTipoCarro());
            statement.setString(6, carro.getCodigoFilial()); // Atualizando o código da filial
            statement.setLong(7, id);
            statement.executeUpdate();
        }
    }


    public void deleteCarro(Long id) throws SQLException {
        String sql = "DELETE FROM carros WHERE id = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, id);
            statement.executeUpdate();
        }
    }
}
