package br.com.AutonomousAPI.exceptions;

public class ManagerNotFoundException extends BusinessException {
    public ManagerNotFoundException(String message) {
        super(message);
    }
}
