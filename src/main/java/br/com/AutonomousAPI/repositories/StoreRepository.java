package br.com.AutonomousAPI.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import br.com.AutonomousAPI.entities.Store;

import java.util.Optional;

public interface StoreRepository extends JpaRepository<Store, Long> {
    Optional<Store> findByPhone(String phone);

    Optional<Store> findByAddress(String address);
}
