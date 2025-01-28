package com.sphere.demo.service.user;

import com.sphere.demo.apipayload.status.ErrorStatus;
import com.sphere.demo.exception.ex.UnivCertException;
import com.sphere.demo.repository.UserRepository;
import com.univcert.api.UnivCert;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class UnivCertService {

    @Value("${university-auth.api-key}")
    private String apiKey;

    private static final String SUCCESS = "success";

    private final UserRepository userRepository;

    public void verify(String email, String univName) {
        try {
            validateEmail(email);
            validateUnivName(univName);
            certify(email, univName);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void confirm(String email, String univName, Integer code) {
        try {
            certifyCode(email, univName, code);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void validateEmail(String email) throws IOException {
        boolean exists = userRepository.existsByUnivEmail(email);
        if (exists) {
            throw new UnivCertException(ErrorStatus.EMAIL_EXISTS);
        }

        boolean success = (Boolean) UnivCert.status(apiKey, email).get(SUCCESS);
        if (success) {
            throw new UnivCertException(ErrorStatus.ALREADY_AUTHENTICATED);
        }
    }

    private static void validateUnivName(String univName) throws IOException {
        boolean success = (Boolean) UnivCert.check(univName).get(SUCCESS);
        if (!success) {
            throw new UnivCertException(ErrorStatus.INVALID_UNIV_NAME);
        }
    }

    private void certify(String email, String univName) throws IOException {
        boolean success = (Boolean) UnivCert.certify(apiKey, email, univName, true).get(SUCCESS);
        if (!success) {
            throw new UnivCertException(ErrorStatus.STUDENT_AUTH_FAILED);
        }
    }

    private void certifyCode(String email, String univName, Integer code) throws IOException {
        boolean success = (Boolean) UnivCert.certifyCode(apiKey, email, univName, code).get(SUCCESS);
        if (!success) {
            throw new UnivCertException(ErrorStatus.INVALID_CODE);
        }
    }
}
