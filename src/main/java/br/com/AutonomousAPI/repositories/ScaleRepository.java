package br.com.AutonomousAPI.repositories;

import br.com.AutonomousAPI.entities.Scale;
import br.com.AutonomousAPI.enums.ScaleStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ScaleRepository extends JpaRepository<Scale, Long> {
    List<Scale> findByScaleStatus(ScaleStatus status);

    Scale findByScaleDateBetweenAndFreelancerEmailAndScaleStatus(
            LocalDate start,
            LocalDate end,
            String email,
            ScaleStatus status
    );
}
