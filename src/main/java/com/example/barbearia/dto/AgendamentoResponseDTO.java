package com.example.barbearia.dto;

import com.example.barbearia.models.Agendamento;

import java.time.LocalDateTime;

public record AgendamentoResponseDTO(
        Long id,
        String cliente,
        String barbeiro,
        String servico,
        LocalDateTime dataHora
) {
    public static AgendamentoResponseDTO fromEntity(Agendamento agendamento) {
        return new AgendamentoResponseDTO(
                agendamento.getId(),
                agendamento.getCliente().getNome(),
                agendamento.getBarbeiro().getNome(),
                agendamento.getServico().getNome(),
                agendamento.getDataHora()
        );
    }
}

