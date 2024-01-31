package com.sphere.demo.exception.ex;

import com.sphere.demo.apipayload.status.ErrorStatus;
import com.sphere.demo.exception.GeneralException;

public class UserException extends GeneralException {

    public UserException(ErrorStatus errorStatus) {
        super(errorStatus);
    }
}
