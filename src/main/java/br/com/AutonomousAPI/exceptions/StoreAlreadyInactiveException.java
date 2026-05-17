package br.com.AutonomousAPI.exceptions;

public class StoreAlreadyInactiveException extends BusinessException {
    public StoreAlreadyInactiveException(String message) {
        super(message);
    }
}
