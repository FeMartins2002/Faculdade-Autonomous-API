package br.com.AutonomousAPI.exceptions;

public class EmailAlreadyRegisteredException extends BusinessException {
    public EmailAlreadyRegisteredException(String message) {
        super(message);
    }
}
