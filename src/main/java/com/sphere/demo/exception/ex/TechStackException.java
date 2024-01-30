package com.sphere.demo.exception.ex;

import com.sphere.demo.apipayload.status.ErrorStatus;
import com.sphere.demo.exception.GeneralException;

public class TechStackException extends GeneralException {
    public TechStackException(ErrorStatus errorStatus) {
        super(errorStatus);
    }
}
