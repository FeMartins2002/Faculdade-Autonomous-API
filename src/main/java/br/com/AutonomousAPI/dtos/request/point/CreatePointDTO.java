package br.com.AutonomousAPI.dtos.request.point;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class CreatePointDTO {
    @NotBlank
    @Email
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
