package br.com.AutonomousAPI.services;

import br.com.AutonomousAPI.dtos.request.store.CreateStoreDTO;
import br.com.AutonomousAPI.entities.Manager;
import br.com.AutonomousAPI.entities.Store;
import br.com.AutonomousAPI.exceptions.AddressAlreadyRegisteredException;
import br.com.AutonomousAPI.exceptions.ManagerNotFoundException;
import br.com.AutonomousAPI.exceptions.PhoneAlreadyRegisteredException;
import br.com.AutonomousAPI.mappers.StoreMapper;
import br.com.AutonomousAPI.repositories.ManagerRepository;
import br.com.AutonomousAPI.repositories.StoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public void createStore(CreateStoreDTO dto) {
        validateStore(dto);
        Manager manager = findManager(dto.getManagerId());

        Store store = storeMapper.toEntity(dto, manager);
        store.setActive(true);

        storeRepository.save(store);
    }

    private void validateStore(CreateStoreDTO store) {
        if(validatePhone(store.getPhone())) {
            throw new PhoneAlreadyRegisteredException("Telefone já cadastrado");
        }
        if(validateAddress(store.getAddress())) {
            throw new AddressAlreadyRegisteredException("Endereço já cadastrado");
        }
    }

    private boolean validatePhone(String phone) {
        return storeRepository.findByPhone(phone).isPresent();
    }

    private boolean validateAddress(String address) {
        return storeRepository.findByAddress(address).isPresent();
    }

    private Manager findManager(Long managerId) {
        return managerRepository.findById(managerId)
                .orElseThrow(() -> new ManagerNotFoundException("Manager não encontrado"));
    }
}
