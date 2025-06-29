package br.edu.fateczl.clube.service;

import br.edu.fateczl.clube.exception.RegraNegocioException;
import br.edu.fateczl.clube.model.*;
import br.edu.fateczl.clube.repository.AssociadoRepository;
import br.edu.fateczl.clube.repository.ItemReservavelRepository;
import br.edu.fateczl.clube.repository.ReservaRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class ReservaService {

    private final ReservaRepository reservaRepository;
    private final AssociadoRepository associadoRepository;
    private final ItemReservavelRepository itemReservavelRepository;

    public ReservaService(ReservaRepository reservaRepository, AssociadoRepository associadoRepository, ItemReservavelRepository itemReservavelRepository) {
        this.reservaRepository = reservaRepository;
        this.associadoRepository = associadoRepository;
        this.itemReservavelRepository = itemReservavelRepository;
    }

    @Transactional
    public Reserva criarReserva(Long associadoId, Long itemReservavelId, Date dataReserva) {
        Associado associado = associadoRepository.findById(associadoId)
                .orElseThrow(() -> new RegraNegocioException("Associado não encontrado."));

        ItemReservavel item = itemReservavelRepository.findById(itemReservavelId)
                .orElseThrow(() -> new RegraNegocioException("Item para reserva não encontrado."));

        validarPermissaoDeReserva(associado.getStatus(), item);

        List<Reserva> existentes = reservaRepository.findByItemReservadoIdAndDataReserva(itemReservavelId, dataReserva);
        if (item instanceof Churrasqueira || item instanceof SalaoDeFesta) {
            if (!existentes.isEmpty()) {
                throw new RegraNegocioException("Este item já está reservado para a data selecionada.");
            }
        } else {
            if (existentes.size() >= item.getCapacidadeMaxima()) {
                throw new RegraNegocioException("Não há mais vagas para esta atividade na data selecionada.");
            }
        }

        Reserva novaReserva = new Reserva();
        novaReserva.setAssociado(associado);
        novaReserva.setItemReservado(item);
        novaReserva.setDataReserva(dataReserva);
        return reservaRepository.save(novaReserva);
    }

    public Reserva buscarPorId(Long reservaId) {
        return reservaRepository.findById(reservaId)
                .orElseThrow(() -> new RegraNegocioException("Reserva com ID " + reservaId + " não encontrada."));
    }

    public List<Reserva> listarTodas() {
        return reservaRepository.findAll();
    }

    private void validarPermissaoDeReserva(StatusAssociado status, ItemReservavel item) {
        if (status == StatusAssociado.BLOQUEADO) {
            throw new RegraNegocioException("Associado bloqueado não pode realizar reservas.");
        }

        boolean isItemRestritoHarasGolfe = item instanceof PasseioHaras; // Adicionar Golfe aqui quando existir
        if (status == StatusAssociado.RESTRITO_PISCINA_GOLFE_HARAS && isItemRestritoHarasGolfe) {
            throw new RegraNegocioException("Associado com pendências não pode reservar haras ou campo de golfe.");
        }

        boolean isAreaLivre = item instanceof Churrasqueira || item instanceof SalaoDeFesta;
        if (status == StatusAssociado.RESTRITO_QUADRAS && !isAreaLivre) {
            throw new RegraNegocioException("Associado com pendências avançadas só pode reservar churrasqueiras e salões de festa.");
        }
    }

    @Transactional
    public void deletarReserva(Long reservaId) {
        if (!reservaRepository.existsById(reservaId)) {
            throw new RegraNegocioException("Reserva com ID " + reservaId + " não encontrada.");
        }
        reservaRepository.deleteById(reservaId);
    }
}
