package com.code.fullstack_backend.controller;

import com.code.fullstack_backend.exception.FilialNotFoundException;
import com.code.fullstack_backend.model.Filial;
import com.code.fullstack_backend.repository.FilialRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin("http://localhost:3000")
public class FilialController {

    @Autowired
    private FilialRepository filialRepository;

    // Endpoint para adicionar uma nova filial
    @PostMapping("/filial")
    public Filial newFilial(@RequestBody Filial newFilial) {
        // Verifica se o código da filial já existe no banco de dados
        if (filialRepository.existsByCodigoFilial(newFilial.getCodigoFilial())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Código da filial já cadastrado!");
        }
        return filialRepository.save(newFilial);
    }

    // Endpoint para verificar se o código da filial já existe
    @GetMapping("/filial/codigo/{codigoFilial}")
    public boolean checkFilialCodeExists(@PathVariable String codigoFilial) {
        return filialRepository.existsByCodigoFilial(codigoFilial);
    }

    // Método GET para retornar todas as filiais
    @GetMapping("/filiais")
    public List<Filial> getAllFiliais() {
        return filialRepository.findAll();
    }

    @GetMapping("/filial/{id}")
    public Filial getFilialById(@PathVariable Long id) {
        return filialRepository.findById(id).orElseThrow(() -> new FilialNotFoundException(id));
    }


    @PutMapping("/filial/{id}")
    public ResponseEntity<Filial> updateFilial(@PathVariable Long id, @RequestBody Filial filial) {
        try {
            Filial existingFilial = filialRepository.findById(id).orElseThrow(() -> new RuntimeException("Filial não encontrada"));
            // Atualize os dados da filial
            existingFilial.setNome(filial.getNome());
            existingFilial.setRua(filial.getRua());
            existingFilial.setNumero(filial.getNumero());
            existingFilial.setCidade(filial.getCidade());
            existingFilial.setEstado(filial.getEstado());
            existingFilial.setTelefone(filial.getTelefone());
            filialRepository.save(existingFilial); // Salva a filial atualizada

            return ResponseEntity.ok(existingFilial); // Retorna a filial atualizada
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null); // Caso algum erro aconteça
        }
    }


    @DeleteMapping("/filial/{id}")
    public ResponseEntity<Void> deleteFilial(@PathVariable Long id) {
        Optional<Filial> filial = filialRepository.findById(id);
        if (filial.isPresent()) {
            filialRepository.delete(filial.get());
            return ResponseEntity.noContent().build();  // Retorna status 204 (No Content) se deletado
        } else {
            return ResponseEntity.notFound().build();  // Retorna status 404 se não encontrar a filial
        }
    }

}
