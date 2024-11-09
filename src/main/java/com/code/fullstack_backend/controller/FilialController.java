package com.code.fullstack_backend.controller;

import com.code.fullstack_backend.dao.FilialDAO;
import com.code.fullstack_backend.model.Filial;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("/api/filiais")
@CrossOrigin(origins = "http://localhost:3000")
public class FilialController {

    private final FilialDAO filialDAO = new FilialDAO();

    // Rota para listar todas as filiais
    @GetMapping
    public List<Filial> getAllFiliais() throws SQLException {
        return filialDAO.getAllFiliais();
    }

    // Rota para pegar uma filial específica por codigoFilial
    @GetMapping("/{codigoFilial}")
    public Filial getFilialByCodigoFilial(@PathVariable String codigoFilial) throws SQLException {
        return filialDAO.getFilialByCodigoFilial(codigoFilial);
    }

    @PostMapping
    public ResponseEntity<String> addFilial(@RequestBody Filial filial) {
        try {
            filialDAO.addFilial(filial);
            return ResponseEntity.status(201).body("Filial adicionada com sucesso!");
        } catch (SQLException e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Erro ao adicionar filial: " + e.getMessage());
        }
    }

    @PutMapping("/codigo/{codigoFilial}")
    public void updateFilial(@PathVariable String codigoFilial, @RequestBody Filial filial) throws SQLException {
        filialDAO.updateFilial(codigoFilial, filial);
    }

    // Rota para deletar uma filial
    @DeleteMapping("/{codigoFilial}")
    public void deleteFilial(@PathVariable String codigoFilial) throws SQLException {
        filialDAO.deleteFilial(codigoFilial);
    }

    // Rota para verificar se o código da filial já existe
    @GetMapping("/codigo/{codigoFilial}")
    public boolean existsByCodigoFilial(@PathVariable String codigoFilial) throws SQLException {
        return filialDAO.existsByCodigoFilial(codigoFilial);
    }
}
