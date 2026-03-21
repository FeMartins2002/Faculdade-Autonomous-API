package br.com.AutonomousAPI.services;

import br.com.AutonomousAPI.dtos.request.manager.LoginManagerDTO;
import br.com.AutonomousAPI.dtos.response.manager.ManagerResponseDTO;
import br.com.AutonomousAPI.entities.Manager;
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

    public ManagerResponseDTO login(LoginManagerDTO dto) {
        Manager manager = findEmail(dto.getEmail());

        if (!manager.getPassword().equals(dto.getPassword())) {
            throw new UnauthorizedException("Senha incorreta");
        }

        return managerMapper.toResponse(manager);
    }

    private Manager findEmail(String email) {
        return managerRepository.findByEmail(email)
                .orElseThrow(() -> new ManagerNotFoundException("Manager não encontrado"));
    }
}
