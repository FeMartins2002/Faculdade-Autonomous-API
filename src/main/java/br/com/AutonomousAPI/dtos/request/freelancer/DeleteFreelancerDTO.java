package br.com.AutonomousAPI.dtos.request.freelancer;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class DeleteFreelancerDTO {

    @NotNull(message = "O gestor responsável deve ser informado.")
    @Positive(message = "O ID do gestor informado é inválido.")
    private Long managerId;

    @NotNull(message = "O freelancer deve ser informado.")
    @Positive(message = "O ID do freelancer informado é inválido.")
    private Long freelancerId;

    public DeleteFreelancerDTO() {

    }

    public DeleteFreelancerDTO(Long managerId, Long freelancerId) {
        this.managerId = managerId;
        this.freelancerId = freelancerId;
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
}
