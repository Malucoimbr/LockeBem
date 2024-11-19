package com.code.fullstack_backend.controller;

import com.code.fullstack_backend.dao.CarroDAO;
import com.code.fullstack_backend.model.Carro;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/carro")  // Definindo o caminho base
@CrossOrigin(origins = "http://localhost:3000")
public class CarroController {

    private CarroDAO carroDAO = new CarroDAO();

    @PostMapping
    public ResponseEntity<String> criarCarro(@RequestBody Carro carro) {
        try {
            carroDAO.addCarro(carro);
            return ResponseEntity.status(201).body("Carro criado com sucesso!");
        } catch (SQLException e) {
            return ResponseEntity.status(500).body("Erro ao acessar o banco de dados: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(400).body("Erro ao criar carro: " + e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Carro> exibirCarro(@PathVariable Integer id) {
        try {
            Optional<Carro> carro = Optional.ofNullable(carroDAO.getCarroById(id));
            return carro.map(c -> ResponseEntity.ok(c)) // Retorna o objeto Carro diretamente como JSON
                    .orElseGet(() -> ResponseEntity.status(404).body(null));
        } catch (Exception e) {
            return ResponseEntity.status(400).body(null);
        }
    }


    @PutMapping("/{id}")
    public void updateCarro(@PathVariable Integer id, @RequestBody Carro carro) throws SQLException {
        carroDAO.updateCarro(id, carro);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletarCarro(@PathVariable Integer id) {
        try {
            Optional<Carro> carroExistente = Optional.ofNullable(carroDAO.getCarroById(id));
            if (carroExistente.isPresent()) {
                carroDAO.deleteCarro(id);
                return ResponseEntity.status(200).body("Carro deletado com sucesso!");
            } else {
                return ResponseEntity.status(404).body("Carro não encontrado para exclusão.");
            }
        } catch (SQLException e) {
            return ResponseEntity.status(500).body("Erro ao acessar o banco de dados: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(400).body("Erro ao deletar carro: " + e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<List<Carro>> listarCarros() {
        try {
            List<Carro> carros = carroDAO.getAllCarro();
            return carros.isEmpty() ?
                    ResponseEntity.status(404).body(null) :
                    ResponseEntity.ok(carros);
        } catch (SQLException e) {
            return ResponseEntity.status(500).body(null);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(null);
        }
    }

    @GetMapping("/disponiveis")
    public ResponseEntity<List<Carro>> listarCarrosDisponiveis(
            @RequestParam String dataInicio,
            @RequestParam String dataFim) {
        try {
            // Convertendo as datas de String para LocalDate
            LocalDate start = LocalDate.parse(dataInicio);
            LocalDate end = LocalDate.parse(dataFim);
            System.out.println("Procurando carros disponíveis de " + start + " até " + end); // Log para ver se a rota está sendo chamada
            List<Carro> carrosDisponiveis = carroDAO.getCarrosDisponiveis(start, end);
            if (carrosDisponiveis.isEmpty()) {
                System.out.println("Nenhum carro disponível encontrado.");
                return ResponseEntity.status(404).body(null);
            }
            System.out.println("Carros encontrados: " + carrosDisponiveis.size());
            return ResponseEntity.ok(carrosDisponiveis);
        } catch (SQLException e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body(null);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body(null);
        }
    }


    @GetMapping("/qtdedisponiveis")
    public ResponseEntity<Integer> getCarrosDisponiveis() {
        try {
            int carrosDisponiveis = carroDAO.getQuantidadeCarrosDisponiveis();
            return ResponseEntity.ok(carrosDisponiveis);
        } catch (SQLException e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body(null);
        }
    }
}
