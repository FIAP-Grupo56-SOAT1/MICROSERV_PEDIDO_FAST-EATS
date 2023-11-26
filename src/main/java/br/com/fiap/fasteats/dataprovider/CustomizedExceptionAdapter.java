package br.com.fiap.fasteats.dataprovider;

import br.com.fiap.fasteats.core.domain.exception.PedidoNotFound;
import br.com.fiap.fasteats.core.domain.exception.ProdutoNotFound;
import br.com.fiap.fasteats.core.domain.exception.RegraNegocioException;
import br.com.fiap.fasteats.dataprovider.repository.exception.ExceptionResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.List;

@ControllerAdvice
@RestController
public class CustomizedExceptionAdapter extends ResponseEntityExceptionHandler {
    @ExceptionHandler(Exception.class)
    public final ResponseEntity<Object> handleAllExceptions(Exception ex, WebRequest request) {
        ExceptionResponse exceptionResponse = new ExceptionResponse(LocalDateTime.now(), ex.getMessage(), List.of(request.getDescription(false)));

        return new ResponseEntity<>(exceptionResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(PedidoNotFound.class)
    public final ResponseEntity<Object> handleUserNotFoundException(PedidoNotFound ex, WebRequest request) {
        ExceptionResponse exceptionResponse = new ExceptionResponse(LocalDateTime.now(), ex.getMessage(), List.of(request.getDescription(false)));

        return new ResponseEntity<>(exceptionResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ProdutoNotFound.class)
    public final ResponseEntity<Object> handleUserNotFoundException(ProdutoNotFound ex, WebRequest request) {
        ExceptionResponse exceptionResponse = new ExceptionResponse(LocalDateTime.now(), ex.getMessage(), List.of(request.getDescription(false)));

        return new ResponseEntity<>(exceptionResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(RegraNegocioException.class)
    public final ResponseEntity<Object> handleRegraNegocioException(RegraNegocioException ex, WebRequest request) {
        ExceptionResponse exceptionResponse = new ExceptionResponse(LocalDateTime.now(), ex.getMessage(), List.of(request.getDescription(false)));

        return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
    }
}