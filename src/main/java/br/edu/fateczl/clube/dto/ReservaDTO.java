package br.edu.fateczl.clube.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.Date;

@Schema(description = "DTO para criar uma nova reserva")
public record ReservaDTO(
        @Schema(description = "ID do associado que está fazendo a reserva.", example = "1")
        Long associadoId,

        @Schema(description = "ID do item a ser reservado (churrasqueira, salão, etc.).", example = "3")
        Long itemReservavelId,

        @Schema(description = "Data e hora da reserva.", example = "2025-08-10T09:00:00.000Z")
        Date dataReserva
) {}
