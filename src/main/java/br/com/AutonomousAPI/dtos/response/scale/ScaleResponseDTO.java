package br.com.AutonomousAPI.dtos.response.scale;

import br.com.AutonomousAPI.dtos.response.point.PointResponseDTO;
import br.com.AutonomousAPI.enums.ScaleStatus;

import java.time.LocalDateTime;
import java.util.List;

public class ScaleResponseDTO {
    private Long id;
    private ScaleStatus scaleStatus;
    private double scaleValue;
    private LocalDateTime scaleDateTime;
    private String scaleObservation;
    private String freelancerName;
    private String managerName;
    private String storeName;
    private List<PointResponseDTO> points;

    public ScaleResponseDTO() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ScaleStatus getScaleStatus() {
        return scaleStatus;
    }

    public void setScaleStatus(ScaleStatus scaleStatus) {
        this.scaleStatus = scaleStatus;
    }

    public double getScaleValue() {
        return scaleValue;
    }

    public void setScaleValue(double scaleValue) {
        this.scaleValue = scaleValue;
    }

    public LocalDateTime getScaleDateTime() {
        return scaleDateTime;
    }

    public void setScaleDateTime(LocalDateTime scaleDateTime) {
        this.scaleDateTime = scaleDateTime;
    }

    public String getScaleObservation() {
        return scaleObservation;
    }

    public void setScaleObservation(String scaleObservation) {
        this.scaleObservation = scaleObservation;
    }

    public String getFreelancerName() {
        return freelancerName;
    }

    public void setFreelancerName(String freelancerName) {
        this.freelancerName = freelancerName;
    }

    public String getManagerName() {
        return managerName;
    }

    public void setManagerName(String managerName) {
        this.managerName = managerName;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public List<PointResponseDTO> getPoints() {
        return points;
    }

    public void setPoints(List<PointResponseDTO> points) {
        this.points = points;
    }
}
