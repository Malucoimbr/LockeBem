package com.code.fullstack_backend.controller;

import com.code.fullstack_backend.dao.UserDAO;
import com.code.fullstack_backend.model.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "http://localhost:3000")
public class UserController {

    private final UserDAO userDAO = new UserDAO();

    @GetMapping
    public List<User> getAllUsers() throws SQLException {
        return userDAO.getAllUsers();
    }

    @GetMapping("/{id}")
    public User getUserById(@PathVariable Long id) throws SQLException {
        return userDAO.getUserById(id);
    }

    @PostMapping
    public ResponseEntity<String> addUser(@RequestBody User user) {
        try {
            userDAO.addUser(user);
            return ResponseEntity.status(201).body("User added successfully!");
        } catch (SQLException e) {
            return ResponseEntity.status(500).body("Error adding user: " + e.getMessage());
        }
    }


    @PutMapping("/{id}")
    public void updateUser(@PathVariable Long id, @RequestBody User user) throws SQLException {
        userDAO.updateUser(id, user);
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id) throws SQLException {
        userDAO.deleteUser(id);
    }

    @GetMapping("/rg/{rg}")
    public boolean existsByRg(@PathVariable String rg) throws SQLException {
        return userDAO.existsByRg(rg);
    }
}
