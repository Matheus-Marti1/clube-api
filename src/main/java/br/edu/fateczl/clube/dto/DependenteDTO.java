package br.edu.fateczl.clube.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "DTO para criar ou atualizar um dependente")
public record DependenteDTO(
        @Schema(description = "Nome completo do dependente.", example = "Maria Silva")
        String nomeCompleto,

        @Schema(description = "Número do RG do dependente.", example = "55.666.777-8")
        String rg
) {}
