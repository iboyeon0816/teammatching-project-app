package com.sphere.demo.exception.ex;

import com.sphere.demo.apipayload.status.ErrorStatus;
import com.sphere.demo.exception.GeneralException;

public class MemberException extends GeneralException {

    public MemberException(ErrorStatus errorStatus) {
        super(errorStatus);
    }
}
