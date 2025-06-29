package br.edu.fateczl.clube.controller;

import br.edu.fateczl.clube.dto.PagamentoDTO;
import br.edu.fateczl.clube.model.Pagamento;
import br.edu.fateczl.clube.service.CobrancaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@Tag(name = "Cobranças e Pagamentos")
@RestController
@RequestMapping("/api/v1/cobrancas")
public class CobrancaController {

    private final CobrancaService cobrancaService;

    public CobrancaController(CobrancaService cobrancaService) {
        this.cobrancaService = cobrancaService;
    }

    @Operation(summary = "Gera as cobranças mensais para todos os associados", description = "Ideal para ser chamada por uma rotina agendada (Scheduler).")
    @ApiResponse(responseCode = "200", description = "Processo de geração de cobranças concluído")
    @PostMapping("/gerar-mensal")
    public ResponseEntity<String> gerarCobrancas() {
        cobrancaService.gerarCobrancasMensais();
        return ResponseEntity.ok("Geração de cobranças mensais concluída.");
    }

    @Operation(summary = "Registra o pagamento de uma cobrança")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Pagamento registrado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Regra de negócio violada (ex: cobrança não encontrada ou já paga)")
    })
    @PostMapping("/{cobrancaId}/pagar")
    public ResponseEntity<String> pagarCobranca(@PathVariable Long cobrancaId, @RequestBody PagamentoDTO pagamentoDTO) {
        Pagamento pagamento = new Pagamento();
        pagamento.setFormaPagamento(pagamentoDTO.formaPagamento());
        pagamento.setValorPago(pagamentoDTO.valorPago());
        pagamento.setDataPagamento(new Date());

        cobrancaService.realizarPagamento(cobrancaId, pagamento);
        return ResponseEntity.ok("Pagamento da cobrança " + cobrancaId + " realizado com sucesso.");
    }
}
