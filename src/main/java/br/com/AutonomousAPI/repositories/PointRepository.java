package br.com.AutonomousAPI.repositories;

import br.com.AutonomousAPI.entities.Point;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PointRepository extends JpaRepository<Point, Long> {

}
