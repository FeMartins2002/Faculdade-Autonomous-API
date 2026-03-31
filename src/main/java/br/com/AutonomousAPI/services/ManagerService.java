package br.com.AutonomousAPI.services;

import br.com.AutonomousAPI.dtos.request.manager.LoginManagerDTO;
import br.com.AutonomousAPI.dtos.response.manager.ManagerResponseDTO;
import br.com.AutonomousAPI.entities.Log;
import br.com.AutonomousAPI.entities.Manager;
import br.com.AutonomousAPI.enums.ActionType;
import br.com.AutonomousAPI.enums.LogStatus;
import br.com.AutonomousAPI.exceptions.ManagerNotFoundException;
import br.com.AutonomousAPI.exceptions.UnauthorizedException;
import br.com.AutonomousAPI.mappers.ManagerMapper;
import br.com.AutonomousAPI.repositories.ManagerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ManagerService {
    @Autowired
    private ManagerRepository managerRepository;

    @Autowired
    private ManagerMapper managerMapper;

    @Autowired
    private LogService logService;

    public ManagerResponseDTO login(LoginManagerDTO dto) {
            Manager manager = findByEmail(dto.getEmail());

            if (!manager.getPassword().equals(dto.getPassword())) {
                createLog(ActionType.LOGIN, manager.getId(), "Credenciais inválidas", LogStatus.ERROR);
                throw new UnauthorizedException("Senha incorreta");
            }

            createLog(ActionType.LOGIN, manager.getId(), "Login com sucesso", LogStatus.SUCCESS);
            return managerMapper.toResponse(manager);
    }

    private Manager findByEmail(String email) {
        return managerRepository.findByEmail(email)
                .orElseThrow(() -> {
                    createLog(ActionType.LOGIN, null, "Credenciais inválidas", LogStatus.ERROR);
                    return new ManagerNotFoundException("Manager não encontrado");
                });
    }

    private void createLog(ActionType action, Long managerId, String description, LogStatus status) {
        Log log = new Log(action, "Manager", null, managerId, description, status);
        logService.createLog(log);
    }
}
