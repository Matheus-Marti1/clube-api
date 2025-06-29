package br.edu.fateczl.clube.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "salao_de_festa") public class SalaoDeFesta extends ItemReservavel {
    private int capacidade;
    public SalaoDeFesta() {}
    @Override public int getCapacidadeMaxima() { return this.capacidade; }

}
