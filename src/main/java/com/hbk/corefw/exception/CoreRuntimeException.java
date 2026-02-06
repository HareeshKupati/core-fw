package com.hbk.corefw.exception;

import com.hbk.corefw.dto.Error;

import java.util.List;

public class CoreRuntimeException extends RuntimeException {

    private List<Error> errors = null;

    public CoreRuntimeException(String message, List<Error> errors) {
        super(message);
        this.errors = errors;
    }

    public List<Error> getErrors() {
        return errors;
    }
}
