package br.edu.fateczl.clube.controller;

import br.edu.fateczl.clube.dto.DependenteDTO;
import br.edu.fateczl.clube.model.Dependente;
import br.edu.fateczl.clube.service.DependenteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/dependentes")
@Tag(name = "Dependentes")
public class DependenteController {

    private final DependenteService dependenteService;

    public DependenteController(DependenteService dependenteService) {
        this.dependenteService = dependenteService;
    }

    @Operation(summary = "Atualiza os dados de um dependente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Dependente atualizado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dependente não encontrado")
    })
    @PutMapping("/{id}")
    public ResponseEntity<Dependente> updateDependente(
            @Parameter(description = "ID do dependente a ser atualizado") @PathVariable Long id,
            @RequestBody DependenteDTO dependenteDTO) {

        Dependente dependenteAtualizado = dependenteService.atualizarDependente(id, dependenteDTO);
        return ResponseEntity.ok(dependenteAtualizado);
    }

    @Operation(summary = "Deleta um dependente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Dependente deletado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dependente não encontrado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDependente(
            @Parameter(description = "ID do dependente a ser deletado") @PathVariable Long id) {

        dependenteService.deletarDependente(id);
        return ResponseEntity.noContent().build();
    }
}
