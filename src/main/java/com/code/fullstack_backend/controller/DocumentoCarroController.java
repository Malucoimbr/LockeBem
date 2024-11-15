package com.code.fullstack_backend.controller;

import com.code.fullstack_backend.dao.DocumentoCarroDAO;
import com.code.fullstack_backend.model.DocumentoCarro;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/documento-carro")  // Definindo o caminho base
@CrossOrigin(origins = "http://localhost:3000")
public class DocumentoCarroController {

    private DocumentoCarroDAO documentoCarroDAO;

    // Construtor para inicializar o DocumentoCarroDAO
    public DocumentoCarroController() {
        this.documentoCarroDAO = new DocumentoCarroDAO(); // Inicializando a instância
    }

    // Rota para criar um novo documento de carro (POST)
    @PostMapping
    public ResponseEntity<String> criarDocumentoCarro(@RequestBody DocumentoCarro documentoCarro) {
        try {
            documentoCarroDAO.addDocumentoCarro(documentoCarro);
            return ResponseEntity.status(201).body("Documento do carro criado com sucesso!");
        } catch (SQLException e) {
            // Retorna mensagem de erro mais informativa
            return ResponseEntity.status(400).body("Erro ao criar documento do carro: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            // Erro de validação (anoFab null, por exemplo)
            return ResponseEntity.status(400).body("Erro de validação: " + e.getMessage());
        } catch (Exception e) {
            // Captura qualquer outra exceção imprevista
            return ResponseEntity.status(500).body("Erro inesperado: " + e.getMessage());
        }
    }

    // Rota para exibir um documento de carro pelo ID (GET)
    @GetMapping("/{id}")
    public ResponseEntity<String> exibirDocumentoCarro(@PathVariable Integer id) {
        try {
            Optional<DocumentoCarro> documentoCarro = documentoCarroDAO.getDocumentoCarroById(id);
            return documentoCarro.map(dc -> ResponseEntity.ok("Documento Carro: " + dc.getChassi() + ", Modelo: " + dc.getModelo() + ", Placa: " + dc.getPlaca()))
                    .orElseGet(() -> ResponseEntity.status(404).body("Documento do carro não encontrado."));
        } catch (SQLException e) {
            return ResponseEntity.status(500).body("Erro ao buscar documento do carro: " + e.getMessage());
        }
    }

    // Rota para atualizar os dados de um documento de carro (PUT)
    @PutMapping("/{id}")
    public ResponseEntity<String> atualizarDocumentoCarro(@PathVariable Integer id, @RequestBody DocumentoCarro documentoCarroAtualizado) {
        try {
            Optional<DocumentoCarro> documentoExistente = documentoCarroDAO.getDocumentoCarroById(id);
            if (documentoExistente.isPresent()) {
                documentoCarroAtualizado.setId(id);  // Garantir que o ID não seja alterado
                documentoCarroDAO.updateDocumentoCarro(documentoCarroAtualizado);
                return ResponseEntity.status(200).body("Documento do carro atualizado com sucesso!");
            }
            return ResponseEntity.status(404).body("Documento do carro não encontrado para atualização.");
        } catch (SQLException e) {
            return ResponseEntity.status(500).body("Erro ao atualizar documento do carro: " + e.getMessage());
        }
    }

    // Rota para deletar um documento de carro pelo ID (DELETE)
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletarDocumentoCarro(@PathVariable Integer id) {
        try {
            Optional<DocumentoCarro> documentoExistente = documentoCarroDAO.getDocumentoCarroById(id);
            if (documentoExistente.isPresent()) {
                documentoCarroDAO.deleteDocumentoCarro(id);
                return ResponseEntity.status(200).body("Documento do carro deletado com sucesso!");
            }
            return ResponseEntity.status(404).body("Documento do carro não encontrado para exclusão.");
        } catch (SQLException e) {
            return ResponseEntity.status(500).body("Erro ao deletar documento do carro: " + e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<List<DocumentoCarro>> listarTodosDocumentosCarro() {
        try {
            List<DocumentoCarro> documentosCarro = documentoCarroDAO.getAllDocumentoCarro();

            if (documentosCarro.isEmpty()) {
                // Retorna 204 No Content se não houver documentos, em vez de 404
                return ResponseEntity.noContent().build();
            }

            // Retorna a lista de documentos com status 200 OK
            return ResponseEntity.ok(documentosCarro);
        } catch (SQLException e) {
            // Retorna 500 Internal Server Error em caso de falha no banco de dados
            return ResponseEntity.status(500).body(null);
        }
    }

}
