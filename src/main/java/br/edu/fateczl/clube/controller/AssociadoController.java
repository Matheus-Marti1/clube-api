package br.edu.fateczl.clube.controller;

import br.edu.fateczl.clube.dto.AssociadoUpdateDTO;
import br.edu.fateczl.clube.dto.DependenteDTO;
import br.edu.fateczl.clube.model.Associado;
import br.edu.fateczl.clube.model.Dependente;
import br.edu.fateczl.clube.service.AssociadoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Associados")
@RestController
@RequestMapping("/api/v1/associados")
public class AssociadoController {

    private final AssociadoService associadoService;

    public AssociadoController(AssociadoService associadoService) {
        this.associadoService = associadoService;
    }

    @Operation(summary = "Cria um novo associado", description = "Cadastra um associado, seu endereço e dependentes.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Associado criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Erro de validação ou regra de negócio (ex: CPF já cadastrado)")
    })
    @PostMapping
    public ResponseEntity<Associado> createAssociado(@RequestBody Associado associado) {
        Associado novoAssociado = associadoService.salvarAssociado(associado);
        return new ResponseEntity<>(novoAssociado, HttpStatus.CREATED);
    }

    @GetMapping
    @Operation(summary = "Lista todos os associados de forma paginada")
    @ApiResponse(responseCode = "200", description = "Lista de associados retornada com sucesso")
    public ResponseEntity<Page<Associado>> getAllAssociados(Pageable pageable) {
        Page<Associado> associados = associadoService.listarTodos(pageable);
        return ResponseEntity.ok(associados);
    }

    @Operation(summary = "Busca um associado por ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Associado encontrado"),
            @ApiResponse(responseCode = "400", description = "Associado não encontrado (lançado pela RegraNegocioException)")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Associado> getAssociadoById(@PathVariable Long id) {
        Associado associado = associadoService.buscarPorId(id);
        return ResponseEntity.ok(associado);
    }

    @Operation(summary = "Atualiza os dados de um associado existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Associado atualizado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Associado não encontrado")
    })
    @PutMapping("/{id}")
    public ResponseEntity<Associado> updateAssociado(@PathVariable Long id, @RequestBody AssociadoUpdateDTO dto) {
        Associado associadoAtualizado = associadoService.atualizarAssociado(id, dto);
        return ResponseEntity.ok(associadoAtualizado);
    }

    @Operation(summary = "Deleta um associado permanentemente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Associado deletado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Associado não encontrado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAssociado(@PathVariable Long id) {
        associadoService.deletarAssociado(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Adiciona um novo dependente a um associado")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Dependente adicionado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Associado não encontrado")
    })
    @PostMapping("/{associadoId}/dependentes")
    public ResponseEntity<Dependente> addDependente(
            @Parameter(description = "ID do associado pai") @PathVariable Long associadoId,
            @RequestBody DependenteDTO dependenteDTO) {

        Dependente novoDependente = associadoService.adicionarDependente(associadoId, dependenteDTO);
        return new ResponseEntity<>(novoDependente, HttpStatus.CREATED);
    }
}

