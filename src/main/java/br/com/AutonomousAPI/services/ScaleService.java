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

    public void createScale(CreateScaleDTO dto) {
        //validateScale(dto);
        Manager manager = findManager(dto.getManagerId());
        Freelancer freelancer = findFreelancer(dto.getFreelancerId());
        Store store = findStore(dto.getStoreId());

        Scale scale = scaleMapper.toEntity(dto, manager, freelancer, store);
        createScaleSuccessfulLog(scale.getId(), manager.getId());

        scaleRepository.save(scale);
    }

    // Pendente implementar validação de Scale
    private void validateScale(CreateScaleDTO dto) {

    }

    private Manager findManager(Long managerId) {
        return managerRepository.findById(managerId)
                .orElseThrow(() -> new ManagerNotFoundException("Manager não encontrado"));
    }

    private Freelancer findFreelancer(Long freelancerId) {
        return freelancerRepository.findById(freelancerId)
                .orElseThrow(() -> new FreelancerNotFoundException("Freelancer não encontrado"));
    }

    private Store findStore(Long storeId) {
        return storeRepository.findById(storeId)
                .orElseThrow(() -> new StoreNotFoundException("Loja não encontrado"));
    }

    private void createScaleSuccessfulLog(Long scaleId, Long ManagerId) {
        Log log = new Log(
                ActionType.CREATE,
                "Scale",
                scaleId,
                ManagerId,
                "Escala cria com sucesso",
                LogStatus.SUCCESS
        );
        logService.createLog(log);
    }
}
