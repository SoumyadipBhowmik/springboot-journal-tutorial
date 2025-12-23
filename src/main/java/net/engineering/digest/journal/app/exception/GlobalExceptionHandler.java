package net.engineering.digest.journal.app.exception;

import net.engineering.digest.journal.app.error.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleUserNotFoundException(UserNotFoundException exception) {
        ErrorResponse errorResponse =
                new ErrorResponse(LocalDateTime.now(), exception.getMessage(), "User not found");
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(CreateUserException.class)
    public ResponseEntity<ErrorResponse> handleUserCreationException(CreateUserException exception) {
        ErrorResponse errorResponse =
                new ErrorResponse(LocalDateTime.now(), exception.getMessage(), "Error Creating User");
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
}
