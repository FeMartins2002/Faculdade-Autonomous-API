package br.com.AutonomousAPI.services.utils;

import br.com.AutonomousAPI.entities.*;
import br.com.AutonomousAPI.enums.PointType;
import br.com.AutonomousAPI.enums.Role;
import br.com.AutonomousAPI.enums.ScaleStatus;
import br.com.AutonomousAPI.repositories.*;
import com.github.javafaker.Faker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;

import static br.com.AutonomousAPI.services.utils.Hash.generateHash;

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

    @Autowired
    private PointRepository pointRepository;

    @Override
    public void run(String... args) throws Exception {

        // Inicializa quantidade para testes de sobrecarga
        int totalFreelancers = 5;

        int totalScalesCompletedNow = 100; // 2026
        int totalScalesCompletedLast = 300; // Outros

        int totalScaleCreated = 8;

        int totalScaleCancelledNow = 20; // 2026
        int totalScaleCancelledLast = 200; // Outros


        Faker faker = new Faker(new Locale("pt-BR"));
        Random random = new Random();

        //============================== Initialize Managers ==============================//

        Manager manager1 = new Manager("Felipe", "felipe123@gmail.com", "11967762246", generateHash("123"), Role.ROLE_MANAGER);
        Manager manager2 = new Manager("Maycon", "maycon123@gmail.com", "11973218523", generateHash("123"), Role.ROLE_USER);
        Manager manager3 = new Manager("Jonathan", "jonathan123@gmail.com", "11982324350", generateHash("123"), Role.ROLE_USER);

        managerRepository.saveAll(Arrays.asList(manager1, manager2, manager3));

        //============================== Initialize Stores ==============================//

        List<Store> stores = new ArrayList<>();

        Store store1 = new Store("Moema", "11962541234", "Rua Moema, 123", manager1);
        Store store2 = new Store("Oscar Freire", "11983446576", "Rua Paulista, 321", manager1);
        Store store3 = new Store("Tatuapé", "11973432120", "Rua Tatuapé, 456", manager1);
        Store store4 = new Store("Morumbi", "11967674431", "Rua Morumbi, 654", manager1);
        stores.add(store1);
        stores.add(store2);
        stores.add(store3);
        stores.add(store4);

        storeRepository.saveAll(stores);

        //============================== Initialize Freelancers ==============================//

        List<Freelancer> freelancers = new ArrayList<>();

        for (int i = 0; i < totalFreelancers; i++) {
            Freelancer freelancer = new Freelancer(
                    faker.number().digits(11),
                    faker.name().firstName(),
                    "",
                    "11" + faker.number().digits(9),
                    generateHash("123"),
                    manager1
            );
            freelancer.setEmail(freelancer.getName().toLowerCase() + "123@gmail.com");
            freelancers.add(freelancer);
        }

        freelancerRepository.saveAll(freelancers);

        //============================== Initialize Scales ==============================//

        Freelancer freelancer = new Freelancer(
                "61345364758",
                "Alice",
                "alice123@gmail.com",
                "11977782323",
                generateHash("123"),
                manager1
        );
        freelancerRepository.save(freelancer);

        Scale scaleDefault = new Scale(
                300,
                LocalDate.parse("2026-05-29"),
                LocalTime.parse("10:00"),
                LocalTime.parse("18:30"),
                freelancer,
                manager1,
                store1
        );
        scaleRepository.save(scaleDefault);

        List<Scale> scales = new ArrayList<>();

        // GERA ESCALAS COM STATUS (CONCLUIDO) COM A DATA ANTES DE 2026
        for (int i = 0; i < totalScalesCompletedLast; i++) {
            String[] years = {"2020", "2021", "2022", "2023", "2024", "2025"};
            String[] months = {"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12"};
            String dayText = String.valueOf(random.nextInt(10, 28));

            LocalDate scaleDate = LocalDate.parse(
                    years[random.nextInt(years.length)] + "-"
                            + months[random.nextInt(months.length)] + "-"
                            + dayText);

            LocalTime entryTime = LocalTime.parse("10:00");
            LocalTime exitTime = LocalTime.parse("17:00");

            Scale scale = new Scale(
                    faker.number().numberBetween(150, 300),
                    scaleDate,
                    entryTime,
                    exitTime,
                    freelancers.get(random.nextInt(freelancers.size())),
                    manager1,
                    stores.get(random.nextInt(stores.size()))
            );

            // RANDOM ENTRE -30 E +30 MIN
            int entryDelay = random.nextInt(-10, 15);
            int exitDelay = random.nextInt(-10, 15);

            LocalDateTime entryDateTime = LocalDateTime.of(scaleDate, entryTime.plusMinutes(entryDelay));
            LocalDateTime exitDateTime = LocalDateTime.of(scaleDate, exitTime.plusMinutes(exitDelay));

            Point pointEntry = new Point(PointType.ENTRY, entryDateTime, scale);
            Point pointExit = new Point(PointType.EXIT, exitDateTime, scale);

            scale.setScaleStatus(ScaleStatus.CONCLUIDO);
            scaleRepository.save(scale);

            pointRepository.saveAll(Arrays.asList(pointEntry, pointExit));

            scale.getPoints().add(pointEntry);
            scale.getPoints().add(pointExit);
            scaleRepository.save(scale);
        }

        // GERA ESCALAS COM STATUS (CONCLUIDO) COM A DATA DENTRO DE 2026 ATÉ MAIO
        for (int i = 0; i < totalScalesCompletedNow; i++) {
            String[] months = {"01", "02", "03", "04", "05"};
            String dayText = String.valueOf(random.nextInt(10, 24));

            LocalDate scaleDate = LocalDate.parse("2026-" + months[random.nextInt(months.length)] + "-" + dayText);
            LocalTime entryTime = LocalTime.parse("10:00");
            LocalTime exitTime = LocalTime.parse("17:00");

            Scale scale = new Scale(
                    faker.number().numberBetween(150, 300),
                    scaleDate,
                    entryTime,
                    exitTime,
                    freelancers.get(random.nextInt(freelancers.size())),
                    manager1,
                    stores.get(random.nextInt(stores.size()))
            );

            // RANDOM ENTRE -30 E +30 MIN
            int entryDelay = random.nextInt(-30, 31);
            int exitDelay = random.nextInt(-30, 31);

            LocalDateTime entryDateTime = LocalDateTime.of(scaleDate, entryTime.plusMinutes(entryDelay));
            LocalDateTime exitDateTime = LocalDateTime.of(scaleDate, exitTime.plusMinutes(exitDelay));

            Point pointEntry = new Point(PointType.ENTRY, entryDateTime, scale);
            Point pointExit = new Point(PointType.EXIT, exitDateTime, scale);

            scale.setScaleStatus(ScaleStatus.CONCLUIDO);
            scaleRepository.save(scale);

            pointRepository.saveAll(Arrays.asList(pointEntry, pointExit));

            scale.getPoints().add(pointEntry);
            scale.getPoints().add(pointExit);
            scaleRepository.save(scale);
        }

        // GERA ESCALAS COM STATUS (CRIADO) COM A DATA DENTRO DE 2026
        for (int i = 0; i < totalScaleCreated; i++) {
            LocalTime startTime = LocalTime.of(faker.number().numberBetween(7, 10), 0);
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

        // GERA ESCALAS COM STATUS (CANCELADO) COM A DATA ANTES DE 2026
        for (int i = 0; i < totalScaleCancelledLast; i++) {
            String[] years = {"2020", "2021", "2022", "2023", "2024", "2025"};
            String[] mouths = {"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12"};
            String dayText = String.valueOf(random.nextInt(10, 28));

            Scale scale = new Scale(
                    faker.number().numberBetween(150, 300),
                    LocalDate.parse(years[random.nextInt(years.length)] + "-" + mouths[random.nextInt(mouths.length)] + "-" + dayText),
                    LocalTime.parse("08:00"),
                    LocalTime.parse("17:00"),
                    freelancers.get(random.nextInt(freelancers.size())),
                    manager1,
                    stores.get(random.nextInt(stores.size()))
            );

            String[] reason = {"Freelancer Desistiu", "Freelancer Faltou"};

            scale.setScaleObservation(reason[random.nextInt(reason.length)]);
            scale.setScaleStatus(ScaleStatus.CANCELADO);
            scales.add(scale);
        }

        // GERA ESCALAS COM STATUS (CANCELADO) COM A DATA DENTRO 2026
        for (int i = 0; i < totalScaleCancelledNow; i++) {
            String[] mouths = {"01", "02", "03", "04", "05"};
            String dayText = String.valueOf(random.nextInt(10, 23));

            Scale scale = new Scale(
                    faker.number().numberBetween(150, 300),
                    LocalDate.parse("2026-" + mouths[random.nextInt(mouths.length)] + "-" + dayText),
                    LocalTime.parse("08:00"),
                    LocalTime.parse("17:00"),
                    freelancers.get(random.nextInt(freelancers.size())),
                    manager1,
                    stores.get(random.nextInt(stores.size()))
            );

            String[] reason = {"Freelancer Desistiu", "Freelancer Faltou"};

            scale.setScaleObservation(reason[random.nextInt(reason.length)]);
            scale.setScaleStatus(ScaleStatus.CANCELADO);
            scales.add(scale);
        }

        scaleRepository.saveAll(scales);


        String VERDE = "\u001B[32m";
        String RESET = "\u001B[0m";

        System.out.println(scaleDefault.getFreelancer().getEmail());
        System.out.println(VERDE + "==================== SERVIDOR INICIADO COM SUCESSO! ====================" + RESET);
    }
}
