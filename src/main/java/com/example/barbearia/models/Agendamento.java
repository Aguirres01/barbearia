package com.example.barbearia.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
@Getter
@Setter
@AllArgsConstructor
@Entity(name = "agendamento")
@Table(name = "agendamentos")

public class Agendamento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "cliente_id", nullable = false)
    private Cliente cliente;

    @ManyToOne
    @JoinColumn(name = "barbeiro_id", nullable = false)
    private Barbeiro barbeiro;

    @ManyToOne
    @JoinColumn(name = "servico_id", nullable = false)
    private Servico servico;

    @Column(name = "data_hora", nullable = false)
    private LocalDateTime dataHora;

    public Agendamento(Cliente cliente, Barbeiro barbeiro, Servico servico, LocalDateTime dataHora) {
        this.cliente = cliente;
        this.barbeiro = barbeiro;
        this.servico = servico;
        this.dataHora = dataHora;
    }

    public Agendamento() {} // Construtor padrão para JPA


}


