package com.sphere.demo.exception.ex;

import com.sphere.demo.apipayload.status.ErrorStatus;
import com.sphere.demo.exception.GeneralException;

public class PageException extends GeneralException {
    public PageException(ErrorStatus errorStatus) {
        super(errorStatus);
    }
}
