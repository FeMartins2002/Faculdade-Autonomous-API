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
import com.github.javafaker.Faker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

import static br.com.AutonomousAPI.services.utils.Hash.generateHash;
import static br.com.AutonomousAPI.services.utils.PasswordGenerator.generateDefaultPassword;

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

        // Inicializa quantidade para testes de sobrecarga
        int totalStores = 1;
        int totalFreelancers = 3;
        int totalScaleCompleted = 2;
        int totalScaleCreated = 2;
        int totalScaleCancelled = 2;


        Faker faker = new Faker(new Locale("pt-BR"));
        Random random = new Random();

        //============================== Initialize Managers ==============================//

        Manager manager1 = new Manager("Felipe", "felipe123@gmail.com", "11967762246", generateHash("123"), Role.ROLE_MANAGER);
        Manager manager2 = new Manager("Marcelo", "marcelo123@gmail.com", "11973218523", generateHash("321"), Role.ROLE_USER);

        managerRepository.saveAll(Arrays.asList(manager1, manager2));

        //============================== Initialize Stores ==============================//

        List<Store> stores = new ArrayList<>();

        Store store1 = new Store("Moema", "11962541234", "Rua Moema, 123", manager1);
        Store store2 = new Store("Oscar Freire", "11983446576", "Rua Paulista, 123", manager1);
        stores.add(store1);
        stores.add(store2);

        for (int i = 0; i < totalStores; i++) {
            Store store = new Store(
                    faker.address().cityName(),
                    faker.phoneNumber().cellPhone(),
                    faker.address().streetAddress(),
                    manager1
            );
            stores.add(store);
        }

        storeRepository.saveAll(stores);

        //============================== Initialize Freelancers ==============================//

        List<Freelancer> freelancers = new ArrayList<>();

        Freelancer freelancer1 = new Freelancer("56612985432", "Maria", "maria123@gmail.com", "11923451123", "123", manager1);
        freelancers.add(freelancer1);

        for (int i = 0; i < totalFreelancers; i++) {
            Freelancer freelancer = new Freelancer(
                    faker.number().digits(11),
                    faker.name().firstName(),
                    faker.internet().emailAddress(),
                    faker.phoneNumber().cellPhone(),
                    generateHash(generateDefaultPassword()),
                    manager1
            );
            freelancers.add(freelancer);
        }

        freelancerRepository.saveAll(freelancers);

        //============================== Initialize Scales ==============================//

        List<Scale> scales = new ArrayList<>();

        Scale scale1 = new Scale(
                150,
                LocalDate.parse("2026-05-15"),
                LocalTime.parse("13:00"),
                LocalTime.parse("20:00"),
                freelancer1,
                manager1,
                store1
        );

        Scale scale2 = new Scale(
                200,
                LocalDate.parse("2026-08-21"),
                LocalTime.parse("13:00"),
                LocalTime.parse("22:00"),
                freelancer1,
                manager1,
                store2
        );

        Scale scale3 = new Scale(
                150,
                LocalDate.parse("2026-08-22"),
                LocalTime.parse("08:00"),
                LocalTime.parse("17:00"),
                freelancer1,
                manager1,
                store2
        );
        scale1.setScaleStatus(ScaleStatus.CRIADO);
        scale2.setScaleStatus(ScaleStatus.CONCLUIDO);
        scale3.setScaleStatus(ScaleStatus.CANCELADO);

        scales.add(scale1);
        scales.add(scale2);
        scales.add(scale3);

        // CONCLUIDO
        for (int i = 0; i < totalScaleCompleted; i++) {

            LocalTime startTime = LocalTime.of(
                    faker.number().numberBetween(7, 10),
                    0
            );

            LocalTime endTime = startTime.plusHours(8);

            Scale scale = new Scale(
                    faker.number().numberBetween(100, 300),
                    LocalDate.now().minusDays(random.nextInt(30)),
                    startTime,
                    endTime,
                    freelancers.get(random.nextInt(freelancers.size())),
                    manager1,
                    stores.get(random.nextInt(stores.size()))
            );

            scale.setScaleStatus(ScaleStatus.CONCLUIDO);
            scales.add(scale);
        }

        // CRIADO
        for (int i = 0; i < totalScaleCreated; i++) {

            LocalTime startTime = LocalTime.of(
                    faker.number().numberBetween(7, 10),
                    0
            );

            LocalTime endTime = startTime.plusHours(8);

            Scale scale = new Scale(
                    faker.number().numberBetween(100, 300),
                    LocalDate.now().plusDays(random.nextInt(30)),
                    startTime,
                    endTime,
                    freelancers.get(random.nextInt(freelancers.size())),
                    manager1,
                    stores.get(random.nextInt(stores.size()))
            );

            scale.setScaleStatus(ScaleStatus.CRIADO);
            scales.add(scale);
        }

        // CANCELADO
        for (int i = 0; i < totalScaleCancelled; i++) {

            LocalTime startTime = LocalTime.of(
                    faker.number().numberBetween(7, 10),
                    0
            );

            LocalTime endTime = startTime.plusHours(8);

            Scale scale = new Scale(
                    faker.number().numberBetween(100, 300),
                    LocalDate.now().plusDays(random.nextInt(60)),
                    startTime,
                    endTime,
                    freelancers.get(random.nextInt(freelancers.size())),
                    manager1,
                    stores.get(random.nextInt(stores.size()))
            );

            scale.setScaleStatus(ScaleStatus.CANCELADO);
            scales.add(scale);
        }

        scaleRepository.saveAll(scales);


        String VERDE = "\u001B[32m";
        String RESET = "\u001B[0m";
        System.out.println(VERDE + "==================== SERVIDOR INICIADO COM SUCESSO! ====================" + RESET);
    }
}
