package br.com.AutonomousAPI.dtos.request.store;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class DeleteStoreDTO {

    @NotNull(message = "O gestor responsável deve ser informado.")
    @Positive(message = "O ID do gestor informado é inválido.")
    private Long managerId;

    @NotNull(message = "A loja a ser atualizada deve ser informada.")
    @Positive(message = "O ID da loja informado é inválido.")
    private Long storeId;

    public DeleteStoreDTO() {

    }

    public DeleteStoreDTO(Long managerId, Long storeId) {
        this.managerId = managerId;
        this.storeId = storeId;
    }

    public Long getManagerId() {
        return managerId;
    }

    public void setManagerId(Long managerId) {
        this.managerId = managerId;
    }

    public Long getStoreId() {
        return storeId;
    }

    public void setStoreId(Long storeId) {
        this.storeId = storeId;
    }
}
