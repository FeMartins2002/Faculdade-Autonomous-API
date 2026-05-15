package br.com.AutonomousAPI.services;

import br.com.AutonomousAPI.dtos.request.point.CreatePointDTO;
import br.com.AutonomousAPI.entities.Freelancer;
import br.com.AutonomousAPI.entities.Log;
import br.com.AutonomousAPI.entities.Point;
import br.com.AutonomousAPI.entities.Scale;
import br.com.AutonomousAPI.enums.ActionType;
import br.com.AutonomousAPI.enums.LogStatus;
import br.com.AutonomousAPI.enums.PointType;
import br.com.AutonomousAPI.enums.ScaleStatus;
import br.com.AutonomousAPI.exceptions.FreelancerNotFoundException;
import br.com.AutonomousAPI.repositories.FreelancerRepository;
import br.com.AutonomousAPI.repositories.PointRepository;
import br.com.AutonomousAPI.repositories.ScaleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
public class PointService {
    @Autowired
    private PointRepository pointRepository;

    @Autowired
    private ScaleRepository scaleRepository;

    @Autowired
    private FreelancerRepository freelancerRepository;

    @Autowired
    private LogService logService;

    public void savePoint(CreatePointDTO dto) {
        Freelancer freelancer = findByFreelancer(dto.getEmail());
        Scale scale = findScaleToday(dto.getEmail());
        validateScale(scale, freelancer.getName());

        PointType pointType = definePointType(scale);
        Point point = new Point(pointType, LocalDateTime.now(), scale);

        pointRepository.save(point);
        createLog(ActionType.CREATE, "Point", null, null, "Ponto marcado: Freelancer: " + freelancer.getName(), LogStatus.SUCCESS);
    }

    private PointType definePointType(Scale scale) {
        int totalPoints = scale.getPoints().size();

        if (totalPoints == 0) {
            return PointType.ENTRY;
        }

        if (totalPoints == 1) {
            return PointType.EXIT;
        }

        throw new IllegalStateException("Scale já possui entrada e saída");
    }

    private void validateScale(Scale scale, String freelancerName) {
        if (scale == null) {
            createLog(ActionType.CREATE, "Scale", null, null,
                    "Escala não encontrada para hoje: freelancer: " + freelancerName, LogStatus.ERROR);
            throw new RuntimeException("Escala não encontrada para hoje");
        }
    }

    private Freelancer findByFreelancer(String email) {
        return freelancerRepository.findByEmail(email)
                .orElseThrow(() -> {
                    createLog(ActionType.CREATE, "Freelancer", null, null, "Freelancer não encontrado ao tentar marcar ponto: " + email, LogStatus.ERROR);
                    return new FreelancerNotFoundException("Freelancer não encontrado");
                });
    }

    private Scale findScaleToday(String email) {
        LocalDate today = LocalDate.now();

        return scaleRepository.findByScaleDateBetweenAndFreelancerEmailAndScaleStatus(
                LocalDate.from(today.atStartOfDay()),
                LocalDate.from(today.atTime(23, 59, 59)),
                email,
                ScaleStatus.CRIADO
        );
    }

    private void createLog(ActionType action, String entityName, Long entityId, Long managerId, String description, LogStatus status) {
        Log log = new Log(action, entityName, entityId, managerId, description, status);
        logService.createLog(log);
    }
}
