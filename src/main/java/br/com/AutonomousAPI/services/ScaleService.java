package br.com.AutonomousAPI.services;

import br.com.AutonomousAPI.dtos.request.scale.CreateScaleDTO;
import br.com.AutonomousAPI.dtos.response.scale.ScaleResponseDTO;
import br.com.AutonomousAPI.entities.*;
import br.com.AutonomousAPI.enums.ActionType;
import br.com.AutonomousAPI.enums.LogStatus;
import br.com.AutonomousAPI.enums.ScaleStatus;
import br.com.AutonomousAPI.exceptions.FreelancerNotFoundException;
import br.com.AutonomousAPI.exceptions.ManagerNotFoundException;
import br.com.AutonomousAPI.exceptions.StoreNotFoundException;
import br.com.AutonomousAPI.mappers.ScaleMapper;
import br.com.AutonomousAPI.repositories.FreelancerRepository;
import br.com.AutonomousAPI.repositories.ManagerRepository;
import br.com.AutonomousAPI.repositories.ScaleRepository;
import br.com.AutonomousAPI.repositories.StoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ScaleService {
    @Autowired
    private ScaleRepository scaleRepository;

    @Autowired
    private ScaleMapper scaleMapper;

    @Autowired
    private ManagerRepository managerRepository;

    @Autowired
    private FreelancerRepository freelancerRepository;

    @Autowired
    private StoreRepository storeRepository;

    @Autowired
    private LogService logService;

    public List<ScaleResponseDTO> findAll() {
        List<Scale> scales = scaleRepository.findAll();
        return scaleMapper.toResponseList(scales);
    }

    public List<ScaleResponseDTO> findByStatus(ScaleStatus status) {
        return scaleMapper.toResponseList(scaleRepository.findByScaleStatus(status));
    }

    public ScaleResponseDTO createScale(CreateScaleDTO dto) {
        //validateScale(dto);
        Manager manager = findByManager(dto.getManagerId());
        Freelancer freelancer = findByFreelancer(dto.getFreelancerId());
        Store store = findByStore(dto.getStoreId());

        Scale scale = scaleMapper.toEntity(dto, manager, freelancer, store);
        scale.setScaleStatus(ScaleStatus.CRIADO);

        scaleRepository.save(scale);
        createLog(ActionType.CREATE, "Scale", scale.getId(), manager.getId(), "Scale criada com sucesso", LogStatus.SUCCESS);
        return scaleMapper.toResponse(scale);
    }

    // Pendente implementar validação de Scale
    private void validateScale(CreateScaleDTO dto) {

    }

    private Manager findByManager(Long managerId) {
        return managerRepository.findById(managerId)
                .orElseThrow(() -> {
                    createLog(ActionType.CREATE, "Manager", null, null, "Manager não encontrado ao tentar criar scale", LogStatus.ERROR);
                    return new ManagerNotFoundException("Manager não encontrado");
                });
    }

    private Freelancer findByFreelancer(Long freelancerId) {
        return freelancerRepository.findById(freelancerId)
                .orElseThrow(() -> {
                    createLog(ActionType.CREATE, "Freelancer", null, null, "Freelancer não encontrado ao tentar criar scale", LogStatus.ERROR);
                    return new FreelancerNotFoundException("Freelancer não encontrado");
                });
    }

    private Store findByStore(Long storeId) {
        return storeRepository.findById(storeId)
                .orElseThrow(() -> {
                    createLog(ActionType.CREATE, "Store", null, null, "Store não encontrado ao tentar criar scale", LogStatus.ERROR);
                    return new StoreNotFoundException("Store não encontrado");
                });
    }

    private void createLog(ActionType action, String entityName, Long entityId, Long managerId, String description, LogStatus status) {
        Log log = new Log(action, entityName, entityId, managerId, description, status);
        logService.createLog(log);
    }
}
