package br.com.AutonomousAPI.dtos.request.scale;

import br.com.AutonomousAPI.enums.ScaleStatus;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.time.LocalDateTime;

public class CreateScaleDTO {
    @NotNull
    @Positive
    private Long managerId;

    @NotNull
    private ScaleStatus scaleStatus;

    @Positive
    private double scaleValue;

    @NotNull
    @Future
    private LocalDateTime scaleDateTime;

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
