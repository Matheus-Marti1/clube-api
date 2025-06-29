package br.edu.fateczl.clube.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "passeio_haras") public class PasseioHaras extends ItemReservavel {
    public PasseioHaras() {}
    @Override public int getCapacidadeMaxima() { return 20; }
}
