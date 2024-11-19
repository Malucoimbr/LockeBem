package com.code.fullstack_backend.controller;

import com.code.fullstack_backend.dao.FuncionarioDAO;
import com.code.fullstack_backend.model.Funcionario;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;
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

    @GetMapping("/total")
    public ResponseEntity<Integer> getTotalFuncionarios() {
        try {
            int total = funcionarioDAO.getTotalFuncionarios();
            return ResponseEntity.ok(total);
        } catch (SQLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(null);
        }
    }
    @GetMapping("/totalAdmitidosUltimoMes")
    public ResponseEntity<Integer> getTotalFuncionariosAdmitidosUltimoMes() {
        try {
            int totalFuncionarios = funcionarioDAO.getTotalFuncionariosAdmitidosUltimoMes();
            return ResponseEntity.ok(totalFuncionarios); // Retorna a quantidade total de funcionários admitidos
        } catch (SQLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null); // Retorna erro se falhar
        }
    }

    @GetMapping("/mediaFuncionariosPorFilial")
    public ResponseEntity<Double> getMediaFuncionariosPorFilial() {
        try {
            double media = funcionarioDAO.getMediaFuncionariosPorFilial();
            return ResponseEntity.ok(media); // Retorna a média de funcionários por filial
        } catch (SQLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null); // Retorna erro se falhar
        }
    }

    @GetMapping("/recentesAdmitidos")
    public ResponseEntity<List<Funcionario>> getRecentAdmitidos() {
        try {
            List<Funcionario> recentAdmitidos = funcionarioDAO.getRecentAdmitidos();
            if (recentAdmitidos.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null); // Retorna 404 se não encontrar funcionários
            }
            return ResponseEntity.ok(recentAdmitidos); // Retorna a lista de funcionários admitidos recentemente
        } catch (SQLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(null); // Retorna erro se falhar
        }
    }

    @GetMapping("/porFilial")
    public ResponseEntity<Map<String, Integer>> getFuncionariosPorFilial() {
        try {
            // Chama o método do DAO para obter a contagem de funcionários por filial
            Map<String, Integer> funcionariosPorFilial = funcionarioDAO.getFuncionariosPorFilial();
            return ResponseEntity.ok(funcionariosPorFilial); // Retorna o mapa com a quantidade de funcionários por filial
        } catch (SQLException e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body(null); // Retorna erro em caso de exceção
        }
    }





}
