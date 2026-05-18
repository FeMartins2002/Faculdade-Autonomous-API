package br.com.AutonomousAPI.configurations;

import br.com.AutonomousAPI.exceptions.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGeneric(Exception ex) {

        ErrorResponse error = new ErrorResponse(
                LocalDateTime.now(),
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "INTERNAL_ERROR",
                "Ocorreu um erro inesperado.",
                ex.getMessage()
        );

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ErrorResponse> handleBusiness(BusinessException ex) {
        HttpStatus status = resolveStatus(ex);

        ErrorResponse error = new ErrorResponse(
                LocalDateTime.now(),
                status.value(),
                "BUSINESS_RULE",
                resolveUserMessage(ex),
                ex.getMessage()
        );

        return ResponseEntity.status(status).body(error);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidation(MethodArgumentNotValidException ex) {

        String errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> error.getDefaultMessage())
                .collect(Collectors.joining(", "));

        ErrorResponse error = new ErrorResponse(
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST.value(),
                "VALIDATION_ERROR",
                errors,
                errors
        );

        return ResponseEntity.badRequest().body(error);
    }

    private HttpStatus resolveStatus(BusinessException ex) {
        if (ex instanceof UnauthorizedException)
            return HttpStatus.UNAUTHORIZED;

        if(ex instanceof AccessDeniedException) {
            return HttpStatus.FORBIDDEN;
        }

        if (ex instanceof FreelancerNotFoundException
                || ex instanceof ManagerNotFoundException
                || ex instanceof StoreNotFoundException)
            return HttpStatus.NOT_FOUND;

        if (ex instanceof CpfAlreadyRegisteredException
                || ex instanceof EmailAlreadyRegisteredException
                || ex instanceof PhoneAlreadyRegisteredException
                || ex instanceof AddressAlreadyRegisteredException)
            return HttpStatus.CONFLICT;

        return HttpStatus.BAD_REQUEST;
    }

    private String resolveUserMessage(BusinessException ex) {
        if (ex instanceof UnauthorizedException) {
            return "Usuário ou senha inválidos.";
        }

        if(ex instanceof AccessDeniedException) {
            return "Você não possui permissão para realizar esta ação.";
        }

        if (ex instanceof ManagerNotFoundException) {
            return "Usuário ou senha inválidos.";
        }

        if (ex instanceof FreelancerNotFoundException) {
            return "Usuário ou senha inválidos.";
        }

        if (ex instanceof StoreNotFoundException) {
            return "Loja não encontrada";
        }

        if(ex instanceof CpfAlreadyRegisteredException) {
            return "Já existe um cadastro com esse CPF.";
        }

        if(ex instanceof EmailAlreadyRegisteredException) {
            return "Já existe um cadastro com esse E-mail.";
        }

        if(ex instanceof PhoneAlreadyRegisteredException) {
            return "Já existe um cadastro com esse Telefone.";
        }

        if(ex instanceof AddressAlreadyRegisteredException) {
            return "Já existe um cadastro com esse Endereço.";
        }

        return "Não foi possível processar a solicitação.";
    }
}
