package gym.utils;

import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception reserved for application domain errors
 */
@Log4j2
@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
public class ApplicationException extends RuntimeException {

    public ApplicationException(String message, Throwable e) {
        super(message, e);
        log.error(e);
    }

    public ApplicationException(String message) {
        super(message);
        log.error(message);
    }
}
