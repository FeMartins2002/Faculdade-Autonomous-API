package br.com.AutonomousAPI.services;

import br.com.AutonomousAPI.dtos.request.scale.*;
import br.com.AutonomousAPI.dtos.response.scale.*;
import br.com.AutonomousAPI.entities.*;
import br.com.AutonomousAPI.enums.ActionType;
import br.com.AutonomousAPI.enums.LogStatus;
import br.com.AutonomousAPI.enums.Role;
import br.com.AutonomousAPI.enums.ScaleStatus;
import br.com.AutonomousAPI.exceptions.AccessDeniedException;
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

    public ScaleResponseDTO createScale(CreateScaleDTO dto) {
        Manager manager = findByManager(dto.getManagerId());
        validateAuthorization(manager, ActionType.CREATE);
        //validateScale(dto);

        Freelancer freelancer = findByFreelancer(dto.getFreelancerId());
        Store store = findByStore(dto.getStoreId());

        Scale scale = scaleMapper.toEntity(dto, manager, freelancer, store);
        scale.setScaleStatus(ScaleStatus.CRIADO);

        scaleRepository.save(scale);
        createLog(ActionType.CREATE, "Scale", scale.getId(), manager.getId(), "Escala criada com sucesso", LogStatus.SUCCESS);
        return scaleMapper.toResponse(scale);
    }

    public ScaleResponseDTO updateScale(UpdateScaleDTO dto) {
        Manager manager = findByManager(dto.getManagerId());
        validateAuthorization(manager, ActionType.UPDATE);
        //validateScale(dto);

        Scale scale = findByScale(dto.getScaleId());
        Store store = findByStore(dto.getStoreId());

        scale.setStartTime(dto.getStartTime());
        scale.setEndTime(dto.getEndTime());
        scale.setStore(store);
        scale.setScaleValue(dto.getScaleValue());

        scaleRepository.save(scale);

        createLog(ActionType.UPDATE, "Scale", scale.getId(), manager.getId(), "Escala atualizada com sucesso", LogStatus.SUCCESS);
        return scaleMapper.toResponse(scale);
    }

    public ScaleResponseDTO completedScale(CompletedScaleDTO dto) {
        Manager manager = findByManager(dto.getManagerId());
        validateAuthorization(manager, ActionType.UPDATE);
        //validateScale(dto);

        Scale scale = findByScale(dto.getScaleId());
        scale.setScaleStatus(ScaleStatus.CONCLUIDO);

        scaleRepository.save(scale);

        createLog(ActionType.UPDATE, "Scale", scale.getId(), manager.getId(), "Escala finalizada com sucesso", LogStatus.SUCCESS);
        return scaleMapper.toResponse(scale);
    }

    public ScaleResponseDTO cancelledScale(CancelledScaleDTO dto) {
        Manager manager = findByManager(dto.getManagerId());
        validateAuthorization(manager, ActionType.UPDATE);
        //validateScale(dto);

        Scale scale = findByScale(dto.getScaleId());

        scale.setScaleStatus(ScaleStatus.CANCELADO);
        scale.setScaleObservation(dto.getObservation());

        scaleRepository.save(scale);

        createLog(ActionType.UPDATE, "Scale", scale.getId(), manager.getId(), "Escala cancelada com sucesso", LogStatus.SUCCESS);
        return scaleMapper.toResponse(scale);
    }

    public List<ScaleResponseDTO> findAll() {
        List<Scale> scales = scaleRepository.findAll();
        return scaleMapper.toResponseList(scales);
    }

    public List<ScaleResponseDTO> findByStatus(ScaleStatus status) {
        List<Scale> scales = scaleRepository.findByScaleStatus(status);
        return scaleMapper.toResponseList(scales);
    }

    public List<ScaleResponseDTO> findByScalesClosed() {
        List<Scale> scales = scaleRepository.findByScaleStatusIn(List.of(ScaleStatus.CONCLUIDO, ScaleStatus.CANCELADO));
        return scaleMapper.toResponseList(scales);
    }

    public ObservationResponse findObservationById(Long id) {
        Scale scale = findByScale(id);

        return switch (scale.getScaleStatus()) {
            case CONCLUIDO -> new ObservationResponse(id, "Escala concluída com sucesso");
            case CANCELADO ->  new ObservationResponse(id, scale.getScaleObservation());
            default -> new ObservationResponse(id, "Escala em andamento");
        };
    }

    // Pendente implementar validação de Scale
    private void validateScale(CreateScaleDTO dto) {

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
                    createLog(ActionType.CREATE, "Manager", null, null, "Gerente não encontrado ao tentar criar escala", LogStatus.ERROR);
                    return new ManagerNotFoundException("Gerente não encontrado ao tentar criar escala");
                });
    }

    private Freelancer findByFreelancer(Long freelancerId) {
        return freelancerRepository.findById(freelancerId)
                .orElseThrow(() -> {
                    createLog(ActionType.CREATE, "Freelancer", null, null, "Freelancer não encontrado ao tentar criar escala", LogStatus.ERROR);
                    return new FreelancerNotFoundException("Freelancer não encontrado ao tentar criar escala");
                });
    }

    private Store findByStore(Long storeId) {
        return storeRepository.findById(storeId)
                .orElseThrow(() -> {
                    createLog(ActionType.CREATE, "Store", null, null, "Loja não encontrado ao tentar criar escala", LogStatus.ERROR);
                    return new StoreNotFoundException("Loja não encontrado ao tentar criar escala");
                });
    }

    private Scale findByScale(Long scaleId) {
        return scaleRepository.findById(scaleId)
                .orElseThrow(() -> {
                    createLog(ActionType.CREATE, "Store", null, null, "Escala não encontrada", LogStatus.ERROR);
                    return new StoreNotFoundException("Escala não encontrada");
                });
    }

    private void createLog(ActionType action, String entityName, Long entityId, Long managerId, String description, LogStatus status) {
        Log log = new Log(action, entityName, entityId, managerId, description, status);
        logService.createLog(log);
    }
}
