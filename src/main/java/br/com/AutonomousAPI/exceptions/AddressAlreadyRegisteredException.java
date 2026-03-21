package br.com.AutonomousAPI.exceptions;

public class AddressAlreadyRegisteredException extends BusinessException {
    public AddressAlreadyRegisteredException(String message) {
        super(message);
    }
}
