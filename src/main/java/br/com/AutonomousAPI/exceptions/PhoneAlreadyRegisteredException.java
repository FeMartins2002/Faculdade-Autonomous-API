package br.com.AutonomousAPI.exceptions;

public class PhoneAlreadyRegisteredException extends BusinessException {
    public PhoneAlreadyRegisteredException(String message) {
        super(message);
    }
}
