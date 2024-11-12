package com.code.fullstack_backend.controller;

import com.code.fullstack_backend.dao.CarroDAO;
import com.code.fullstack_backend.dao.FilialDAO;
import com.code.fullstack_backend.model.Carro;
import com.code.fullstack_backend.model.CarroTipo;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("/api/carro")
@CrossOrigin(origins = "http://localhost:3000")
public class CarroController {

    private final CarroDAO carroDAO = new CarroDAO();
    private final FilialDAO filialDAO = new FilialDAO();

    @GetMapping
    public List<Carro> getAllCarro() throws SQLException {
        return carroDAO.getAllCarro();
    }

    @GetMapping("/{id}")
    public Carro getCarroById(@PathVariable Long id) throws SQLException {
        return carroDAO.getCarroById(id);
    }

    @PostMapping
    public ResponseEntity<String> addCarro(@RequestBody Carro carro) {
        try {
            carroDAO.addCarro(carro);
            return ResponseEntity.status(201).body("Carro adicionado com sucesso!");
        } catch (SQLException e) {
            e.printStackTrace();
            return ResponseEntity.status(400).body("Erro ao adicionar carro: " + e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateCarro(@PathVariable Long id, @RequestBody Carro carro) {
        try {
            carroDAO.updateCarro(id, carro);
            return ResponseEntity.status(200).body("Carro atualizado com sucesso!");
        } catch (SQLException e) {
            e.printStackTrace();
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCarro(@PathVariable Long id) {
        try {
            carroDAO.deleteCarro(id);
            return ResponseEntity.status(200).body("Carro deletado com sucesso!");
        } catch (SQLException e) {
            e.printStackTrace();
            return ResponseEntity.status(400).body("Erro ao deletar carro: " + e.getMessage());
        }
    }
}
