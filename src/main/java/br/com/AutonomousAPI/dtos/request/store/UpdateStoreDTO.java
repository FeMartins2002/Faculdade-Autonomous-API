package br.com.AutonomousAPI.dtos.request.store;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;

public class UpdateStoreDTO {

    @NotNull(message = "O gestor responsável deve ser informado.")
    @Positive(message = "O ID do gestor informado é inválido.")
    private Long managerId;

    @NotNull(message = "A loja a ser atualizada deve ser informada.")
    @Positive(message = "O ID da loja informado é inválido.")
    private Long storeId;

    @NotBlank(message = "O nome da loja deve ser informado.")
    private String name;

    @NotBlank(message = "O telefone da loja deve ser informado.")
    @Pattern(regexp = "\\d{11}", message = "O telefone deve conter DDD e número, totalizando 11 dígitos.")
    private String phone;

    @NotBlank(message = "O endereço da loja deve ser informado.")
    private String address;

    public UpdateStoreDTO() {

    }

    public UpdateStoreDTO(Long managerId, Long storeId, String name, String phone, String address) {
        this.managerId = managerId;
        this.storeId = storeId;
        this.name = name;
        this.phone = phone;
        this.address = address;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
