package com.hbk.corefw.exception;

import com.hbk.corefw.dto.Error;

import java.util.Arrays;

import static com.hbk.corefw.util.CoreConstants.BAD_REQUEST;

public class LatestTokenNotFoundException extends CoreRuntimeException {

    private static final String INVALID_TOKEN = "Invalid token!!";

    public LatestTokenNotFoundException() {
        super(INVALID_TOKEN, Arrays.asList(new Error(BAD_REQUEST, INVALID_TOKEN, null, null, null)));
    }

}
