package com.code.fullstack_backend.dao;

import com.code.fullstack_backend.model.Carro;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import static com.code.fullstack_backend.dao.DatabaseConnection.getConnection;

public class CarroDAO {

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
                Carro carro = new Carro(
                        resultSet.getInt("id"),
                        resultSet.getInt("km"),
                        resultSet.getString("carro_tipo"),
                        resultSet.getDouble("valor_diaria"),
                        resultSet.getInt("Filial_id"),
                        resultSet.getInt("Documento_id")  // Agora também recupera o ID do documento do carro
                );
                carros.add(carro);
            }
        }
        return carros;
    }

    public Carro getCarroById(int id) {
        try (Connection conn = getConnection()) {
            String sql = "SELECT * FROM carro WHERE id = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                // Usando o construtor com parâmetros
                Carro carro = new Carro(
                        rs.getInt("id"),
                        rs.getInt("km"),
                        rs.getString("carro_tipo"),
                        rs.getDouble("valor_diaria"),
                        rs.getInt("Filial_id"),
                        rs.getInt("Documento_id")
                );
                return carro;
            }
            return null; // Caso não encontre o carro
        } catch (SQLException e) {
            e.printStackTrace();
            return null; // Ou você pode lançar uma exceção customizada
        }
    }



    public void addCarro(Carro carro) throws SQLException {
        // Garantir que carroTipo não seja nulo ou vazio
        if (carro.getCarroTipo() == null || carro.getCarroTipo().isEmpty()) {
            throw new IllegalArgumentException("O campo 'carroTipo' não pode ser nulo ou vazio.");
        }

        // Comando SQL para inserir os dados do carro
        String sql = "INSERT INTO Carro (km, carro_tipo, valor_diaria, Filial_id, Documento_id) VALUES (?, ?, ?, ?, ?)";

        // Usando o DatabaseConnection para obter a conexão
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, carro.getKm());
            stmt.setString(2, carro.getCarroTipo());
            stmt.setDouble(3, carro.getValorDiaria());
            stmt.setInt(4, carro.getFilialId());
            stmt.setInt(5, carro.getDocumentoCarroId());
            stmt.executeUpdate(); // Executa o comando SQL
        }
    }





    public void updateCarro(Integer id, Carro carro) throws SQLException {
        String sql = "UPDATE Carro SET km = ?, carro_tipo = ?, Filial_id = ?, valor_diaria = ?, Documento_id = ? WHERE id = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, carro.getKm());
            statement.setString(2, carro.getCarroTipo());
            statement.setInt(3, carro.getFilialId());
            statement.setDouble(4, carro.getValorDiaria());
            statement.setInt(5, carro.getDocumentoCarroId());
            statement.setInt(6, id);

            statement.executeUpdate();
        }
    }

    private boolean existsById(Integer id) throws SQLException {
        String sql = "SELECT COUNT(*) FROM carro WHERE id = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return resultSet.getInt(1) > 0;
            }
        }
        return false;
    }



    public void deleteCarro(int id) throws SQLException {
        String sql = "DELETE FROM carro WHERE id = ?";
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            statement.executeUpdate();
        }
    }

    public List<Carro> getCarrosDisponiveis(LocalDate dataInicio, LocalDate dataFim) throws SQLException {
        List<Carro> carros = new ArrayList<>();

        // SQL ajustado utilizando BETWEEN para verificar sobreposição do período
        String sql = "SELECT * FROM carro " +
                "LEFT JOIN contrato ON contrato.Carro_id = carro.id " +
                "WHERE contrato.id IS NULL " +  // Carros sem contrato
                "OR NOT (contrato.data_inicio BETWEEN ? AND ? " +  // Verifica se a data de início do contrato está no intervalo
                "OR contrato.data_fim BETWEEN ? AND ?)";  // Verifica se a data de fim do contrato está no intervalo

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            // Definindo corretamente os parâmetros
            statement.setDate(1, java.sql.Date.valueOf(dataInicio)); // dataInicio (data inicial do período solicitado)
            statement.setDate(2, java.sql.Date.valueOf(dataFim));    // dataFim (data final do período solicitado)
            statement.setDate(3, java.sql.Date.valueOf(dataInicio)); // Parâmetro de dataInicio
            statement.setDate(4, java.sql.Date.valueOf(dataFim));    // Parâmetro de dataFim

            // Executando a consulta e processando os resultados
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Carro carro = new Carro(
                            resultSet.getInt("id"),
                            resultSet.getInt("km"),
                            resultSet.getString("carro_tipo"),
                            resultSet.getDouble("valor_diaria"),
                            resultSet.getInt("Filial_id"),
                            resultSet.getInt("Documento_carro_id")
                    );
                    carros.add(carro);
                }
            }
        }
        return carros;
    }


    public int getQuantidadeCarrosDisponiveis() throws SQLException {
        String sql = "SELECT COUNT(*) AS carros_disponiveis " +
                "FROM Carro c " +
                "WHERE c.id NOT IN (" +
                "   SELECT ca.Carro_id " +
                "   FROM Contrato_aluguel ca " +
                "   WHERE CURRENT_DATE BETWEEN ca.data_inicio AND ca.data_fim" +
                ") " +
                "AND c.id NOT IN (" +
                "   SELECT m.carroId " +
                "   FROM Manutencao m " +
                "   WHERE CURRENT_DATE = m.dataMan" +
                ")";

        int carrosDisponiveis = 0;

        try (Connection connection = getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {

            if (resultSet.next()) {
                carrosDisponiveis = resultSet.getInt("carros_disponiveis");
            }
        }

        return carrosDisponiveis;
    }



}
