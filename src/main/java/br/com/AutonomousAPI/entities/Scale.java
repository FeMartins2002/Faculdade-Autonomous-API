package br.com.AutonomousAPI.entities;

import br.com.AutonomousAPI.enums.ScaleStatus;
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
import jakarta.persistence.OneToMany;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Scale implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "scale_status", nullable = false)
    private ScaleStatus scaleStatus;

    @Column(name = "scale_value", nullable = false)
    private double scaleValue;

    @Column(name = "scale_date", nullable = false)
    private LocalDate scaleDate;

    @Column(name = "start_time", nullable = false)
    private LocalTime startTime;

    @Column(name = "end_time", nullable = false)
    private LocalTime endTime;

    @Column(name = "scale_observation")
    private String scaleObservation;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_freelancer", nullable = false)
    private Freelancer freelancer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_manager", nullable = false)
    private Manager manager;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_store", nullable = false)
    private Store store;

    @OneToMany(mappedBy = "scale", fetch = FetchType.LAZY)
    private List<Point> points = new ArrayList<>();

    public Scale() {

    }

    public Scale(double scaleValue, LocalDate scaleDate, LocalTime startTime, LocalTime endTime, Freelancer freelancer, Manager manager, Store store) {
        this.scaleStatus = ScaleStatus.CRIADO;
        this.scaleValue = scaleValue;
        this.scaleDate = scaleDate;
        this.startTime = startTime;
        this.endTime = endTime;
        this.freelancer = freelancer;
        this.manager = manager;
        this.store = store;
    }

    public Long getId() {
        return id;
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

    public LocalDate getScaleDate() {
        return scaleDate;
    }

    public void setScaleDate(LocalDate scaleDate) {
        this.scaleDate = scaleDate;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }

    public String getScaleObservation() {
        return scaleObservation;
    }

    public void setScaleObservation(String scaleObservation) {
        this.scaleObservation = scaleObservation;
    }

    public Freelancer getFreelancer() {
        return freelancer;
    }

    public void setFreelancer(Freelancer freelancer) {
        this.freelancer = freelancer;
    }

    public Manager getManager() {
        return manager;
    }

    public void setManager(Manager manager) {
        this.manager = manager;
    }

    public Store getStore() {
        return store;
    }

    public void setStore(Store store) {
        this.store = store;
    }

    public List<Point> getPoints() {
        return points;
    }

    public void setPoints(List<Point> points) {
        this.points = points;
    }
}
