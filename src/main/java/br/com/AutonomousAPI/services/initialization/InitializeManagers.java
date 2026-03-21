package br.com.AutonomousAPI.services.initialization;

import br.com.AutonomousAPI.entities.Manager;
import br.com.AutonomousAPI.enums.Role;
import br.com.AutonomousAPI.repositories.ManagerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;

@Service
public class InitializeManagers implements CommandLineRunner {
    @Autowired
    private ManagerRepository managerRepository;

    @Override
    public void run(String... args) throws Exception {
        Manager manager1 = new Manager("Felipe", "felipe123@gmail.com", "11967762246", "123", Role.ROLE_MANAGER);
        Manager manager2 = new Manager("Marcelo", "marcelo123@gmail.com", "11973218523", "123", Role.ROLE_MANAGER);
        Manager manager3 = new Manager("Ricardo", "ricardo123@gmail.com", "11982594023", "123", Role.ROLE_USER);

        managerRepository.save(manager1);
        managerRepository.save(manager2);
        managerRepository.save(manager3);

        System.out.println("Managers inicializados com sucesso!");
    }
}