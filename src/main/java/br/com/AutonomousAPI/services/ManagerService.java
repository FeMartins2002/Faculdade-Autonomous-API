package br.com.AutonomousAPI.services;

import br.com.AutonomousAPI.dtos.request.manager.*;
import br.com.AutonomousAPI.dtos.response.manager.*;
import br.com.AutonomousAPI.entities.Log;
import br.com.AutonomousAPI.entities.Manager;
import br.com.AutonomousAPI.enums.ActionType;
import br.com.AutonomousAPI.enums.LogStatus;
import br.com.AutonomousAPI.exceptions.ManagerNotFoundException;
import br.com.AutonomousAPI.exceptions.PasswordsDoNotMatchException;
import br.com.AutonomousAPI.exceptions.UnauthorizedException;
import br.com.AutonomousAPI.mappers.ManagerMapper;
import br.com.AutonomousAPI.repositories.ManagerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static br.com.AutonomousAPI.services.utils.Hash.generateHash;
import static br.com.AutonomousAPI.services.utils.Generator.generateToken;

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

            if (!manager.getPassword().equals(generateHash(dto.getPassword()))) {
                createLog(ActionType.LOGIN, manager.getId(), "Credenciais inválidas", LogStatus.ERROR);
                throw new UnauthorizedException("Senha incorreta");
            }

            createLog(ActionType.LOGIN, manager.getId(), "Login com sucesso", LogStatus.SUCCESS);
            return managerMapper.toResponse(manager);
    }

    public ManagerResponseDTO changePassword(ChangePasswordDTO dto) {
        validateManager(dto);
        Manager manager = findByEmail(dto.getEmail());
        manager.setPassword(generateHash(dto.getPassword()));

        managerRepository.save(manager);
        return managerMapper.toResponse(manager);
    }

    public TokenResponse validateToken(String email) {
        Manager manager = findByEmail(email);
        String token = generateToken();

        //envia no email
        //System.out.print("TESTE TOKEN =============================== " + token);

        return new TokenResponse(manager.getEmail(), token);
    }

    private void validateManager(ChangePasswordDTO dto) {
        if(dto.getEmail() == null || dto.getPassword() == null || dto.getRepeatPassword() == null) {
            throw new IllegalArgumentException("Alteração de senha com dados inválidos");
        }

        if(!dto.getPassword().equals(dto.getRepeatPassword())) {
            throw new PasswordsDoNotMatchException("As senhas não coincidem");
        }
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
