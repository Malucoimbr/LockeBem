package com.code.fullstack_backend.controller;

import com.code.fullstack_backend.dao.FilialDAO;
import com.code.fullstack_backend.model.Filial;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/filial")
@CrossOrigin(origins = "http://localhost:3000")
public class FilialController {

    private final FilialDAO filialDAO = new FilialDAO();

    @GetMapping
    public List<Filial> getAllFilial() throws SQLException {
        return filialDAO.getAllFilial();
    }

    @GetMapping("/{id}")
    public Filial getFilialById(@PathVariable String id) throws SQLException {
        return filialDAO.getFilialById(id);
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

    @PutMapping("/{id}")
    public ResponseEntity<String> updateFilial(@PathVariable String id, @RequestBody Filial filial) {
        try {
            boolean isUpdated = filialDAO.updateFilial(id, filial);
            if (isUpdated) {
                return ResponseEntity.status(HttpStatus.NO_CONTENT).build(); // 204 No Content para sucesso
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Filial não encontrada.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao atualizar filial: " + e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public void deleteFilial(@PathVariable String id) throws SQLException {
        filialDAO.deleteFilial(id);
    }


    @GetMapping("/exists/{id}")
    public boolean existsById(@PathVariable String id) throws SQLException {
        return filialDAO.existsById(id);
    }
}