package br.com.AutonomousAPI.services;

import br.com.AutonomousAPI.dtos.request.scale.CreateScaleDTO;
import br.com.AutonomousAPI.entities.Freelancer;
import br.com.AutonomousAPI.entities.Manager;
import br.com.AutonomousAPI.entities.Scale;
import br.com.AutonomousAPI.entities.Store;
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

    public void createScale(CreateScaleDTO dto) {
        //validateScale(dto);
        Manager manager = findManager(dto.getManagerId());
        Freelancer freelancer = findFreelancer(dto.getFreelancerId());
        Store store = findStore(dto.getStoreId());

        Scale scale = scaleMapper.toEntity(dto, manager, freelancer, store);
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
}
