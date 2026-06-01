package br.com.AutonomousAPI.dtos.request.scale;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.time.LocalDate;
import java.time.LocalTime;

public class CreateScaleDTO {

    @NotNull(message = "O gestor responsável deve ser informado.")
    @Positive(message = "O ID do gestor informado é inválido.")
    private Long managerId;

    @Positive(message = "O valor da escala deve ser maior que zero.")
    private double scaleValue;

    @NotNull(message = "A data da escala deve ser informada.")
    @Future(message = "A data da escala deve ser uma data futura.")
    @JsonFormat(pattern = "yyyy/MM/dd")
    private LocalDate scaleDate;

    @NotNull(message = "O horário de início da escala deve ser informado.")
    private LocalTime startTime;

    @NotNull(message = "O horário de término da escala deve ser informado.")
    private LocalTime endTime;

    @NotNull(message = "O freelancer da escala deve ser informado.")
    @Positive(message = "O ID do freelancer informado é inválido.")
    private Long freelancerId;

    @NotNull(message = "A loja da escala deve ser informada.")
    @Positive(message = "O ID da loja informado é inválido.")
    private Long storeId;

    public CreateScaleDTO() {

    }

    public CreateScaleDTO(Long managerId, double scaleValue, LocalDate scaleDate, LocalTime startTime, LocalTime endTime, Long freelancerId, Long storeId) {
        this.managerId = managerId;
        this.scaleValue = scaleValue;
        this.scaleDate = scaleDate;
        this.startTime = startTime;
        this.endTime = endTime;
        this.freelancerId = freelancerId;
        this.storeId = storeId;
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
