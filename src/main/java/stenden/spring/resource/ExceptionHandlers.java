package stenden.spring.resource;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;

/**
 * A class for handling exceptions
 */
@RestControllerAdvice
public class ExceptionHandlers extends ResponseEntityExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(GreetingException.class)
    public ErrorResponse handleGreetingException(
            GreetingException exception,
            HttpServletRequest request
    ) {
        String response = String.format(
                "I have the message '%s' for %s",
                exception.getMessage(),
                request.getRemoteAddr()
        );
        return new ErrorResponse(response);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return ResponseEntity.badRequest().body(new ErrorResponse("Try proper input next time"));

    }
}
