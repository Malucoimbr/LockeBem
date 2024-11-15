package com.code.fullstack_backend.controller;

import com.code.fullstack_backend.dao.FuncionarioDAO;
import com.code.fullstack_backend.model.Funcionario;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/funcionario")
@CrossOrigin(origins = "http://localhost:3000")
public class FuncionarioController {

    private final FuncionarioDAO funcionarioDAO = new FuncionarioDAO();

    @GetMapping
    public List<Funcionario> getAllFuncionario() throws SQLException {
        return funcionarioDAO.getAllFuncionario();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Funcionario> getFuncionarioById(@PathVariable Integer id) {
        try {
            Funcionario funcionario = funcionarioDAO.getFuncionarioById(id);
            if (funcionario == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }
            return ResponseEntity.ok(funcionario);
        } catch (SQLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PostMapping
    public ResponseEntity<String> addFuncionario(@RequestBody Funcionario funcionario) {
        try {
            funcionarioDAO.addFuncionario(funcionario);
            return ResponseEntity.status(201).body("Funcionario added successfully!");
        } catch (SQLException e) {
            if (e.getMessage().contains("Email já existe")) {
                return ResponseEntity.status(400).body("Erro: Email já existe no banco de dados.");
            }
            return ResponseEntity.status(500).body("Erro ao adicionar funcionario: " + e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public void updateFuncionario(@PathVariable Integer id, @RequestBody Funcionario funcionario) throws SQLException {
        funcionarioDAO.updateFuncionario(id, funcionario);
    }

    @DeleteMapping("/{id}")
    public void deleteFuncionario(@PathVariable Integer id) throws SQLException {
        funcionarioDAO.deleteFuncionario(id);
    }

    @GetMapping("/exists/{email}")
    public boolean existsByEmail(@PathVariable String email) throws SQLException {
        return funcionarioDAO.existsByEmail(email);
    }

    @GetMapping("/idByEmail/{email}")
    public ResponseEntity<?> getIdByEmail(@PathVariable String email) {
        try {
            Optional<Integer> id = funcionarioDAO.getIdByEmail(email);
            if (id.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Funcionario não encontrado com o email: " + email);
            }
            return ResponseEntity.ok(id.get());
        } catch (SQLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro interno ao acessar o banco de dados");
        }
    }
}
