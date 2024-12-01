package com.sphere.demo.exception.ex;

import com.sphere.demo.apipayload.status.ErrorStatus;
import com.sphere.demo.exception.GeneralException;

public class UnivCertException extends GeneralException {
    public UnivCertException(ErrorStatus errorStatus) {
        super(errorStatus);
    }
}
