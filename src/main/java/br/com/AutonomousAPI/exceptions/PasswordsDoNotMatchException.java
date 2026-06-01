package br.com.AutonomousAPI.exceptions;

public class PasswordsDoNotMatchException extends BusinessException {
    public PasswordsDoNotMatchException(String message) {
        super(message);
    }
}
