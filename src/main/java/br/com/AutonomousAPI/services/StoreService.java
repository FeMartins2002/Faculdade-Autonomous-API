package br.com.AutonomousAPI.services;

import br.com.AutonomousAPI.dtos.request.store.*;
import br.com.AutonomousAPI.dtos.response.store.*;
import br.com.AutonomousAPI.entities.Log;
import br.com.AutonomousAPI.entities.Manager;
import br.com.AutonomousAPI.entities.Store;
import br.com.AutonomousAPI.enums.ActionType;
import br.com.AutonomousAPI.enums.LogStatus;
import br.com.AutonomousAPI.exceptions.*;
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

    public StoreResponseDTO createStore(CreateStoreDTO dto) {
        validateStore(dto);

        Manager manager = findByManager(dto.getManagerId());

        Store store = storeMapper.toEntity(dto, manager);
        store.setActive(true);

        storeRepository.save(store);
        createLog(ActionType.CREATE, "Store", store.getId(), manager.getId(), "Loja criada com sucesso", LogStatus.SUCCESS);
        return storeMapper.toResponse(store);
    }

    public StoreResponseDTO updateStore(UpdateStoreDTO dto) {
        validateStore(dto);

        Manager manager = findByManager(dto.getManagerId());
        Store store = findByStore(dto.getStoreId());

        store.setName(dto.getName());
        store.setPhone(dto.getPhone());
        store.setAddress(dto.getAddress());

        storeRepository.save(store);

        createLog(ActionType.UPDATE, "Store", store.getId(), manager.getId(), "Loja atualizada com sucesso", LogStatus.SUCCESS);
        return storeMapper.toResponse(store);
    }

    public StoreResponseDTO deleteStore(DeleteStoreDTO dto) {
        Manager manager = findByManager(dto.getManagerId());
        Store store = findByStore(dto.getStoreId());

        if (!store.isActive()) {
            createLog(ActionType.DELETE, "Store", store.getId(), manager.getId(), "Tentativa de exclusão de loja já inativa", LogStatus.ERROR);
            throw new StoreAlreadyInactiveException("Tentativa de exclusão de loja já inativa");
        }

        store.setActive(false);
        storeRepository.save(store);

        createLog(ActionType.DELETE, "Store", store.getId(), manager.getId(), "Loja desativada com sucesso", LogStatus.SUCCESS);
        return storeMapper.toResponse(store);
    }

    public List<StoreResponseDTO> findAll() {
        List<Store> stores = storeRepository.findByActiveTrueOrderByName();
        return storeMapper.toResponseList(stores);
    }

    public List<StoreOptions> findOptionsActives() {
        List<Store> stores = storeRepository.findByActiveTrueOrderByName();
        return storeMapper.toOptionsList(stores);
    }

    private void validateStore(CreateStoreDTO store) {
        if(validatePhone(store.getPhone())) {
            createLog(ActionType.CREATE, "Store", null, store.getManagerId(), "Tentativa de cadastro com telefone já existente", LogStatus.ERROR);
            throw new PhoneAlreadyRegisteredException("Tentativa de cadastro com telefone já existente");
        }
        if(validateAddress(store.getAddress())) {
            createLog(ActionType.CREATE, "Store", null, store.getManagerId(), "Tentativa de cadastro com endereço já existente", LogStatus.ERROR);
            throw new AddressAlreadyRegisteredException("Tentativa de cadastro com endereço já existente");
        }
    }

    private void validateStore(UpdateStoreDTO dto) {
        if (storeRepository.existsByPhoneAndIdNot(dto.getPhone(), dto.getStoreId())) {
            createLog(ActionType.UPDATE, "Store", dto.getStoreId(), dto.getManagerId(), "Tentativa de atualização com telefone já existente", LogStatus.ERROR);
            throw new PhoneAlreadyRegisteredException("Tentativa de atualização com telefone já existente");
        }

        if (storeRepository.existsByAddressAndIdNot(dto.getAddress(), dto.getStoreId())) {
            createLog(ActionType.UPDATE, "Store", dto.getStoreId(), dto.getManagerId(), "Tentativa de atualização com endereço já existente", LogStatus.ERROR);
            throw new AddressAlreadyRegisteredException("Tentativa de atualização com endereço já existente");
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
                    createLog(ActionType.CREATE, "Manager", null, null,"Gerente não encontrado ao tentar criar loja", LogStatus.ERROR);
                    return new ManagerNotFoundException("Gerente não encontrado ao tentar criar loja");
                });
    }

    private Store findByStore(Long storeId) {
        return storeRepository.findById(storeId)
                .orElseThrow(() -> {
                    createLog(ActionType.UPDATE, "Store", storeId, null, "Loja não encontrada ao tentar atualizar", LogStatus.ERROR);
                    return new StoreNotFoundException("Loja não encontrada ao tentar atualizar");
                });
    }

    private void createLog(ActionType action, String entityName, Long entityId, Long managerId, String description, LogStatus status) {
        Log log = new Log(action, entityName, entityId, managerId, description, status);
        logService.createLog(log);
    }
}
