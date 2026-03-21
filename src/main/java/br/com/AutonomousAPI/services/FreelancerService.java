package br.com.AutonomousAPI.services;

import br.com.AutonomousAPI.dtos.request.freelancer.CreateFreelancerDTO;
import br.com.AutonomousAPI.entities.Freelancer;
import br.com.AutonomousAPI.entities.Manager;
import br.com.AutonomousAPI.exceptions.CpfAlreadyRegisteredException;
import br.com.AutonomousAPI.exceptions.EmailAlreadyRegisteredException;
import br.com.AutonomousAPI.exceptions.ManagerNotFoundException;
import br.com.AutonomousAPI.exceptions.PhoneAlreadyRegisteredException;
import br.com.AutonomousAPI.mappers.FreelancerMapper;
import br.com.AutonomousAPI.repositories.FreelancerRepository;
import br.com.AutonomousAPI.repositories.ManagerRepository;
import br.com.AutonomousAPI.services.utils.PasswordGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FreelancerService {
    @Autowired
    private FreelancerRepository freelancerRepository;

    @Autowired
    private FreelancerMapper freelancerMapper;

    @Autowired
    private ManagerRepository managerRepository;

    public void createFreelancer(CreateFreelancerDTO dto) {
        validateFreelancer(dto);
        Manager manager = findManager(dto.getManagerId());
        String generatedPassword = PasswordGenerator.generateDefaultPassword();

        Freelancer freelancer = freelancerMapper.toEntity(dto, manager, generatedPassword);
        freelancer.setActive(true);

        freelancerRepository.save(freelancer);
    }

    private void validateFreelancer(CreateFreelancerDTO freelancer) {
        if (validateCpf(freelancer.getCpf())) {
            throw new CpfAlreadyRegisteredException("CPF já cadastrado");
        }
        if (validateEmail(freelancer.getEmail())) {
            throw new EmailAlreadyRegisteredException("Email já cadastrado");
        }
        if (validatePhone(freelancer.getPhone())) {
            throw new PhoneAlreadyRegisteredException("Telefone já cadastrado");
        }
    }

    private boolean validateCpf(String cpf) {
        return freelancerRepository.findByCpf(cpf).isPresent();
    }

    private boolean validateEmail(String email) {
        return freelancerRepository.findByEmail(email).isPresent();
    }

    private boolean validatePhone(String phone) {
        return freelancerRepository.findByPhone(phone).isPresent();
    }

    private Manager findManager(Long managerId) {
        return managerRepository.findById(managerId)
                .orElseThrow(() -> new ManagerNotFoundException("Manager não encontrado"));
    }
}
