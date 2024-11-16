package com.tooldepot.pos.service;

import lombok.Getter;

@Getter
public class PosServiceException extends Exception {
    private final Error error;

    public PosServiceException(Error error, String message) {
        super(message);
        this.error = error;
    }

    public PosServiceException(Error error, String message, Throwable cause) {
        super(message, cause);
        this.error = error;
    }

    @Getter
    public enum Error {
        INVALID_DISCOUNT_PERCENT(1),
        INVALID_RENTAL_DAYS(2),
        INVALID_TOOL_CODE(3);

        private final int errorCode;

        Error(int errorCode) {
            this.errorCode = errorCode;
        }
    }
}
