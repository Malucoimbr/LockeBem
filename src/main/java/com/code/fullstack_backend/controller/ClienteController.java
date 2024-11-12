package com.code.fullstack_backend.controller;

import com.code.fullstack_backend.dao.ClienteDAO;
import com.code.fullstack_backend.model.Cliente;
import org.springframework.http.HttpStatus;
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
    @GetMapping("/{id}")
    public ResponseEntity<Cliente> getClienteById(@PathVariable Integer id) {
        try {
            Cliente cliente = clienteDAO.getClienteById(id);
            if (cliente == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }
            return ResponseEntity.ok(cliente);
        } catch (SQLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PostMapping
    public ResponseEntity<String> addCliente(@RequestBody Cliente cliente) {
        try {
            clienteDAO.addCliente(cliente);
            return ResponseEntity.status(201).body("Cliente added successfully!");
        } catch (SQLException e) {
            // Verifica se a exceção foi devido à duplicação do RG
            if (e.getMessage().contains("RG já existe")) {
                return ResponseEntity.status(400).body("Erro: RG já existe no banco de dados.");
            }
            return ResponseEntity.status(500).body("Erro ao adicionar cliente: " + e.getMessage());
        }
    }


    @PutMapping("/{id}")
    public void updateCliente(@PathVariable Integer id, @RequestBody Cliente cliente) throws SQLException {
        clienteDAO.updateCliente(id, cliente);
    }


    @DeleteMapping("/{id}")
    public void deleteCliente(@PathVariable Integer id) throws SQLException {
        clienteDAO.deleteCliente(id);
    }

    @GetMapping("/exists/{rg}")
    public boolean existsByRg(@PathVariable Integer rg) throws SQLException {
        return clienteDAO.existsByRg(rg);
    }
}