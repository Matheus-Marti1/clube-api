package br.edu.fateczl.clube.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Setter
@Getter
@Entity
@Table(name = "associado")
public class Associado {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private String rg;
    @Column(unique = true)
    private String cpf;
    private String telResidencial;
    private String telComercial;
    private String celular;

    @Temporal(TemporalType.DATE)
    private Date dataCadastro;

    @Enumerated(EnumType.STRING)
    private TipoAssociado tipo;

    @Enumerated(EnumType.STRING)
    private StatusAssociado status = StatusAssociado.ATIVO;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "endereco_id", referencedColumnName = "id")
    private Endereco endereco;

    @OneToMany(mappedBy = "associado", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Dependente> dependentes;

    @OneToMany(mappedBy = "associado", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference("associado-cobrancas")
    private List<Cobranca> cobrancas;

    @OneToMany(mappedBy = "associado", cascade = CascadeType.ALL)
    @JsonManagedReference("associado-reservas")
    private List<Reserva> reservas;

    public Associado() {}

}
