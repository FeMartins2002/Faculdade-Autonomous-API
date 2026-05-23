package br.com.AutonomousAPI.repositories;

import br.com.AutonomousAPI.dtos.response.dashboard.FreelancerCostDTO;
import br.com.AutonomousAPI.dtos.response.dashboard.FreelancerRankingDTO;
import br.com.AutonomousAPI.dtos.response.dashboard.ScaleStatusCountDTO;
import br.com.AutonomousAPI.entities.Scale;
import br.com.AutonomousAPI.enums.ScaleStatus;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ScaleRepository extends JpaRepository<Scale, Long> {
    List<Scale> findByScaleStatus(ScaleStatus status);

    List<Scale> findByScaleStatusIn(List<ScaleStatus> statuses);

    Scale findByScaleDateBetweenAndFreelancerEmailAndScaleStatus(
            LocalDate start,
            LocalDate end,
            String email,
            ScaleStatus status
    );

    // CONSULTA TOTAL DE CONSULTAS CONCLUIDAS E CANCELADAS
    @Query("""
        SELECT new br.com.AutonomousAPI.dtos.response.dashboard.ScaleStatusCountDTO(s.scaleStatus,COUNT(s))
        FROM Scale s
        WHERE s.scaleStatus IN (
            br.com.AutonomousAPI.enums.ScaleStatus.CANCELADO,
            br.com.AutonomousAPI.enums.ScaleStatus.CONCLUIDO
        )
        GROUP BY s.scaleStatus""")
    List<ScaleStatusCountDTO> getScaleStatistics();

    // CONSULTA FREELANCES COM MAIS ESCALAS CONCLUIDAS
    @Query("""
    SELECT new br.com.AutonomousAPI.dtos.response.dashboard.FreelancerRankingDTO(s.freelancer.name, COUNT(s))
    FROM Scale s
    WHERE s.scaleStatus = br.com.AutonomousAPI.enums.ScaleStatus.CONCLUIDO
    GROUP BY s.freelancer.id, s.freelancer.name
    ORDER BY COUNT(s) DESC""")
    List<FreelancerRankingDTO> getTopFreelancerScales(Pageable pageable);

    // CONSULTA FREELANCES COM MAIS VALOR RECEBIDO
    @Query("""
    SELECT new br.com.AutonomousAPI.dtos.response.dashboard.FreelancerCostDTO(s.freelancer.name, SUM(s.scaleValue))
    FROM Scale s
    WHERE s.scaleStatus = br.com.AutonomousAPI.enums.ScaleStatus.CONCLUIDO
    GROUP BY s.freelancer.id, s.freelancer.name
    ORDER BY SUM(s.scaleValue) DESC""")
    List<FreelancerCostDTO> getTopFreelancerPayments(Pageable pageable);

    // TOTAL DE ESCALAS POR MÊS
    @Query(value = """
    SELECT 
        MONTH(scale_date) AS monthNumber,
        YEAR(scale_date) AS yearNumber,
        COUNT(*) AS totalScales
    FROM scale
    WHERE scale_status = 'CONCLUIDO' AND scale_date >= DATEADD('MONTH', -12, CURRENT_DATE)
    GROUP BY YEAR(scale_date), MONTH(scale_date)
    ORDER BY YEAR(scale_date), MONTH(scale_date)
    """, nativeQuery = true)
    List<Object[]> getMonthlyScales();
}
