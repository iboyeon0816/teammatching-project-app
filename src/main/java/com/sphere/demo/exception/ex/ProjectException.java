package com.sphere.demo.exception.ex;

import com.sphere.demo.apipayload.status.ErrorStatus;
import com.sphere.demo.exception.GeneralException;

public class ProjectException extends GeneralException {
    public ProjectException(ErrorStatus errorStatus) {
        super(errorStatus);
    }
}
