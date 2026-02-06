package com.hbk.corefw.exception;

import com.hbk.corefw.dto.Error;
import com.hbk.corefw.dto.FieldLocation;

import java.util.Arrays;

import static com.hbk.corefw.util.CoreConstants.NOT_FOUND;
import static com.hbk.corefw.util.CoreConstants.RESOURCE_NOT_FOUND;

public class ResourceNotFoundException extends CoreException {

    public ResourceNotFoundException(String field, String value, FieldLocation location) {
        super(RESOURCE_NOT_FOUND, Arrays.asList(new Error(NOT_FOUND, RESOURCE_NOT_FOUND, field, value, location)));
    }

}
