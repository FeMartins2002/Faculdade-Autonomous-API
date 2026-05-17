package br.com.AutonomousAPI.dtos.request.freelancer;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public class UpdateFreelancerDTO {

    @NotNull(message = "O gestor responsável deve ser informado.")
    @Positive(message = "O ID do gestor informado é inválido.")
    private Long managerId;

    @NotNull(message = "O freelancer deve ser informado.")
    @Positive(message = "O ID do freelancer informado é inválido.")
    private Long freelancerId;

    @NotBlank(message = "O nome completo do freelancer deve ser informado.")
    private String name;

    @NotBlank(message = "O telefone do freelancer deve ser informado.")
    @Pattern(regexp = "\\d{11}", message = "O telefone deve conter DDD e número, totalizando 11 dígitos.")
    private String phone;

    @NotBlank(message = "O e-mail do freelancer deve ser informado.")
    @Email(message = "O e-mail informado é inválido. Exemplo: usuario@email.com")
    private String email;

    public UpdateFreelancerDTO() {

    }

    public UpdateFreelancerDTO(Long managerId, Long freelancerId, String name, String phone, String email) {
        this.managerId = managerId;
        this.freelancerId = freelancerId;
        this.name = name;
        this.phone = phone;
        this.email = email;
    }

    public Long getManagerId() {
        return managerId;
    }

    public void setManagerId(Long managerId) {
        this.managerId = managerId;
    }

    public Long getFreelancerId() {
        return freelancerId;
    }

    public void setFreelancerId(Long freelancerId) {
        this.freelancerId = freelancerId;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
