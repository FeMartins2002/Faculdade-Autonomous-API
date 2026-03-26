package br.com.AutonomousAPI.services;

import br.com.AutonomousAPI.entities.Point;
import br.com.AutonomousAPI.repositories.PointRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PointService {
    @Autowired
    private PointRepository pointRepository;

    public void savePoint() {
        Point point = new Point();
        pointRepository.save(point);
    }
}
