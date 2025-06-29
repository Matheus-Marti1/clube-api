package br.edu.fateczl.clube.dto;

import br.edu.fateczl.clube.model.Endereco;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Schema(description = "DTO para atualizar dados de um associado")
public class AssociadoUpdateDTO {
    @Schema(description = "Nome completo do associado.", example = "Carlos Alberto da Silva")
    private String nome;
    @Schema(description = "Novo telefone residencial.", example = "1122223333")
    private String telResidencial;
    private String telComercial;
    private String celular;
    private Endereco endereco;

}
