package com.example.barbearia.repository;

import com.example.barbearia.models.Agendamento;
import com.example.barbearia.models.Barbeiro;
import com.example.barbearia.models.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
@Repository
public interface AgendamentoRepository extends JpaRepository<Agendamento, Long> {
    List<Agendamento> findByBarbeiroAndDataHoraBetween(Barbeiro barbeiro, LocalDateTime inicio, LocalDateTime fim);


    List<Agendamento> findByClienteAndDataHoraBetween(Cliente cliente, LocalDateTime localDateTime, LocalDateTime fim);
}
