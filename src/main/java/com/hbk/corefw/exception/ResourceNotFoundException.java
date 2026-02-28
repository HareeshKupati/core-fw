package com.hbk.corefw.exception;

import com.hbk.corefw.dto.Error;
import com.hbk.corefw.dto.FieldLocation;

import java.util.List;

import static com.hbk.corefw.util.CoreConstants.RESOURCE_NOT_FOUND;

public class ResourceNotFoundException extends CoreRuntimeException {

    public ResourceNotFoundException(String field, String value, FieldLocation location) {
        super(RESOURCE_NOT_FOUND, List.of(new Error(RESOURCE_NOT_FOUND, field, value, location)));
    }

}
