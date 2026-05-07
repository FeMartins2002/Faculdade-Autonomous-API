package br.com.AutonomousAPI.services;

import br.com.AutonomousAPI.dtos.request.freelancer.CreateFreelancerDTO;
import br.com.AutonomousAPI.dtos.response.freelancer.FreelancerResponseDTO;
import br.com.AutonomousAPI.entities.Freelancer;
import br.com.AutonomousAPI.entities.Log;
import br.com.AutonomousAPI.entities.Manager;
import br.com.AutonomousAPI.enums.ActionType;
import br.com.AutonomousAPI.enums.LogStatus;
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

import java.util.List;

@Service
public class FreelancerService {
    @Autowired
    private FreelancerRepository freelancerRepository;

    @Autowired
    private FreelancerMapper freelancerMapper;

    @Autowired
    private ManagerRepository managerRepository;

    @Autowired
    private LogService logService;

    public List<FreelancerResponseDTO> freelancersActives() {
        List<Freelancer> freelancers = freelancerRepository.findByActiveTrueOrderByName();
        return freelancerMapper.toResponseList(freelancers);
    }

    public List<FreelancerResponseDTO> freelancersInactives() {
        List<Freelancer> freelancers = freelancerRepository.findByActiveFalseOrderByName();
        return freelancerMapper.toResponseList(freelancers);
    }

    public FreelancerResponseDTO createFreelancer(CreateFreelancerDTO dto) {
        validateFreelancer(dto);
        Manager manager = findByManager(dto.getManagerId());
        String generatedPassword = PasswordGenerator.generateDefaultPassword();

        Freelancer freelancer = freelancerMapper.toEntity(dto, manager, generatedPassword);
        freelancer.setActive(true);

        freelancerRepository.save(freelancer);
        createLog(ActionType.CREATE, "Freelancer", freelancer.getId(), manager.getId(), "Freelancer criado com sucesso", LogStatus.SUCCESS);
        return freelancerMapper.toResponse(freelancer);
    }

    private void validateFreelancer(CreateFreelancerDTO freelancer) {
        if (validateCpf(freelancer.getCpf())) {
            createLog(ActionType.CREATE, "Freelancer", null, freelancer.getManagerId(), "Tentativa de cadastro com cpf já existente", LogStatus.ERROR);
            throw new CpfAlreadyRegisteredException("Tentativa de cadastro de Freelancer com CPF existente no DB");
        }
        if (validateEmail(freelancer.getEmail())) {
            createLog(ActionType.CREATE, "Freelancer", null, freelancer.getManagerId(), "Tentativa de cadastro com e-mail já existente", LogStatus.ERROR);
            throw new EmailAlreadyRegisteredException("Tentativa de cadastro de Freelancer com E-mail existente no DB");
        }
        if (validatePhone(freelancer.getPhone())) {
            createLog(ActionType.CREATE, "Freelancer", null, freelancer.getManagerId(), "Tentativa de cadastro com telefone já existente", LogStatus.ERROR);
            throw new PhoneAlreadyRegisteredException("Tentativa de cadastro de Freelancer com Telefone existente no DB");
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

    private Manager findByManager(Long managerId) {
        return managerRepository.findById(managerId)
                .orElseThrow(() -> {
                    createLog(ActionType.CREATE, "Manager", null, null, "Manager não encontrado ao tentar criar freelancer", LogStatus.ERROR);
                    return new ManagerNotFoundException("Manager não encontrado");
                });
    }

    private void createLog(ActionType action, String entityName, Long entityId, Long managerId, String description, LogStatus status) {
        Log log = new Log(action, entityName, entityId, managerId, description, status);
        logService.createLog(log);
    }
}
