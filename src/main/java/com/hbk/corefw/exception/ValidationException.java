package com.hbk.corefw.exception;

import com.hbk.corefw.dto.Error;
import com.hbk.corefw.util.CoreConstants;

import java.util.List;

public class ValidationException extends CoreException {

    public ValidationException(List<Error> errors) {
        super(CoreConstants.VALIDATION_ERROR, errors);
    }

}
