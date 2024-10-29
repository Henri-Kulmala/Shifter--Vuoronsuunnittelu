package app.shifter.exceptionHandling;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import app.shifter.exceptionHandling.Exceptions.NotQualifiedException;
import app.shifter.exceptionHandling.Exceptions.OutOfWorkingHoursException;
import app.shifter.exceptionHandling.Exceptions.ShiftConflictException;
import app.shifter.exceptionHandling.Exceptions.UnauthorizedAccessException;


@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ShiftConflictException.class)
    public ResponseEntity<String> handleShiftConflictException(ShiftConflictException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
    }

    @ExceptionHandler(OutOfWorkingHoursException.class)
    public ResponseEntity<String> handleOutOfWorkingHoursException(OutOfWorkingHoursException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

    @ExceptionHandler(UnauthorizedAccessException.class)
    public ResponseEntity<String> handleUnauthorizedAccessException(UnauthorizedAccessException ex) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(ex.getMessage());
    }

    @ExceptionHandler(NotQualifiedException.class)
    public ResponseEntity<String> handleNotQualifiedException(NotQualifiedException ex) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(ex.getMessage());
    }

    
}
