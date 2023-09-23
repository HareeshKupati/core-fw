package com.hbk.corefw.exception;

import com.hbk.corefw.dto.ErrorDTO;
import com.hbk.corefw.util.CoreConstants;

import java.util.List;

public class ValidationException extends CoreException {

    public ValidationException(List<ErrorDTO> errorDTOS) {
        super(CoreConstants.VALIDATION_ERROR, errorDTOS);
    }

}
