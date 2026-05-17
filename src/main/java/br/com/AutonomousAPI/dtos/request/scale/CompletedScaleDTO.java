package br.com.AutonomousAPI.dtos.request.scale;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class CompletedScaleDTO {

    @NotNull(message = "O gestor responsável deve ser informado.")
    @Positive(message = "O ID do gestor informado é inválido.")
    private Long managerId;

    @NotNull(message = "A escala a ser cancelada deve ser informada.")
    @Positive(message = "O ID da escala informado é inválido.")
    private Long scaleId;

    public CompletedScaleDTO() {

    }

    public CompletedScaleDTO(Long managerId, Long scaleId) {
        this.managerId = managerId;
        this.scaleId = scaleId;
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
}
