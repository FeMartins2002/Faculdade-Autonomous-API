package br.com.AutonomousAPI.exceptions;

public class CpfAlreadyRegisteredException extends BusinessException {
    public CpfAlreadyRegisteredException(String message) {
        super(message);
    }
}
