package br.edu.fateczl.clube.repository;

import br.edu.fateczl.clube.model.ItemReservavel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ItemReservavelRepository extends JpaRepository<ItemReservavel, Long> {

    Optional<ItemReservavel> findByNome(String nome);
}
