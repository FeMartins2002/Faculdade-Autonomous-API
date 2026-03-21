package br.com.AutonomousAPI.repositories;

import br.com.AutonomousAPI.entities.Log;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LogRepository extends JpaRepository<Log, Long> {

}
