package br.com.AutonomousAPI.dtos.request.scale;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.time.LocalDate;
import java.time.LocalTime;

public class CreateScaleDTO {
    @NotNull
    @Positive
    private Long managerId;

    @Positive(message = "Valor deve ser maior que zero")
    private double scaleValue;

    @NotNull
    @Future(message = "A data da escala deve ser futura")
    @JsonFormat(pattern = "yyyy/MM/dd")
    private LocalDate scaleDate;

    @NotNull
    private LocalTime startTime;

    @NotNull
    private LocalTime endTime;

    @NotNull
    @Positive
    private Long freelancerId;

    @NotNull
    @Positive
    private Long storeId;

    public CreateScaleDTO() {

    }

    public Long getManagerId() {
        return managerId;
    }

    public void setManagerId(Long managerId) {
        this.managerId = managerId;
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

    public Long getFreelancerId() {
        return freelancerId;
    }

    public void setFreelancerId(Long freelancerId) {
        this.freelancerId = freelancerId;
    }

    public Long getStoreId() {
        return storeId;
    }

    public void setStoreId(Long storeId) {
        this.storeId = storeId;
    }
}
