package com.tooldepot.pos.service;

import lombok.Getter;

@Getter
public class PosServiceException extends Exception {
    private final Error errorCode;

    public PosServiceException(Error errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }

    public PosServiceException(Error errorCode, String message, Throwable cause) {
        super(message, cause);
        this.errorCode = errorCode;
    }

    public enum Error {
        INVALID_DISCOUNT_PERCENTAGE,
        INVALID_RENTAL_DAYS,
        INVALID_TOOL_CODE
    }
}
