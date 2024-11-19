package com.code.fullstack_backend.controller;

import com.code.fullstack_backend.dao.ManutencaoDAO;
import com.code.fullstack_backend.model.Manutencao;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("/api/manutencao")
@CrossOrigin(origins = "http://localhost:3000")
public class ManutencaoController {

    private final ManutencaoDAO manutencaoDAO = new ManutencaoDAO();

    @GetMapping
    public List<Manutencao> getAllManutencao() throws SQLException {
        return manutencaoDAO.getAllManutencao();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Manutencao> getManutencaoById(@PathVariable Integer id) {
        try {
            Manutencao manutencao = manutencaoDAO.getManutencaoById(id);
            if (manutencao == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }
            return ResponseEntity.ok(manutencao);
        } catch (SQLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PostMapping
    public ResponseEntity<String> addManutencao(@RequestBody Manutencao manutencao) {
        try {
            manutencaoDAO.addManutencao(manutencao);
            return ResponseEntity.status(201).body("Manutencao added successfully!");
        } catch (SQLException e) {
            return ResponseEntity.status(500).body("Erro ao adicionar manutencao: " + e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public void updateManutencao(@PathVariable Integer id, @RequestBody Manutencao manutencao) throws SQLException {
        manutencaoDAO.updateManutencao(id, manutencao);
    }

    @DeleteMapping("/{id}")
    public void deleteManutencao(@PathVariable Integer id) throws SQLException {
        manutencaoDAO.deleteManutencao(id);
    }

    @GetMapping("/funcionario/{funcionarioId}")
    public List<Manutencao> getManutencaoByFuncionarioId(@PathVariable Integer funcionarioId) throws SQLException {
        return manutencaoDAO.getManutencaoByFuncionarioId(funcionarioId);
    }

    @GetMapping("/carro/{carroId}")
    public List<Manutencao> getManutencaoByCarroId(@PathVariable Integer carroId) throws SQLException {
        return manutencaoDAO.getManutencaoByCarroId(carroId);
    }

    @GetMapping("/exists/{carroId}")
    public boolean existsManutencaoByCarroId(@PathVariable Integer carroId) throws SQLException {
        return manutencaoDAO.existsManutencaoByCarroId(carroId);
    }

    @GetMapping("/custoTotal")
    public ResponseEntity<Double> getCustoTotalManutencao() {
        try {
            double custoTotal = manutencaoDAO.getCustoTotalManutencao();
            return ResponseEntity.ok(custoTotal);
        } catch (SQLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/custoTotalPorTipoCarro")
    public ResponseEntity<List<Object[]>> getCustoTotalPorTipoCarro() {
        try {
            List<Object[]> custosPorTipoCarro = manutencaoDAO.getCustoTotalPorTipoCarro();
            return ResponseEntity.ok(custosPorTipoCarro);
        } catch (SQLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/manutencaoHoje")
    public ResponseEntity<Integer> getQuantidadeCarrosManutencao() {
        try {
            int quantidade = manutencaoDAO.getQuantidadeCarrosManutencao();
            return ResponseEntity.ok(quantidade);
        } catch (SQLException e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body(null); // Retorna erro 500 em caso de exceção
        }
    }

}
