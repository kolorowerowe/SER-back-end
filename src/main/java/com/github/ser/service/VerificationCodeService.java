package com.github.ser.service;

import com.github.ser.model.database.VerificationCode;
import com.github.ser.repository.VerificationCodeRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Service
@Log4j2
public class VerificationCodeService {

    private final VerificationCodeRepository verificationCodeRepository;
    private final Random random;

    private static final int VERIFICATION_CODE_EXPIRATION_MINUTES = 60;

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

        if (verificationCode == null) {
            return false;
        }

        verificationCodeRepository.delete(verificationCode);

        return true;
    }


    private String generateSixDigitCode() {
        int number = random.nextInt(900000) + 100000;
        return String.valueOf(number);
    }

    @Scheduled(cron = "0 */10 * * * *") // every ten minutes (Second, Minutes, Hours, Day Of Month, Month, Day Of Week)
    private void deleteOldVerificationCodes() {
        LocalDateTime now = LocalDateTime.now();
        log.info("Deleting old verification codes at " + now);

        List<VerificationCode> oldVerificationCodes = verificationCodeRepository.findAll()
                .stream()
                .filter(verificationCode -> verificationCode.getGenerationTime().plus(VERIFICATION_CODE_EXPIRATION_MINUTES, ChronoUnit.MINUTES).isBefore(now))
                .collect(Collectors.toList());


        oldVerificationCodes.forEach(verificationCodeRepository::delete);

        log.info("Deleted " + oldVerificationCodes.size() + " old verification codes.");

    }
}
