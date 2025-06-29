package br.edu.fateczl.clube.service;

import br.edu.fateczl.clube.dto.AssociadoUpdateDTO;
import br.edu.fateczl.clube.dto.DependenteDTO;
import br.edu.fateczl.clube.exception.RegraNegocioException;
import br.edu.fateczl.clube.model.Associado;
import br.edu.fateczl.clube.model.Cobranca;
import br.edu.fateczl.clube.model.Dependente;
import br.edu.fateczl.clube.model.StatusAssociado;
import br.edu.fateczl.clube.repository.AssociadoRepository;
import br.edu.fateczl.clube.repository.CobrancaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class AssociadoService {

    private final AssociadoRepository associadoRepository;
    private final CobrancaRepository cobrancaRepository;

    public AssociadoService(AssociadoRepository associadoRepository, CobrancaRepository cobrancaRepository) {
        this.associadoRepository = associadoRepository;
        this.cobrancaRepository = cobrancaRepository;
    }

    @Transactional
    public Associado salvarAssociado(Associado associado) {
        if (associado.getId() == null && associadoRepository.findByCpf(associado.getCpf()).isPresent()) {
            throw new RegraNegocioException("CPF " + associado.getCpf() + " já está cadastrado.");
        }
        if (associado.getDependentes() != null) {
            associado.getDependentes().forEach(dependente -> dependente.setAssociado(associado));
        }
        return associadoRepository.save(associado);
    }

    public Page<Associado> listarTodos(Pageable pageable) {
        return associadoRepository.findAll(pageable);
    }

    public Associado buscarPorId(Long id) {
        return associadoRepository.findById(id)
                .orElseThrow(() -> new RegraNegocioException("Associado com ID " + id + " não encontrado."));
    }

    @Transactional
    public void atualizarStatusAssociado(Long associadoId) {
        Associado associado = buscarPorId(associadoId);
        List<Cobranca> vencidas = cobrancaRepository.findCobrancasVencidasNaoPagas(associadoId, new Date());

        StatusAssociado novoStatus = StatusAssociado.ATIVO;
        if (vencidas.size() >= 4) {
            novoStatus = StatusAssociado.BLOQUEADO;
        } else if (vencidas.size() == 3) {
            novoStatus = StatusAssociado.RESTRITO_QUADRAS;
        } else if (vencidas.size() == 2) {
            novoStatus = StatusAssociado.RESTRITO_PISCINA_GOLFE_HARAS;
        }

        if (associado.getStatus() != novoStatus) {
            associado.setStatus(novoStatus);
            associadoRepository.save(associado);
        }
    }

    @Transactional
    public Associado atualizarAssociado(Long id, AssociadoUpdateDTO dto) {
        Associado associado = buscarPorId(id);

        associado.setNome(dto.getNome());
        associado.setTelResidencial(dto.getTelResidencial());
        associado.setTelComercial(dto.getTelComercial());
        associado.setCelular(dto.getCelular());

        if (dto.getEndereco() != null) {
            dto.getEndereco().setId(associado.getEndereco() != null ? associado.getEndereco().getId() : null);
            associado.setEndereco(dto.getEndereco());
        }

        return associadoRepository.save(associado);
    }

    @Transactional
    public void deletarAssociado(Long id) {
        if (!associadoRepository.existsById(id)) {
            throw new RegraNegocioException("Associado com ID " + id + " não encontrado para exclusão.");
        }
        associadoRepository.deleteById(id);
    }

    @Transactional
    public Dependente adicionarDependente(Long associadoId, DependenteDTO dependenteDTO) {
        Associado associado = buscarPorId(associadoId);

        Dependente novoDependente = new Dependente();
        novoDependente.setNomeCompleto(dependenteDTO.nomeCompleto());
        novoDependente.setRg(dependenteDTO.rg());
        novoDependente.setAssociado(associado);
        associado.getDependentes().add(novoDependente);
        associadoRepository.save(associado);

        return novoDependente;
    }
}