package br.edu.fateczl.clube.service;

import br.edu.fateczl.clube.dto.DependenteDTO;
import br.edu.fateczl.clube.exception.RegraNegocioException;
import br.edu.fateczl.clube.model.Dependente;
import br.edu.fateczl.clube.repository.DependenteRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class DependenteService {
    private final DependenteRepository dependenteRepository;

    public DependenteService(DependenteRepository dependenteRepository) {
        this.dependenteRepository = dependenteRepository;
    }

    @Transactional
    public Dependente atualizarDependente(Long id, DependenteDTO dto) {
        Dependente dependente = dependenteRepository.findById(id)
                .orElseThrow(() -> new RegraNegocioException("Dependente com ID " + id + " não encontrado."));

        dependente.setNomeCompleto(dto.nomeCompleto());
        dependente.setRg(dto.rg());

        return dependenteRepository.save(dependente);
    }

    @Transactional
    public void deletarDependente(Long id) {
        if (!dependenteRepository.existsById(id)) {
            throw new RegraNegocioException("Dependente com ID " + id + " não encontrado.");
        }
        dependenteRepository.deleteById(id);
    }
}
