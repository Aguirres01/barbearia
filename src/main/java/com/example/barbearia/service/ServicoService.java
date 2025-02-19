package com.example.barbearia.service;

import com.example.barbearia.dto.ServicoDTO;
import com.example.barbearia.models.Servico;
import com.example.barbearia.repository.ServicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ServicoService {

    @Autowired
    private ServicoRepository servicoRepository;

    public Servico cadastrarServico(ServicoDTO dto) {
        Servico servico = new Servico();
        servico.setNome(dto.getNome());
        servico.setPreco(dto.getPreco());
        servico.setDuracaoEmMinutos(dto.getDuracaoEmMinutos());

        return servicoRepository.save(servico);
    }
}
