package com.hbk.corefw.exception;

import com.hbk.corefw.dto.ErrorDTO;

import java.util.List;

public class CoreException extends Exception {

    private List<ErrorDTO> errorDTOS = null;

    public CoreException(String message, List<ErrorDTO> errorDTOS) {
        super(message);
        this.errorDTOS = errorDTOS;
    }

    public List<ErrorDTO> getErrors() {
        return errorDTOS;
    }
}
