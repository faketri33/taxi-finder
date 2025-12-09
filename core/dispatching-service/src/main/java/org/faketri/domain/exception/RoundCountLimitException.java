package org.faketri.domain.exception;

public class RoundCountLimitException extends RuntimeException {
    public RoundCountLimitException(String message) {
        super(message);
    }
}
