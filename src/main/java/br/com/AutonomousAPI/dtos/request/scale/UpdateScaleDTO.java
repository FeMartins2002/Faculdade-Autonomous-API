package br.com.AutonomousAPI.dtos.request.scale;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.time.LocalTime;

public class UpdateScaleDTO {

    @NotNull(message = "O gestor responsável deve ser informado.")
    @Positive(message = "O ID do gestor informado é inválido.")
    private Long managerId;

    @NotNull(message = "A escala a ser atualizada deve ser informada.")
    @Positive(message = "O ID da escala informado é inválido.")
    private Long scaleId;

    @NotNull(message = "O horário de início da escala deve ser informado.")
    private LocalTime startTime;

    @NotNull(message = "O horário de término da escala deve ser informado.")
    private LocalTime endTime;

    @NotNull(message = "A loja da escala deve ser informada.")
    @Positive(message = "O ID da loja informado é inválido.")
    private Long storeId;

    @Positive(message = "O valor da escala deve ser maior que zero.")
    private double scaleValue;

    public UpdateScaleDTO() {

    }

    public UpdateScaleDTO(Long managerId, Long scaleId, LocalTime startTime, LocalTime endTime, Long storeId, double scaleValue) {
        this.managerId = managerId;
        this.scaleId = scaleId;
        this.startTime = startTime;
        this.endTime = endTime;
        this.storeId = storeId;
        this.scaleValue = scaleValue;
    }

    public Long getManagerId() {
        return managerId;
    }

    public void setManagerId(Long managerId) {
        this.managerId = managerId;
    }

    public Long getScaleId() {
        return scaleId;
    }

    public void setScaleId(Long scaleId) {
        this.scaleId = scaleId;
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

    public Long getStoreId() {
        return storeId;
    }

    public void setStoreId(Long storeId) {
        this.storeId = storeId;
    }

    public double getScaleValue() {
        return scaleValue;
    }

    public void setScaleValue(double scaleValue) {
        this.scaleValue = scaleValue;
    }
}