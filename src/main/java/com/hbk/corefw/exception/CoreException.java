package com.hbk.corefw.exception;

import com.hbk.corefw.dto.Error;

import java.util.List;

public class CoreException extends Exception {

    private List<Error> errors = null;

    public CoreException(String message, List<Error> errors) {
        super(message);
        this.errors = errors;
    }

    public List<Error> getErrors() {
        return errors;
    }
}
