package gr.infoteam.workshop_spring_boot.utils.exceptions.custom.handlers;

import gr.infoteam.workshop_spring_boot.utils.exceptions.ErrorDetails;
import gr.infoteam.workshop_spring_boot.utils.exceptions.ErrorResponse;
import gr.infoteam.workshop_spring_boot.utils.exceptions.custom.implementations.SkillNameAlreadyExistsException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.Arrays;

@RestControllerAdvice
public class SkillNameAlreadyExistsExceptionHandler {

    @ExceptionHandler({SkillNameAlreadyExistsException.class})
    @ResponseStatus(HttpStatus.CONFLICT)
    public ResponseEntity<ErrorResponse> handleSkillNameAlreadyExistsException(
            SkillNameAlreadyExistsException exception) {

        var errorResponse = new ErrorResponse(
                Arrays.asList(
                        new ErrorDetails(
                                LocalDateTime.now(),
                                exception.getMessage(),
                                HttpStatus.CONFLICT
                        )
                )
        );

        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(errorResponse);
    }
}
