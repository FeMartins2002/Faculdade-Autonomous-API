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
        try {
            Manager manager = findEmail(dto.getEmail());

            if (!manager.getPassword().equals(dto.getPassword())) {
                IncorrectPasswordLog("Senha incorreta para o e-mail: " + dto.getEmail());
                throw new UnauthorizedException("Senha incorreta");
            }

            loginSuccessfulLog(manager);
            return managerMapper.toResponse(manager);
        }
        catch (ManagerNotFoundException ex) {
            managerNotFoundLog("Tentativa de login com e-mail: " + dto.getEmail());
            throw ex;
        }
    }

    private Manager findEmail(String email) {
        return managerRepository.findByEmail(email)
                .orElseThrow(() -> new ManagerNotFoundException("Manager não encontrado"));
    }

    private void loginSuccessfulLog(Manager manager) {
        Log log = new Log(
                ActionType.LOGIN,
                "",
                null,
                manager.getId(),
                "Login realizado com sucesso com o e-mail: " + manager.getEmail(),
                LogStatus.SUCCESS);
        logService.createLog(log);
    }

    private void managerNotFoundLog(String description) {
        Log log = new Log(
                ActionType.LOGIN,
                null,
                null,
                null,
                description,
                LogStatus.ERROR);
        logService.createLog(log);
    }

    private void IncorrectPasswordLog(String description) {
        Log log = new Log(
                ActionType.LOGIN,
                null,
                null,
                null,
                description,
                LogStatus.ERROR);
        logService.createLog(log);
    }
}
