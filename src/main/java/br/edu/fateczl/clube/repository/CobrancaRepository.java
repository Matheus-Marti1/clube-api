package br.edu.fateczl.clube.repository;

import br.edu.fateczl.clube.model.Cobranca;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface CobrancaRepository extends JpaRepository<Cobranca, Long> {
    @Query("SELECT c FROM Cobranca c WHERE c.associado.id = :associadoId AND c.pago = false AND c.dataVencimento < :dataAtual")
    List<Cobranca> findCobrancasVencidasNaoPagas(
            @Param("associadoId") Long associadoId,
            @Param("dataAtual") Date dataAtual
    );
}
