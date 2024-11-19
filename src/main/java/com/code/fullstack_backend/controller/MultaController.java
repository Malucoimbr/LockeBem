package com.code.fullstack_backend.controller;

import com.code.fullstack_backend.dao.MultaDAO;
import com.code.fullstack_backend.model.Multa;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("/api/multa")
@CrossOrigin(origins = "http://localhost:3000")
public class MultaController {

    private final MultaDAO multaDAO = new MultaDAO();

    @GetMapping
    public List<Multa> getAllMulta() throws SQLException {
        return multaDAO.getAllMulta();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Multa> getMultaById(@PathVariable Integer id) {
        try {
            Multa multa = multaDAO.getMultaById(id);
            if (multa == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }
            return ResponseEntity.ok(multa);
        } catch (SQLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PostMapping
    public ResponseEntity<String> addMulta(@RequestBody Multa multa) {
        try {
            multaDAO.addMulta(multa);
            return ResponseEntity.status(201).body("Multa added successfully!");
        } catch (SQLException e) {
            return ResponseEntity.status(500).body("Erro ao adicionar multa: " + e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public void updateMulta(@PathVariable Integer id, @RequestBody Multa multa) throws SQLException {
        multaDAO.updateMulta(id, multa);
    }

    @DeleteMapping("/{id}")
    public void deleteMulta(@PathVariable Integer id) throws SQLException {
        multaDAO.deleteMulta(id);
    }

    @GetMapping("/contrato/{contratoId}")
    public List<Multa> getMultasByContratoId(@PathVariable Integer contratoId) throws SQLException {
        return multaDAO.getMultasByContratoId(contratoId);
    }

    @GetMapping("/exists/{contratoId}")
    public boolean existsMultaByContratoId(@PathVariable Integer contratoId) throws SQLException {
        return multaDAO.existsMultaByContratoId(contratoId);
    }

    @GetMapping("/total")
    public ResponseEntity<Double> getTotalMultas() {
        try {
            double totalMultas = multaDAO.getTotalMultas();
            return ResponseEntity.ok(totalMultas);
        } catch (SQLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }


}
