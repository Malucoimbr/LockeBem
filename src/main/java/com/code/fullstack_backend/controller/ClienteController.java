package com.code.fullstack_backend.controller;

import com.code.fullstack_backend.dao.ClienteDAO;
import com.code.fullstack_backend.model.Cliente;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("/api/cliente")
@CrossOrigin(origins = "http://localhost:3000")
public class ClienteController {

    private final ClienteDAO clienteDAO = new ClienteDAO();

    @GetMapping
    public List<Cliente> getAllCliente() throws SQLException {
        return clienteDAO.getAllCliente();
    }

    @GetMapping("/{rg}")
    public Cliente getClienteByRg(@PathVariable Integer rg) throws SQLException {
        return clienteDAO.getClienteByRg(rg);
    }

    @PostMapping
    public ResponseEntity<String> addCliente(@RequestBody Cliente cliente) {
        try {
            clienteDAO.addCliente(cliente);
            return ResponseEntity.status(201).body("Cliente added successfully!");
        } catch (SQLException e) {
            return ResponseEntity.status(500).body("Error adding cliente: " + e.getMessage());
        }
    }

    @PutMapping("/{rg}")
    public void updateCliente(@PathVariable Integer rg, @RequestBody Cliente cliente) throws SQLException {
        clienteDAO.updateCliente(rg, cliente);
    }

    @DeleteMapping("/{rg}")
    public void deleteCliente(@PathVariable Integer rg) throws SQLException {
        clienteDAO.deleteCliente(rg);
    }

    @GetMapping("/exists/{rg}")
    public boolean existsByRg(@PathVariable Integer rg) throws SQLException {
        return clienteDAO.existsByRg(rg);
    }
}