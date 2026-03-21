package br.com.AutonomousAPI.exceptions;

public class StoreNotFoundException extends BusinessException {
    public StoreNotFoundException(String message) {
        super(message);
    }
}
