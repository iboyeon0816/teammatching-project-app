package com.sphere.demo.exception.ex;

import com.sphere.demo.apipayload.status.ErrorStatus;
import com.sphere.demo.exception.GeneralException;

public class PositionException extends GeneralException {
    public PositionException(ErrorStatus errorStatus) {
        super(errorStatus);
    }
}