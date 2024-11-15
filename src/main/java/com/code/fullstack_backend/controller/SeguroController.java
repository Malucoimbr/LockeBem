package com.code.fullstack_backend.controller;

import com.code.fullstack_backend.dao.SeguroDAO;
import com.code.fullstack_backend.model.Seguro;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("/api/seguro")
@CrossOrigin(origins = "http://localhost:3000")
public class SeguroController {

    private final SeguroDAO seguroDAO = new SeguroDAO();

    // Endpoint para listar todos os seguros
    @GetMapping
    public List<Seguro> getAllSeguro() throws SQLException {
        return seguroDAO.getAllSeguro();
    }

    // Endpoint para adicionar um novo seguro
    @PostMapping
    public ResponseEntity<String> addSeguro(@RequestBody Seguro seguro) {
        try {
            seguroDAO.addSeguro(seguro);
            return ResponseEntity.status(201).body("Seguro adicionado com sucesso!");
        } catch (SQLException e) {
            return ResponseEntity.status(500).body("Erro ao adicionar seguro: " + e.getMessage());
        }
    }

    // Endpoint para deletar um seguro
    @DeleteMapping("/{id}")
    public void deleteSeguro(@PathVariable Integer id) throws SQLException {
        seguroDAO.deleteSeguro(id);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Seguro> getSeguroById(@PathVariable Integer id) {
        Seguro seguro = seguroDAO.findById(id);
        if (seguro != null) {
            return ResponseEntity.ok(seguro);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @PutMapping("/seguro/{id}")
    public ResponseEntity<?> updateSeguro(@PathVariable int id, @RequestBody Seguro seguro) {
        Seguro seguroExistente = seguroDAO.findById(id);
        if (seguroExistente == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Seguro não encontrado");
        }

        seguro.setId(id);  // Defina o ID do seguro, caso ele venha vazio no corpo da requisição
        boolean atualizado = seguroDAO.updateSeguro(seguro);
        if (atualizado) {
            return ResponseEntity.ok("Seguro atualizado com sucesso");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao atualizar seguro");
        }
    }



}
