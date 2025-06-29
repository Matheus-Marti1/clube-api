package br.edu.fateczl.clube.config;

import br.edu.fateczl.clube.model.Churrasqueira;
import br.edu.fateczl.clube.model.PasseioHaras;
import br.edu.fateczl.clube.model.SalaoDeFesta;
import br.edu.fateczl.clube.model.Trilha;
import br.edu.fateczl.clube.repository.ItemReservavelRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class DataInitializer implements CommandLineRunner {

    private final ItemReservavelRepository itemReservavelRepository;

    public DataInitializer(ItemReservavelRepository itemReservavelRepository) {
        this.itemReservavelRepository = itemReservavelRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        if (itemReservavelRepository.count() == 0) {
            System.out.println("Nenhum item reservável encontrado. Populando o banco de dados...");

            Churrasqueira churrasqueira = new Churrasqueira();
            churrasqueira.setNome("Churrasqueira Principal");
            churrasqueira.setNumero(1);

            SalaoDeFesta salao = new SalaoDeFesta();
            salao.setNome("Salão de Festa Nobre");
            salao.setCapacidade(100);

            PasseioHaras haras = new PasseioHaras();
            haras.setNome("Passeio a Cavalo - Turma da Manhã");

            Trilha trilha = new Trilha();
            trilha.setNome("Trilha da Cachoeira");

            itemReservavelRepository.saveAll(Arrays.asList(churrasqueira, salao, haras, trilha));

            System.out.println("Banco de dados populado com itens reserváveis. IDs gerados: 1, 2, 3, 4.");
        } else {
            System.out.println("Itens reserváveis já existem no banco de dados. Nenhuma ação necessária.");
        }
    }
}
