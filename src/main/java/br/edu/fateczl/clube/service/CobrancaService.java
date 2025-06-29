package br.edu.fateczl.clube.service;

import br.edu.fateczl.clube.exception.RegraNegocioException;
import br.edu.fateczl.clube.model.Associado;
import br.edu.fateczl.clube.model.Cobranca;
import br.edu.fateczl.clube.model.Pagamento;
import br.edu.fateczl.clube.repository.AssociadoRepository;
import br.edu.fateczl.clube.repository.CobrancaRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class CobrancaService {

    private final CobrancaRepository cobrancaRepository;
    private final AssociadoRepository associadoRepository;
    private final AssociadoService associadoService;

    public CobrancaService(CobrancaRepository cobrancaRepository, AssociadoRepository associadoRepository, AssociadoService associadoService) {
        this.cobrancaRepository = cobrancaRepository;
        this.associadoRepository = associadoRepository;
        this.associadoService = associadoService;
    }

    @Transactional
    public void gerarCobrancasMensais() {
        double valorMensalidade = 500.0;
        List<Associado> associados = associadoRepository.findAll();

        for (Associado associado : associados) {
            Cobranca novaCobranca = new Cobranca();
            novaCobranca.setAssociado(associado);
            novaCobranca.setValorBase(valorMensalidade);
            novaCobranca.setDataVencimento(calcularProximoVencimento());
            novaCobranca.setPago(false);
            cobrancaRepository.save(novaCobranca);
        }
    }

    @Transactional
    public void realizarPagamento(Long cobrancaId, Pagamento pagamento) {
        Cobranca cobranca = cobrancaRepository.findById(cobrancaId)
                .orElseThrow(() -> new RegraNegocioException("Cobrança não encontrada."));

        if (cobranca.isPago()) {
            throw new RegraNegocioException("Esta cobrança já foi paga.");
        }

        cobranca.setPago(true);
        cobranca.setPagamento(pagamento);
        cobrancaRepository.save(cobranca);
        associadoService.atualizarStatusAssociado(cobranca.getAssociado().getId());
    }

    private Date calcularProximoVencimento() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MONTH, 1);
        cal.set(Calendar.DAY_OF_MONTH, 10);
        return cal.getTime();
    }
}
