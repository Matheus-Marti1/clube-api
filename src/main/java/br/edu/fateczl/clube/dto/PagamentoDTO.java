package br.edu.fateczl.clube.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "DTO para registrar um pagamento")
public record PagamentoDTO(
        @Schema(description = "Forma como o pagamento foi realizado.", example = "PIX")
        String formaPagamento,

        @Schema(description = "Valor efetivamente pago.", example = "500.00")
        double valorPago
) {}
