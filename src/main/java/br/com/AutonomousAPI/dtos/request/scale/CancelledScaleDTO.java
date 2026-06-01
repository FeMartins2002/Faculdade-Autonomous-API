package br.com.AutonomousAPI.dtos.request.scale;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class CancelledScaleDTO {

    @NotNull(message = "O gestor responsável deve ser informado.")
    @Positive(message = "O ID do gestor informado é inválido.")
    private Long managerId;

    @NotNull(message = "A escala a ser cancelada deve ser informada.")
    @Positive(message = "O ID da escala informado é inválido.")
    private Long scaleId;

    @NotBlank(message = "É necessário informar o motivo do cancelamento da escala.")
    private String observation;

    public CancelledScaleDTO() {

    }

    public CancelledScaleDTO(Long managerId, Long scaleId, String observation) {
        this.managerId = managerId;
        this.scaleId = scaleId;
        this.observation = observation;
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

    public String getObservation() {
        return observation;
    }

    public void setObservation(String observation) {
        this.observation = observation;
    }
}
