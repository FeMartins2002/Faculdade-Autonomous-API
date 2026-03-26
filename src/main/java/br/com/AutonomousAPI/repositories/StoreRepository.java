package br.com.AutonomousAPI.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import br.com.AutonomousAPI.entities.Store;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StoreRepository extends JpaRepository<Store, Long> {
    Optional<Store> findByPhone(String phone);

    Optional<Store> findByAddress(String address);
}
