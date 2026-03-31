package br.com.AutonomousAPI.dtos.response.point;

import br.com.AutonomousAPI.enums.PointType;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

public class PointResponseDTO {
    private Long id;
    private PointType pointType;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime pointDateTime;

    public PointResponseDTO() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public PointType getPointType() {
        return pointType;
    }

    public void setPointType(PointType pointType) {
        this.pointType = pointType;
    }

    public LocalDateTime getPointDateTime() {
        return pointDateTime;
    }

    public void setPointDateTime(LocalDateTime pointDateTime) {
        this.pointDateTime = pointDateTime;
    }
}
