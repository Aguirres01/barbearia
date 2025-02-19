package com.example.barbearia.service;

import com.example.barbearia.dto.AgendamentoRequestDTO;
import com.example.barbearia.dto.AgendamentoResponseDTO;
import com.example.barbearia.models.Agendamento;
import com.example.barbearia.models.Barbeiro;
import com.example.barbearia.models.Cliente;
import com.example.barbearia.models.Servico;
import com.example.barbearia.repository.AgendamentoRepository;
import com.example.barbearia.repository.BarbeiroRepository;
import com.example.barbearia.repository.ClienteRepository;
import com.example.barbearia.repository.ServicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AgendamentoService {
    @Autowired
    private AgendamentoRepository agendamentoRepository;
    @Autowired
    private ClienteRepository clienteRepository;
    @Autowired
    private BarbeiroRepository barbeiroRepository;
    @Autowired
    private ServicoRepository servicoRepository;

    public AgendamentoResponseDTO agendar(AgendamentoRequestDTO dto) {
        // Validar se o horário solicitado está dentro do horário de funcionamento da barbearia
        validarHorario(dto.dataHora());

        // Buscar as entidades necessárias
        Cliente cliente = clienteRepository.findById(dto.clienteId())
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado"));
        Barbeiro barbeiro = barbeiroRepository.findById(dto.barbeiroId())
                .orElseThrow(() -> new RuntimeException("Barbeiro não encontrado"));
        Servico servico = servicoRepository.findById(dto.servicoId())
                .orElseThrow(() -> new RuntimeException("Serviço não encontrado"));

        // Verificar se o cliente já tem agendamento no mesmo dia
        validarClienteDisponivelNoMesmoDia(cliente, dto.dataHora(), servico);

        // Verificar se o barbeiro está disponível no horário
        validarBarbeiroDisponivel(barbeiro, dto.dataHora(), servico);

        // Criar e salvar o agendamento
        Agendamento agendamento = new Agendamento(cliente, barbeiro, servico, dto.dataHora());
        agendamentoRepository.save(agendamento);

        // Retornar a resposta
        return AgendamentoResponseDTO.fromEntity(agendamento);
    }

    private void validarHorario(LocalDateTime dataHora) {
        // Verifica o dia da semana e a hora
        DayOfWeek diaSemana = dataHora.getDayOfWeek();
        int hora = dataHora.getHour();

        if (diaSemana == DayOfWeek.SUNDAY || diaSemana == DayOfWeek.MONDAY) {
            throw new RuntimeException("A barbearia não funciona aos domingos e segundas.");
        }

        if (hora < 9 || hora >= 19) {
            throw new RuntimeException("A barbearia funciona das 09h às 19h.");
        }

        if (hora == 12) {
            throw new RuntimeException("A barbearia está fechada para almoço das 12h às 13h.");
        }

        if (dataHora.getMinute() != 0) {
            throw new RuntimeException("Os agendamentos devem ser feitos em horários cheios (ex: 09:00, 10:00).");
        }
    }

    private void validarClienteDisponivelNoMesmoDia(Cliente cliente, LocalDateTime dataHora, Servico servico) {
        // Verifica se o cliente já tem agendamento no mesmo dia
        LocalDateTime fim = dataHora.plusMinutes(servico.getDuracaoEmMinutos());
        List<Agendamento> agendamentos = agendamentoRepository.findByClienteAndDataHoraBetween(cliente, dataHora.toLocalDate().atStartOfDay(), fim);

        if (!agendamentos.isEmpty()) {
            throw new RuntimeException("O cliente já tem um agendamento para este dia.");
        }
    }

    private void validarBarbeiroDisponivel(Barbeiro barbeiro, LocalDateTime dataHora, Servico servico) {
        // Verifica se o barbeiro está disponível no horário solicitado
        LocalDateTime fim = dataHora.plusMinutes(servico.getDuracaoEmMinutos());
        List<Agendamento> conflitos = agendamentoRepository.findByBarbeiroAndDataHoraBetween(barbeiro, dataHora, fim);
        if (!conflitos.isEmpty()) {
            throw new RuntimeException("O barbeiro não está disponível neste horário.");
        }
    }

    public List<LocalDateTime> listarHorariosDisponiveis(Long barbeiroId, LocalDateTime data, Long servicoId) {
        Barbeiro barbeiro = barbeiroRepository.findById(barbeiroId)
                .orElseThrow(() -> new RuntimeException("Barbeiro não encontrado"));
        Servico servico = servicoRepository.findById(servicoId)
                .orElseThrow(() -> new RuntimeException("Serviço não encontrado"));

        // Lista de horários disponíveis
        List<LocalDateTime> horariosDisponiveis = new ArrayList<>();
        LocalDateTime inicio = data.withHour(9).withMinute(0).withSecond(0).withNano(0); // Começo do expediente
        LocalDateTime fim = data.withHour(19).withMinute(0).withSecond(0).withNano(0); // Fim do expediente

        // Pausa para almoço das 12h às 13h
        LocalDateTime inicioAlmoco = data.withHour(12).withMinute(0);
        LocalDateTime fimAlmoco = data.withHour(13).withMinute(0);

        // Verifica os horários disponíveis
        while (inicio.isBefore(fim)) {
            if (inicio.isBefore(inicioAlmoco) || inicio.isAfter(fimAlmoco)) {
                // Verifica se o barbeiro está disponível neste horário
                List<Agendamento> conflitos = agendamentoRepository.findByBarbeiroAndDataHoraBetween(barbeiro, inicio, inicio.plusMinutes(servico.getDuracaoEmMinutos()));
                if (conflitos.isEmpty()) {
                    horariosDisponiveis.add(inicio);
                }
            }
            inicio = inicio.plusHours(1); // Incrementa 1 hora para o próximo horário
        }

        return horariosDisponiveis;
    }
    // Verificar disponibilidade de horários do barbeiro
    public List<LocalDateTime> verificarDisponibilidadeBarbeiro(Long barbeiroId, LocalDateTime inicio, LocalDateTime fim) {
        // Lógica para verificar os horários disponíveis do barbeiro
        Barbeiro barbeiro = barbeiroRepository.findById(barbeiroId)
                .orElseThrow(() -> new RuntimeException("Barbeiro não encontrado"));

        List<Agendamento> agendamentosExistentes = agendamentoRepository.findByBarbeiroAndDataHoraBetween(barbeiro, inicio, fim);

        // Lógica para gerar a lista de horários disponíveis (baseado nos agendamentos existentes)
        List<LocalDateTime> horariosDisponiveis = new ArrayList<>();
        // Algoritmo para preencher a lista de horários disponíveis (você pode usar a lógica que achar mais adequada)

        return horariosDisponiveis;
    }

    public boolean verificarAgendamentosCliente(Long clienteId, LocalDateTime data) {
        // Buscar o cliente pelo ID
        Cliente cliente = clienteRepository.findById(clienteId)
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado"));

        // Definir o início e fim do dia
        LocalDateTime inicioDoDia = data.toLocalDate().atStartOfDay();
        LocalDateTime fimDoDia = inicioDoDia.plusDays(1).minusSeconds(1);

        // Buscar os agendamentos do cliente no intervalo de tempo
        List<Agendamento> agendamentos = agendamentoRepository.findByClienteAndDataHoraBetween(cliente, inicioDoDia, fimDoDia);

        // Verificar se o cliente já tem agendamento no dia
        return !agendamentos.isEmpty(); // Se a lista não estiver vazia, o cliente já tem agendamento no dia
    }



    public List<AgendamentoResponseDTO> listarAgendamentosPorCliente(Long clienteId) {
        // Buscar o cliente pelo ID
        Cliente cliente = clienteRepository.findById(clienteId)
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado"));

        // Buscar os agendamentos do cliente no intervalo de um ano
        List<Agendamento> agendamentos = agendamentoRepository.findByClienteAndDataHoraBetween(cliente, LocalDateTime.now(), LocalDateTime.now().plusYears(1));

        // Convertendo para DTO
        return agendamentos.stream()
                .map(AgendamentoResponseDTO::fromEntity)
                .collect(Collectors.toList());
    }

}




