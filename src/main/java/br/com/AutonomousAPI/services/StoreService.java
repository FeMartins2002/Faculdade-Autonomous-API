package br.com.AutonomousAPI.services;

import br.com.AutonomousAPI.dtos.request.store.CreateStoreDTO;
import br.com.AutonomousAPI.dtos.response.store.StoreResponseDTO;
import br.com.AutonomousAPI.entities.Log;
import br.com.AutonomousAPI.entities.Manager;
import br.com.AutonomousAPI.entities.Store;
import br.com.AutonomousAPI.enums.ActionType;
import br.com.AutonomousAPI.enums.LogStatus;
import br.com.AutonomousAPI.exceptions.AddressAlreadyRegisteredException;
import br.com.AutonomousAPI.exceptions.ManagerNotFoundException;
import br.com.AutonomousAPI.exceptions.PhoneAlreadyRegisteredException;
import br.com.AutonomousAPI.mappers.StoreMapper;
import br.com.AutonomousAPI.repositories.ManagerRepository;
import br.com.AutonomousAPI.repositories.StoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StoreService {
    @Autowired
    private StoreRepository storeRepository;

    @Autowired
    private StoreMapper storeMapper;

    @Autowired
    private ManagerRepository managerRepository;

    @Autowired
    private LogService logService;

    public List<StoreResponseDTO> findAll() {
        return storeMapper.toResponseList(storeRepository.findAll());
    }

    public StoreResponseDTO createStore(CreateStoreDTO dto) {
        validateStore(dto);
        Manager manager = findByManager(dto.getManagerId());

        Store store = storeMapper.toEntity(dto, manager);
        store.setActive(true);

        storeRepository.save(store);
        createLog(ActionType.CREATE, "Store", store.getId(), manager.getId(), "Store criada com sucesso", LogStatus.SUCCESS);
        return storeMapper.toResponse(store);
    }

    private void validateStore(CreateStoreDTO store) {
        if(validatePhone(store.getPhone())) {
            createLog(ActionType.CREATE, "Store", null, store.getManagerId(), "Tentativa de cadastro com telefone já existente", LogStatus.ERROR);
            throw new PhoneAlreadyRegisteredException("Telefone já cadastrado");
        }
        if(validateAddress(store.getAddress())) {
            createLog(ActionType.CREATE, "Store", null, store.getManagerId(), "Tentativa de cadastro com endereço já existente", LogStatus.ERROR);
            throw new AddressAlreadyRegisteredException("Endereço já cadastrado");
        }
    }

    private boolean validatePhone(String phone) {
        return storeRepository.findByPhone(phone).isPresent();
    }

    private boolean validateAddress(String address) {
        return storeRepository.findByAddress(address).isPresent();
    }

    private Manager findByManager(Long managerId) {
        return managerRepository.findById(managerId)
                .orElseThrow(() -> {
                    createLog(ActionType.CREATE, "Manager", null, null,"Manager não encontrado ao tentar criar store", LogStatus.ERROR);
                    return new ManagerNotFoundException("Manager não encontrado");
                });
    }

    private void createLog(ActionType action, String entityName, Long entityId, Long managerId, String description, LogStatus status) {
        Log log = new Log(action, entityName, entityId, managerId, description, status);
        logService.createLog(log);
    }
}
