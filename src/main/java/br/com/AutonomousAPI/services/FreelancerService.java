package br.com.AutonomousAPI.services;

import br.com.AutonomousAPI.dtos.request.freelancer.*;
import br.com.AutonomousAPI.dtos.request.manager.ChangePasswordDTO;
import br.com.AutonomousAPI.dtos.response.freelancer.*;
import br.com.AutonomousAPI.entities.Freelancer;
import br.com.AutonomousAPI.entities.Log;
import br.com.AutonomousAPI.entities.Manager;
import br.com.AutonomousAPI.enums.ActionType;
import br.com.AutonomousAPI.enums.LogStatus;
import br.com.AutonomousAPI.enums.Role;
import br.com.AutonomousAPI.exceptions.*;
import br.com.AutonomousAPI.mappers.FreelancerMapper;
import br.com.AutonomousAPI.repositories.FreelancerRepository;
import br.com.AutonomousAPI.repositories.ManagerRepository;
import br.com.AutonomousAPI.services.utils.Generator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static br.com.AutonomousAPI.services.utils.Hash.generateHash;

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

    public FreelancerResponseDTO login(LoginFreelancerDTO loginDTO) {
        Freelancer freelancer = findByFreelancer(loginDTO.getEmail());

        if (!freelancer.getPassword().equals(generateHash(loginDTO.getPassword()))) {
            createLog(ActionType.LOGIN, "Freelancer", null, freelancer.getId(), "Credenciais inválidas", LogStatus.ERROR);
            throw new UnauthorizedException("Senha incorreta");
        }

        createLog(ActionType.LOGIN, "Freelancer", null, freelancer.getId(), "Login realizado com sucesso", LogStatus.SUCCESS);

        return freelancerMapper.toResponse(freelancer);
    }

    public FreelancerResponseDTO changePassword(ChangePasswordDTO dto) {
        validateFreelancer(dto);
        Freelancer freelancer = findByFreelancer(dto.getEmail());
        freelancer.setPassword(generateHash(dto.getPassword()));

        freelancerRepository.save(freelancer);
        return freelancerMapper.toResponse(freelancer);
    }

    public FreelancerResponseDTO createFreelancer(CreateFreelancerDTO dto) {
        Manager manager = findByManager(dto.getManagerId());
        validateAuthorization(manager, ActionType.CREATE);
        validateFreelancer(dto);

        String generatedPassword = generateHash(Generator.generateDefaultPassword());
        Freelancer freelancer = freelancerMapper.toEntity(dto, manager, generatedPassword);
        freelancer.setActive(true);

        freelancerRepository.save(freelancer);
        createLog(ActionType.CREATE, "Freelancer", freelancer.getId(), manager.getId(), "Freelancer criado com sucesso.", LogStatus.SUCCESS);
        return freelancerMapper.toResponse(freelancer);
    }

    public FreelancerResponseDTO updateFreelancer(UpdateFreelancerDTO dto) {
        Manager manager = findByManager(dto.getManagerId());
        validateAuthorization(manager, ActionType.UPDATE);
        validateFreelancer(dto);

        Freelancer freelancer = findByFreelancer(dto.getFreelancerId());
        freelancer.setName(dto.getName());
        freelancer.setPhone(dto.getPhone());
        freelancer.setEmail(dto.getEmail());

        freelancerRepository.save(freelancer);
        createLog(ActionType.UPDATE ,"Freelancer", freelancer.getId(), manager.getId(), "Freelancer atualizado com sucesso.", LogStatus.SUCCESS);
        return freelancerMapper.toResponse(freelancer);
    }

    public FreelancerResponseDTO deleteFreelancer(DeleteFreelancerDTO dto) {
        Manager manager = findByManager(dto.getManagerId());
        validateAuthorization(manager, ActionType.DELETE);

        Freelancer freelancer = findByFreelancer(dto.getFreelancerId());

        if (!freelancer.isActive()) {
            createLog(ActionType.DELETE, "Freelancer", freelancer.getId(), manager.getId(), "Tentativa de exclusão de freelancer já inativo", LogStatus.ERROR);
            throw new FreelancerAlreadyInactiveException("Tentativa de exclusão de freelancer já inativo");
        }

        freelancer.setActive(false);
        freelancerRepository.save(freelancer);

        createLog(ActionType.DELETE, "Freelancer", freelancer.getId(), manager.getId(), "Freelancer desativado com sucesso", LogStatus.SUCCESS);
        return freelancerMapper.toResponse(freelancer);
    }

    public List<FreelancerResponseDTO> freelancersActives() {
        List<Freelancer> freelancers = freelancerRepository.findByActiveTrueOrderByName();
        return freelancerMapper.toResponseList(freelancers);
    }

    public List<FreelancerResponseDTO> freelancersInactives() {
        List<Freelancer> freelancers = freelancerRepository.findByActiveFalseOrderByName();
        return freelancerMapper.toResponseList(freelancers);
    }

    public List<FreelancerOptionDTO> freelancerOptions() {
        List<Freelancer> freelancers = freelancerRepository.findByActiveTrueOrderByName();
        return freelancerMapper.toOptionsList(freelancers);
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

    private void validateFreelancer(UpdateFreelancerDTO freelancer) {
        if (freelancerRepository.existsByEmailAndIdNot(freelancer.getEmail(), freelancer.getFreelancerId())) {
            createLog(ActionType.UPDATE, "Freelancer", freelancer.getFreelancerId(), freelancer.getManagerId(), "Tentativa de atualização com e-mail já existente", LogStatus.ERROR);
            throw new EmailAlreadyRegisteredException("Tentativa de atualização com e-mail já existente");
        }

        if (freelancerRepository.existsByPhoneAndIdNot(freelancer.getPhone(), freelancer.getFreelancerId())) {
            createLog(ActionType.UPDATE, "Freelancer", freelancer.getFreelancerId(), freelancer.getManagerId(), "Tentativa de atualização com telefone já existente", LogStatus.ERROR);
            throw new PhoneAlreadyRegisteredException("Tentativa de atualização com telefone já existente");
        }
    }

    private void validateFreelancer(ChangePasswordDTO dto) {
        if(dto.getEmail() == null || dto.getPassword() == null || dto.getRepeatPassword() == null) {
            throw new IllegalArgumentException("Alteração de senha com dados inválidos");
        }

        if(!dto.getPassword().equals(dto.getRepeatPassword())) {
            throw new PasswordsDoNotMatchException("As senhas não coincidem");
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

    private void validateAuthorization(Manager manager, ActionType actionType) {
        if (manager.getRole() != Role.ROLE_MANAGER) {
            createLog(actionType, "Freelancer", null, manager.getId(), "Usuário sem permissão para realizar esta ação", LogStatus.ERROR);
            throw new AccessDeniedException("Usuário sem permissão para realizar esta ação");
        }
    }

    private Manager findByManager(Long managerId) {
        return managerRepository.findById(managerId)
                .orElseThrow(() -> {
                    createLog(ActionType.CREATE, "Manager", null, null, "Manager não encontrado ao tentar cadastrar freelancer", LogStatus.ERROR);
                    return new ManagerNotFoundException("Manager não encontrado ao tentar cadastrar freelancer");
                });
    }

    private Freelancer findByFreelancer(Long freelancerId) {
        return freelancerRepository.findById(freelancerId)
                .orElseThrow(() -> {
                    createLog(ActionType.UPDATE, "Freelancer", freelancerId, null, "Freelancer não encontrado ao tentar atualizar", LogStatus.ERROR);
                    return new FreelancerNotFoundException("Freelancer não encontrado ao tentar atualizar");
                });
    }

    private Freelancer findByFreelancer(String freelancerEmail) {
        return freelancerRepository.findByEmail(freelancerEmail)
                .orElseThrow(() -> {
                    createLog(ActionType.LOGIN, "Freelancer", null, null, "Freelancer não encontrado ao tentar fazer login", LogStatus.ERROR);
                    return new FreelancerNotFoundException("Freelancer não encontrado ao tentar atualizar");
                });
    }

    private void createLog(ActionType action, String entityName, Long entityId, Long managerId, String description, LogStatus status) {
        Log log = new Log(action, entityName, entityId, managerId, description, status);
        logService.createLog(log);
    }
}
