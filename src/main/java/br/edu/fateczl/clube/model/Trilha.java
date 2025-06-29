package br.edu.fateczl.clube.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "trilha") public class Trilha extends ItemReservavel {
    public Trilha() {}
    @Override public int getCapacidadeMaxima() { return 30; }
}
