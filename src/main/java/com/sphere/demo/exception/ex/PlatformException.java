package com.sphere.demo.exception.ex;

import com.sphere.demo.apipayload.status.ErrorStatus;
import com.sphere.demo.exception.GeneralException;

public class PlatformException extends GeneralException {
    public PlatformException(ErrorStatus errorStatus) {
        super(errorStatus);
    }
}
