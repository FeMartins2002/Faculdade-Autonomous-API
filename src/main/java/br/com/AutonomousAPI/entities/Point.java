package br.com.AutonomousAPI.entities;

import br.com.AutonomousAPI.enums.PointType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.EnumType;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
public class Point implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "point_type", nullable = false)
    private PointType pointType;

    @Column(name = "point_datetime", nullable = false)
    private LocalDateTime pointDateTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_scale", nullable = false)
    private Scale scale;

    public Point() {

    }

    public Point(PointType pointType, LocalDateTime pointDateTime, Scale scale) {
        this.pointType = pointType;
        this.pointDateTime = pointDateTime;
        this.scale = scale;
    }

    public Long getId() {
        return id;
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

    public Scale getScale() {
        return scale;
    }

    public void setScale(Scale scale) {
        this.scale = scale;
    }
}
