package br.edu.fateczl.clube.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "API de Gerenciamento de Clube",
                version = "1.0",
                description = "API RESTful para gerenciar associados, cobranças e reservas de um clube de campo."
        ),
        tags = {
                @Tag(name = "Associados", description = "Operações para gerenciamento de associados"),
                @Tag(name = "Dependentes", description = "Operações para gerenciar dependentes individualmente"),
                @Tag(name = "Cobranças e Pagamentos", description = "Operações para gerar cobranças e registrar pagamentos"),
                @Tag(name = "Reservas", description = "Operações para criar, consultar e cancelar reservas")
        }
)
public class OpenApiConfig {
}