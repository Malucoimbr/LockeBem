package com.code.fullstack_backend.dao;

import com.code.fullstack_backend.model.User;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDAO {

    public List<User> getAllUsers() throws SQLException {
        List<User> users = new ArrayList<>();
        String sql = "SELECT * FROM users";
        try (Connection connection = DatabaseConnection.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getLong("id"));
                user.setName(resultSet.getString("name"));
                user.setEmail(resultSet.getString("email"));
                user.setNeighborhood(resultSet.getString("neighborhood"));
                user.setCity(resultSet.getString("city"));
                user.setStreet(resultSet.getString("street"));
                user.setRg(resultSet.getString("rg"));
                users.add(user);
            }
        }
        return users;
    }

    public User getUserById(Long id) throws SQLException {
        User user = null;
        String sql = "SELECT * FROM users WHERE id = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    user = new User();
                    user.setId(resultSet.getLong("id"));
                    user.setName(resultSet.getString("name"));
                    user.setEmail(resultSet.getString("email"));
                    user.setNeighborhood(resultSet.getString("neighborhood"));
                    user.setCity(resultSet.getString("city"));
                    user.setStreet(resultSet.getString("street"));
                    user.setRg(resultSet.getString("rg"));
                }
            }
        }
        return user;
    }

    public void addUser(User user) throws SQLException {
        String sql = "INSERT INTO users (name, email, neighborhood, city, street, rg) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, user.getName());
            statement.setString(2, user.getEmail());
            statement.setString(3, user.getNeighborhood());
            statement.setString(4, user.getCity());
            statement.setString(5, user.getStreet());
            statement.setString(6, user.getRg());
            statement.executeUpdate();
        }
    }

    public void updateUser(Long id, User user) throws SQLException {
        String sql = "UPDATE users SET name = ?, email = ?, neighborhood = ?, city = ?, street = ?, rg = ? WHERE id = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, user.getName());
            statement.setString(2, user.getEmail());
            statement.setString(3, user.getNeighborhood());
            statement.setString(4, user.getCity());
            statement.setString(5, user.getStreet());
            statement.setString(6, user.getRg());
            statement.setLong(7, id);
            statement.executeUpdate();
        }
    }

    public void deleteUser(Long id) throws SQLException {
        String sql = "DELETE FROM users WHERE id = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, id);
            statement.executeUpdate();
        }
    }

    public boolean existsByRg(String rg) throws SQLException {
        String sql = "SELECT 1 FROM users WHERE rg = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, rg);
            try (ResultSet resultSet = statement.executeQuery()) {
                return resultSet.next();
            }
        }
    }
}
