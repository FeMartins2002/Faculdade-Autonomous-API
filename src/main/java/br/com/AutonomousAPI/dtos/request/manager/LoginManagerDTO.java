package br.com.AutonomousAPI.dtos.request.manager;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class LoginManagerDTO {
    @NotBlank
    @Email
    private String email;

    @NotBlank
    private String password;

    public LoginManagerDTO() {

    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
