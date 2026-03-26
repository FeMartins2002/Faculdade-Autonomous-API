package br.com.AutonomousAPI.services.utils;

import br.com.AutonomousAPI.entities.Freelancer;
import br.com.AutonomousAPI.entities.Manager;
import br.com.AutonomousAPI.entities.Scale;
import br.com.AutonomousAPI.entities.Store;
import br.com.AutonomousAPI.enums.Role;
import br.com.AutonomousAPI.enums.ScaleStatus;
import br.com.AutonomousAPI.repositories.FreelancerRepository;
import br.com.AutonomousAPI.repositories.ManagerRepository;
import br.com.AutonomousAPI.repositories.ScaleRepository;
import br.com.AutonomousAPI.repositories.StoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Arrays;

@Service
public class InitializeDB implements CommandLineRunner {
    @Autowired
    private ManagerRepository managerRepository;

    @Autowired
    private StoreRepository storeRepository;

    @Autowired
    private FreelancerRepository freelancerRepository;

    @Autowired
    private ScaleRepository scaleRepository;

    @Override
    public void run(String... args) throws Exception {

        //============================== Initialize Managers ==============================//

        Manager manager1 = new Manager("Felipe", "felipe123@gmail.com", "11967762246", "123", Role.ROLE_MANAGER);
        Manager manager2 = new Manager("Marcelo", "marcelo123@gmail.com", "11973218523", "123", Role.ROLE_MANAGER);
        Manager manager3 = new Manager("Ricardo", "ricardo123@gmail.com", "11982594023", "123", Role.ROLE_USER);

        managerRepository.saveAll(Arrays.asList(manager1, manager2, manager3));

        //============================== Initialize Stores ==============================//

        Store store1 = new Store("Moema", "11962541234", "Rua Moema, 123", manager1);
        Store store2 = new Store("Paulista", "11983446576", "Rua Paulista, 123", manager2);
        Store store3 = new Store("Tatuapé", "11962431323", "Rua Tatuapé, 123", manager3);

        storeRepository.saveAll(Arrays.asList(store1, store2, store3));

        //============================== Initialize Freelancers ==============================//

        Freelancer freelancer1 = new Freelancer("56612985432", "Maria", "maria123@gmail.com", "11923451123", "123", manager1);
        Freelancer freelancer2 = new Freelancer("67212345678", "José", "jose123@mail.com", "11945632123", "123", manager1);
        Freelancer freelancer3 = new Freelancer("12456780123", "Carlos", "carlos123@gmail.com", "11912344321", "123", manager2);
        Freelancer freelancer4 = new Freelancer("87845347896", "Daniel", "daniel123@gmail.com", "11925467856", "123", manager2);
        Freelancer freelancer5 = new Freelancer("16723897654", "Roberto", "roberto123@gmail.com", "11982341122", "123", manager3);

        Freelancer freelancer6 = new Freelancer("87098765423", "Manoel", "manoel123@gmail.com", "11989893434", "123", manager1);
        freelancer6.setActive(false);

        Freelancer freelancer7 = new Freelancer("89045123456", "Fred", "fred123@gmail.com", "11986765454", "123", manager2);
        freelancer7.setActive(false);

        Freelancer freelancer8 = new Freelancer("90854321234", "Melissa", "melissa123@gmail.com", "11962325656", "123", manager2);
        freelancer8.setActive(false);

        Freelancer freelancer9 = new Freelancer("86854324354", "Monique", "monique123@gmail.com", "11967723323", "123", manager3);
        freelancer9.setActive(false);

        Freelancer freelancer10 = new Freelancer("01276432123", "Ben", "ben123@gmail.com", "11987112233", "123", manager3);
        freelancer10.setActive(false);

        freelancerRepository.saveAll(Arrays.asList(freelancer1, freelancer2, freelancer3, freelancer4, freelancer5, freelancer6,
                freelancer7, freelancer8, freelancer9, freelancer10));

        //============================== Initialize Scales ==============================//

        Scale scale1 = new Scale(150, LocalDateTime.parse("2026-02-20T13:00:00"), freelancer1, manager1, store1);
        Scale scale2 = new Scale(200, LocalDateTime.parse("2026-02-21T13:00:00"), freelancer2, manager1, store2);
        Scale scale3 = new Scale(150, LocalDateTime.parse("2026-02-22T13:00:00"), freelancer3, manager1, store3);
        scale1.setScaleStatus(ScaleStatus.CONCLUIDO);
        scale2.setScaleStatus(ScaleStatus.CONCLUIDO);
        scale3.setScaleStatus(ScaleStatus.CONCLUIDO);

        Scale scale4 = new Scale(150, LocalDateTime.parse("2026-03-29T13:00:00"), freelancer4, manager1, store2);
        Scale scale5 = new Scale(150, LocalDateTime.parse("2026-03-30T13:00:00"), freelancer5, manager1, store2);
        Scale scale6 = new Scale(200, LocalDateTime.parse("2026-03-31T13:00:00"), freelancer6, manager1, store3);
        scale4.setScaleStatus(ScaleStatus.CRIADO);
        scale5.setScaleStatus(ScaleStatus.CRIADO);
        scale6.setScaleStatus(ScaleStatus.CRIADO);

        Scale scale7 = new Scale(200, LocalDateTime.parse("2026-05-27T13:00:00"), freelancer1, manager1, store3);
        Scale scale8 = new Scale(200, LocalDateTime.parse("2026-05-28T13:00:00"), freelancer2, manager1, store2);
        Scale scale9 = new Scale(200, LocalDateTime.parse("2026-05-29T13:00:00"), freelancer3, manager1, store1);
        scale7.setScaleStatus(ScaleStatus.CANCELADO);
        scale8.setScaleStatus(ScaleStatus.CANCELADO);
        scale9.setScaleStatus(ScaleStatus.CANCELADO);

        scaleRepository.saveAll(Arrays.asList(scale1, scale2, scale3, scale4, scale5, scale6, scale7, scale8, scale9));

        System.out.println("==================== Banco inicializado com sucesso! ====================");
    }
}
