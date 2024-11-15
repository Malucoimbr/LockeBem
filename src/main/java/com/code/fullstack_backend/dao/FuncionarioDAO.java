package com.code.fullstack_backend.dao;

import com.code.fullstack_backend.model.Funcionario;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class FuncionarioDAO {

    public List<Funcionario> getAllFuncionario() throws SQLException {
        List<Funcionario> funcionarioList = new ArrayList<>();
        String sql = "SELECT * FROM Funcionario";
        try (Connection connection = DatabaseConnection.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            while (resultSet.next()) {
                Funcionario funcionario = new Funcionario();
                funcionario.setId(resultSet.getInt("id"));
                funcionario.setNome(resultSet.getString("nome"));
                funcionario.setCargo(resultSet.getString("cargo"));
                funcionario.setTelefone(resultSet.getString("telefone"));
                funcionario.setDataAdmissao(resultSet.getObject("dataAdmissao", LocalDate.class));
                funcionario.setEmail(resultSet.getString("email"));
                funcionario.setFilialId(resultSet.getInt("filialId"));
                funcionario.setSupervisorId(resultSet.getInt("supervisorId"));
                funcionarioList.add(funcionario);
            }
        }
        return funcionarioList;
    }

    public Funcionario getFuncionarioById(Integer id) throws SQLException {
        Funcionario funcionario = null;
        String sql = "SELECT * FROM Funcionario WHERE id = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    funcionario = new Funcionario();
                    funcionario.setId(resultSet.getInt("id"));
                    funcionario.setNome(resultSet.getString("nome"));
                    funcionario.setCargo(resultSet.getString("cargo"));
                    funcionario.setTelefone(resultSet.getString("telefone"));
                    funcionario.setDataAdmissao(resultSet.getObject("dataAdmissao", LocalDate.class));
                    funcionario.setEmail(resultSet.getString("email"));
                    funcionario.setFilialId(resultSet.getInt("filialId"));
                    funcionario.setSupervisorId(resultSet.getInt("supervisorId"));
                }
            }
        }
        return funcionario;
    }

    public boolean existsFuncionarioById(int id) throws SQLException {
        String sql = "SELECT COUNT(*) FROM Funcionario WHERE id = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                resultSet.next();
                return resultSet.getInt(1) > 0;
            }
        }
    }

    public void addFuncionario(Funcionario funcionario) throws SQLException {
        String sql = "INSERT INTO Funcionario (nome, cargo, telefone, dataAdmissao, email, filialId, supervisorId) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, funcionario.getNome());
            statement.setString(2, funcionario.getCargo());
            statement.setString(3, funcionario.getTelefone());
            statement.setObject(4, funcionario.getDataAdmissao());
            statement.setString(5, funcionario.getEmail());
            statement.setInt(6, funcionario.getFilialId());
            statement.setInt(7, funcionario.getSupervisorId());

            statement.executeUpdate();

            // Obtém o ID gerado automaticamente e define no objeto
            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    funcionario.setId(generatedKeys.getInt(1));
                }
            }
        }
    }

    public void updateFuncionario(Integer id, Funcionario funcionario) throws SQLException {
        String sql = "UPDATE Funcionario SET nome = ?, cargo = ?, telefone = ?, dataAdmissao = ?, email = ?, filialId = ?, supervisorId = ? WHERE id = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, funcionario.getNome());
            statement.setString(2, funcionario.getCargo());
            statement.setString(3, funcionario.getTelefone());
            statement.setObject(4, funcionario.getDataAdmissao());
            statement.setString(5, funcionario.getEmail());
            statement.setInt(6, funcionario.getFilialId());
            statement.setInt(7, funcionario.getSupervisorId());
            statement.setInt(8, id);

            statement.executeUpdate();
        }
    }

    public void deleteFuncionario(Integer id) throws SQLException {
        String sql = "DELETE FROM Funcionario WHERE id = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            statement.executeUpdate();
        }
    }

    public boolean existsByEmail(String email) throws SQLException {
        String sql = "SELECT 1 FROM Funcionario WHERE email = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, email);
            try (ResultSet resultSet = statement.executeQuery()) {
                return resultSet.next();
            }
        }
    }

    public Optional<Integer> getIdByEmail(String email) throws SQLException {
        String sql = "SELECT id FROM Funcionario WHERE email = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, email);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return Optional.of(resultSet.getInt("id"));  // Retorna o ID do funcionário
                }
                return Optional.empty();  // Retorna Optional vazio se não encontrar o funcionário
            }
        }
    }
}