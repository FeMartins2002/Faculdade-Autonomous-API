package br.com.AutonomousAPI.dtos.request.point;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class CreatePointDTO {

    @NotBlank(message = "O e-mail do freelancer deve ser informado.")
    @Email(message = "O e-mail informado é inválido. Exemplo: usuario@email.com")
    private String email;

    public CreatePointDTO() {

    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
