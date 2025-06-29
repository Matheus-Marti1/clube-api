package br.edu.fateczl.clube.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "churrasqueira") public class Churrasqueira extends ItemReservavel {
    private int numero;
    public Churrasqueira() {}
    @Override public int getCapacidadeMaxima() { return 20; }

}
