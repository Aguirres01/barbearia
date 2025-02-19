package com.example.barbearia.controller;

import com.example.barbearia.dto.AgendamentoRequestDTO;
import com.example.barbearia.dto.AgendamentoResponseDTO;
import com.example.barbearia.models.Agendamento;
import com.example.barbearia.service.AgendamentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/agendamentos")
public class AgendamentoController {

    @Autowired
    private AgendamentoService agendamentoService;

    // Método para agendar um corte
    @PostMapping
    public ResponseEntity<AgendamentoResponseDTO> agendar(@RequestBody @Valid @Validated AgendamentoRequestDTO dto) {
        AgendamentoResponseDTO response = agendamentoService.agendar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // Método para verificar a disponibilidade do barbeiro em um horário específico
    @GetMapping("/disponibilidade/barbeiro")
    public ResponseEntity<List<LocalDateTime>> verificarDisponibilidadeBarbeiro(
            @RequestParam Long barbeiroId,
            @RequestParam LocalDateTime inicio,
            @RequestParam LocalDateTime fim) {
        List<LocalDateTime> horariosDisponiveis = agendamentoService.verificarDisponibilidadeBarbeiro(barbeiroId, inicio, fim);
        return ResponseEntity.ok(horariosDisponiveis);
    }

    // Método para verificar se o cliente já tem agendamento no mesmo dia
    @GetMapping("/disponibilidade/cliente")
    public ResponseEntity<Boolean> verificarAgendamentosCliente(
            @RequestParam Long clienteId,
            @RequestParam LocalDateTime data) {
        boolean temAgendamento = agendamentoService.verificarAgendamentosCliente(clienteId, data);
        return ResponseEntity.ok(temAgendamento);
    }

    // Método para listar todos os agendamentos de um cliente
    @GetMapping("/cliente/{clienteId}")
    public ResponseEntity<List<AgendamentoResponseDTO>> listarAgendamentosCliente(@PathVariable Long clienteId) {
        List<AgendamentoResponseDTO> agendamentos = agendamentoService.listarAgendamentosPorCliente(clienteId);
        return ResponseEntity.ok(agendamentos);
    }
}
