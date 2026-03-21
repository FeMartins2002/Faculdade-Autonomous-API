package br.com.AutonomousAPI.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import br.com.AutonomousAPI.entities.Store;

public interface StoreRepository extends JpaRepository<Store, Long> {

}
