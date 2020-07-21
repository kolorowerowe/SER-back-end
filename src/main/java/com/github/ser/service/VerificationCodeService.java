package com.github.ser.service;

import com.github.ser.model.database.VerificationCode;
import com.github.ser.repository.VerificationCodeRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

@Service
@Log4j2
public class VerificationCodeService {

    private final VerificationCodeRepository verificationCodeRepository;
    private final Random random;

    public VerificationCodeService(VerificationCodeRepository verificationCodeRepository) {
        this.verificationCodeRepository = verificationCodeRepository;
        random = new Random();
    }

    public String generateCode(String email) {
        String code = generateSixDigitCode();

        VerificationCode newVerificationCode = VerificationCode.builder()
                .code(code)
                .generationTime(LocalDateTime.now())
                .userEmail(email)
                .build();

        verificationCodeRepository.save(newVerificationCode);

        return code;
    }

    /**
     * verifyCode return true if code is correct and matches an email
     * otherwise - return false
     */
    public Boolean verifyCode(String email, String code) {

        List<VerificationCode> verificationCodes = verificationCodeRepository.findVerificationCodesByUserEmail(email);
        VerificationCode verificationCode = verificationCodes.stream().filter(x -> x.getCode().equals(code)).findFirst().orElse(null);

        if (verificationCode == null){
            return false;
        }

        verificationCodeRepository.delete(verificationCode);

        return true;
    }


    private String generateSixDigitCode() {
        int number = random.nextInt(900000) + 100000;
        return String.valueOf(number);
    }
}
