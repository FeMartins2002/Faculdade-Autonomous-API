package br.com.AutonomousAPI.dtos.request.manager;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class LoginManagerDTO {

    @NotBlank(message = "O e-mail de acesso deve ser informado.")
    @Email(message = "O e-mail informado é inválido. Exemplo: usuario@email.com")
    private String email;

    @NotBlank(message = "A senha de acesso deve ser informada.")
    private String password;

    public LoginManagerDTO() {

    }

    public LoginManagerDTO(String email, String password) {
        this.email = email;
        this.password = password;
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