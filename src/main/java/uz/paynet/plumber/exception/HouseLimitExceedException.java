package uz.paynet.plumber.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class HouseLimitExceedException extends RuntimeException {
    public HouseLimitExceedException(String message) {
        super(message);
    }
}
