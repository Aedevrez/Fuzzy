package anku.exceptions;

import lombok.experimental.StandardException;

@StandardException
public class NegativeValueException extends RuntimeException {
    public NegativeValueException(String message) {
        super(message);
    }
}
