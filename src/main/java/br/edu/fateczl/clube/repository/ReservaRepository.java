package br.edu.fateczl.clube.repository;

import br.edu.fateczl.clube.model.Reserva;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface ReservaRepository extends JpaRepository<Reserva, Long> {
    @Query("SELECT r FROM Reserva r WHERE r.itemReservado.id = :itemReservavelId AND FUNCTION('DATE', r.dataReserva) = FUNCTION('DATE', :data)")
    List<Reserva> findByItemReservadoIdAndDataReserva(
            @Param("itemReservavelId") Long itemReservavelId,
            @Param("data") Date data
    );
}
