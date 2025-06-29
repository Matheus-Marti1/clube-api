package br.edu.fateczl.clube.controller;

import br.edu.fateczl.clube.dto.ReservaDTO;
import br.edu.fateczl.clube.model.Reserva;
import br.edu.fateczl.clube.service.ReservaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Reservas")
@RestController
@RequestMapping("/api/v1/reservas")
public class ReservaController {

    private final ReservaService reservaService;

    public ReservaController(ReservaService reservaService) {
        this.reservaService = reservaService;
    }

    @Operation(summary = "Cria uma nova reserva")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Reserva criada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Regra de negócio violada (ex: item indisponível, associado bloqueado)")
    })
    @PostMapping
    public ResponseEntity<Reserva> criarReserva(@RequestBody ReservaDTO reservaDTO) {
        Reserva novaReserva = reservaService.criarReserva(
                reservaDTO.associadoId(),
                reservaDTO.itemReservavelId(),
                reservaDTO.dataReserva()
        );
        return ResponseEntity.status(201).body(novaReserva);
    }


    @Operation(summary = "Lista todas as reservas", description = "Retorna uma lista de todas as reservas do sistema. Ideal para painéis administrativos.")
    @ApiResponse(responseCode = "200", description = "Lista de reservas retornada com sucesso")
    @GetMapping
    public ResponseEntity<List<Reserva>> getAllReservas() {
        List<Reserva> reservas = reservaService.listarTodas();
        return ResponseEntity.ok(reservas);
    }


    @Operation(summary = "Busca uma reserva por ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Reserva encontrada"),
            @ApiResponse(responseCode = "400", description = "Reserva não encontrada")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Reserva> getReservaById(@PathVariable Long id) {
        Reserva reserva = reservaService.buscarPorId(id);
        return ResponseEntity.ok(reserva);
    }

    @Operation(summary = "Cancela (deleta) uma reserva existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Reserva cancelada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Reserva não encontrada")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> cancelarReserva(@PathVariable Long id) {
        reservaService.deletarReserva(id);
        return ResponseEntity.noContent().build();
    }
}
