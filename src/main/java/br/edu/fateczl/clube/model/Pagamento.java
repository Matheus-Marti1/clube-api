package br.edu.fateczl.clube.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
@Entity
@Table(name = "pagamento")
public class Pagamento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private double valorPago;
    private String formaPagamento;
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataPagamento;

    public Pagamento() {}

}