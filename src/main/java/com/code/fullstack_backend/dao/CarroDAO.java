package com.code.fullstack_backend.dao;

import com.code.fullstack_backend.model.Carro;
import com.code.fullstack_backend.model.CarroTipo;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static com.code.fullstack_backend.dao.DatabaseConnection.getConnection;

public class CarroDAO {

    // Verifica se a placa já existe
    public boolean existsByPlaca(String placa) throws SQLException {
        String sql = "SELECT COUNT(*) FROM carro WHERE placa = ?";
        try (Connection connection = getConnection();
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


        public boolean existsCarroById(int id) throws SQLException {
            String sql = "SELECT COUNT(*) FROM Carro WHERE id = ?";
            try (Connection connection = DatabaseConnection.getConnection();
                 PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setInt(1, id);
                try (ResultSet resultSet = statement.executeQuery()) {
                    resultSet.next();
                    return resultSet.getInt(1) > 0;
                }
            }
        }


    // Verifica se o código da filial já existe
    public boolean existsByCodigoFilial(String codigoFilial) throws SQLException {
        String sql = "SELECT COUNT(*) FROM filial WHERE id = ?";
        try (Connection connection = getConnection();
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

    public List<Carro> getAllCarro() throws SQLException {
        List<Carro> carros = new ArrayList<>();
        String sql = "SELECT * FROM carro";
        try (Connection connection = getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            while (resultSet.next()) {
                Carro carro = new Carro();
                carro.setId((int) resultSet.getLong("id"));
                carro.setPlaca(resultSet.getString("placa"));
                carro.setModelo(resultSet.getString("modelo"));
                carro.setAno_fab(resultSet.getInt("ano_fab"));
                carro.setKm(resultSet.getInt("km"));
                carro.setCarroTipo(String.valueOf(CarroTipo.valueOf(resultSet.getString("carro_tipo")))); // Tipo do carro
                carro.setFilialId(resultSet.getInt("Filial_id"));
                carro.setValorDiaria(resultSet.getDouble("valor_diaria")); // Recupera o valor diário
                carros.add(carro);
            }
        }
        return carros;
    }


    public Carro getCarroById(Long id) throws SQLException {
        Carro carro = null;
        String sql = "SELECT * FROM carro WHERE id = ?";
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    carro = new Carro();
                    carro.setId((int) resultSet.getLong("id"));
                    carro.setPlaca(resultSet.getString("placa"));
                    carro.setModelo(resultSet.getString("modelo"));
                    carro.setAno_fab(resultSet.getInt("ano_fab"));
                    carro.setKm(resultSet.getInt("km"));
                    carro.setCarroTipo(String.valueOf(CarroTipo.valueOf(resultSet.getString("carro_tipo")))); // Tipo do carro
                    carro.setFilialId(resultSet.getInt("Filial_id"));
                    carro.setValorDiaria(resultSet.getDouble("valor_diaria")); // Recupera o valor diário
                }
            }
        }
        return carro;
    }


    public void addCarro(Carro carro) throws SQLException {
        try {
            // Verificando se já existe um carro com a mesma placa
            if (existsByPlaca(carro.getPlaca())) {
                throw new SQLException("Já existe um carro com a mesma placa.");
            }

            // Verificando se o código da filial é válido
            if (!existsByCodigoFilial(String.valueOf(carro.getFilialId()))) {
                throw new SQLException("Código de filial inválido.");
            }

            // Verificando se o carro tem um tipo válido (não nulo)
            if (carro.getCarroTipo() == null) {
                throw new SQLException("Tipo de carro não pode ser nulo.");
            }

            // SQL para inserir o carro no banco de dados, incluindo o valor_diaria
            String sql = "INSERT INTO carro (placa, modelo, ano_fab, km, carro_tipo, Filial_id, valor_diaria) VALUES (?, ?, ?, ?, ?, ?, ?)";

            try (Connection connection = getConnection();
                 PreparedStatement statement = connection.prepareStatement(sql)) {

                // Definindo os valores no PreparedStatement
                statement.setString(1, carro.getPlaca());
                statement.setString(2, carro.getModelo());
                statement.setInt(3, carro.getAno_fab());
                statement.setInt(4, carro.getKm());
                statement.setString(5, carro.getCarroTipo().name()); // Tipo do carro (enum)
                statement.setString(6, String.valueOf(carro.getFilialId())); // Código da filial
                statement.setDouble(7, carro.getValorDiaria()); // Valor diário de aluguel

                // Executando a atualização
                int rowsAffected = statement.executeUpdate();

                if (rowsAffected == 0) {
                    throw new SQLException("Falha ao adicionar o carro. Nenhuma linha foi afetada.");
                }

                System.out.println("Carro adicionado com sucesso!");
            }
        } catch (SQLException e) {
            // Exibindo o erro para depuração
            System.out.println("Erro ao adicionar carro: " + e.getMessage());
            e.printStackTrace(); // Adicionando para mostrar o stack trace completo
            throw new SQLException("Erro ao adicionar carro. Verifique os detalhes e tente novamente.", e);
        }
    }



    public void updateCarro(Long id, Carro carro) throws SQLException {
        if (existsByPlaca(carro.getPlaca()) && !getCarroById(id).getPlaca().equals(carro.getPlaca())) {
            throw new SQLException("Já existe um carro com a mesma placa.");
        }
        if (!existsByCodigoFilial(String.valueOf(carro.getFilialId()))) {
            throw new SQLException("Código de filial inválido.");
        }

        String sql = "UPDATE carro SET placa = ?, modelo = ?, ano_fab = ?, km = ?, carro_tipo = ?, Filial_id = ?, valor_diaria = ? WHERE id = ?";
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, carro.getPlaca());
            statement.setString(2, carro.getModelo());
            statement.setInt(3, carro.getAno_fab());
            statement.setInt(4, carro.getKm());
            statement.setString(5, carro.getCarroTipo().name()); // Tipo do carro
            statement.setString(6, String.valueOf(carro.getFilialId())); // Código da filial
            statement.setDouble(7, carro.getValorDiaria()); // Valor diário
            statement.setLong(8, id); // ID do carro para atualização

            statement.executeUpdate();
        }
    }

    // Exclui um carro
    public void deleteCarro(Long id) throws SQLException {
        String sql = "DELETE FROM carro WHERE id = ?";
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, id);
            statement.executeUpdate();
        }
    }

    public List<Carro> getCarrosDisponiveis() throws SQLException {
        List<Carro> carros = new ArrayList<>();
        String sql = "SELECT * FROM carro c " +
                "LEFT JOIN contrato_aluguel ca ON c.id = ca.carro_id " +
                "WHERE ca.id IS NULL";  // Isso traz carros sem contrato

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                Carro carro = new Carro();
                carro.setId(resultSet.getInt("id"));
                carro.setPlaca(resultSet.getString("placa"));
                carro.setModelo(resultSet.getString("modelo"));
                carro.setAno_fab(resultSet.getInt("ano_fab"));
                carro.setKm(resultSet.getInt("km"));
                carro.setCarroTipo(String.valueOf(CarroTipo.valueOf(resultSet.getString("carro_tipo")))); // Tipo do carro
                carro.setFilialId(resultSet.getInt("Filial_id"));
                carro.setValorDiaria(resultSet.getDouble("valor_diaria")); // Recupera o valor diário
                carros.add(carro);
            }
        }
        return carros;
    }



}
