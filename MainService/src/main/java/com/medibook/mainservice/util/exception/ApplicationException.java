package com.medibook.mainservice.util.exception;

import lombok.Getter;

@Getter
public class ApplicationException extends RuntimeException{
    private final String message;
    private final String code;
    private final int responseCode;

    public ApplicationException(String code, String message, int responseCode) {
        this.message = message;
        this.code = code;
        this.responseCode = responseCode;
    }

    @Override
    public String getMessage() {
        return message;
    }
}



