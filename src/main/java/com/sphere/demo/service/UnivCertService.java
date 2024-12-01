package com.sphere.demo.service;

import com.sphere.demo.apipayload.status.ErrorStatus;
import com.sphere.demo.exception.ex.UnivCertException;
import com.sphere.demo.repository.UserRepository;
import com.univcert.api.UnivCert;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class UnivCertService {

    @Value("${university-auth.api-key}")
    private String apiKey;

    private static final String SUCCESS = "success";

    private final UserRepository userRepository;

    public void verify(String email, String univName) {
        try {
            validateEmailNotExists(email);
            validateUnivName(univName);
            certify(email, univName);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void confirm(String email, String univName, Integer code) {
        try {
            validateCode(email, univName, code);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void validateEmailNotExists(String email) {
        boolean emailExists = userRepository.existsByEmail(email);
        if (emailExists) {
            throw new UnivCertException(ErrorStatus.EMAIL_EXISTS);
        }
    }

    private static void validateUnivName(String univName) throws IOException {
        Map<String, Object> response = UnivCert.check(univName);
        boolean success = (Boolean) response.get(SUCCESS);
        if (!success) {
            throw new UnivCertException(ErrorStatus.INVALID_UNIV_NAME);
        }
    }

    private void certify(String email, String univName) throws IOException {
        Map<String, Object> response = UnivCert.certify(apiKey, email, univName, true);
        boolean success = (Boolean) response.get(SUCCESS);
        if (!success) {
            throw new UnivCertException(ErrorStatus.STUDENT_AUTH_FAILED);
        }
    }

    private void validateCode(String email, String univName, Integer code) throws IOException {
        Map<String, Object> response = UnivCert.certifyCode(apiKey, email, univName, code);
        boolean success = (Boolean) response.get(SUCCESS);
        if (!success) {
            throw new UnivCertException(ErrorStatus.INVALID_CODE);
        }
    }
}
