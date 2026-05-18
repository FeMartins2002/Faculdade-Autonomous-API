package br.com.AutonomousAPI.exceptions;

public class AccessDeniedException extends BusinessException {
    public AccessDeniedException(String message) {
        super(message);
    }
}
