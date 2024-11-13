package com.code.fullstack_backend.controller;

import com.code.fullstack_backend.dao.ContratoAluguelDAO;
import com.code.fullstack_backend.dao.CarroDAO;
import com.code.fullstack_backend.model.Carro;
import com.code.fullstack_backend.model.ContratoAluguel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/contrato-aluguel")
@CrossOrigin(origins = "http://localhost:3000")
public class ContratoAluguelController {

    private final ContratoAluguelDAO contratoAluguelDAO = new ContratoAluguelDAO();
    private final CarroDAO carroDAO = new CarroDAO();

    @PostMapping("/selecionar-datas")
    public ResponseEntity<String> selecionarDatas(@RequestParam LocalDate data_inicio, @RequestParam LocalDate data_fim) {
        if (data_inicio.isAfter(data_fim)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("A data de início deve ser antes da data final.");
        }
        return ResponseEntity.ok("Datas válidas! Agora selecione o carro.");
    }

    @GetMapping("/carros-disponiveis")
    public ResponseEntity<List<Carro>> listarCarrosDisponiveis(@RequestParam LocalDate data_inicio, @RequestParam LocalDate data_fim) {
        try {
            List<Carro> carrosDisponiveis = carroDAO.getCarrosDisponiveis();
            return ResponseEntity.ok(carrosDisponiveis);
        } catch (SQLException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PostMapping("/confirmar-contrato")
    public ResponseEntity<String> confirmarContrato(@RequestBody ContratoAluguel request) {
        try {
            // Verificar os dados recebidos
            System.out.println("Dados recebidos:");
            System.out.println("Cliente_id: " + request.getCliente_id());
            System.out.println("Carro_id: " + request.getCarro_id());
            System.out.println("Data início: " + request.getData_inicio());
            System.out.println("Data fim: " + request.getData_fim());
            System.out.println("Valor pago: " + request.getValor_pago());

            // Processar e salvar o contrato
            ContratoAluguel contrato = new ContratoAluguel();
            contrato.setCliente_id(request.getCliente_id());
            contrato.setCarro_id(request.getCarro_id());
            contrato.setData_inicio(request.getData_inicio());
            contrato.setData_fim(request.getData_fim());
            contrato.setValor_pago(request.getValor_pago());

            contratoAluguelDAO.addContrato(contrato);

            return ResponseEntity.ok("Contrato confirmado com sucesso!");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao confirmar o contrato.");
        }
    }



}
