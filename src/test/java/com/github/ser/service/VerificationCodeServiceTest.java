package com.github.ser.service;

import com.github.ser.SerApplication;
import com.github.ser.model.database.VerificationCode;
import com.github.ser.repository.VerificationCodeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static com.github.ser.testutils.PopulateDatabase.populateVerificationCodeRepository;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = SerApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DisplayName("Verification Codes tests")
class VerificationCodeServiceTest {

    @Autowired
    private VerificationCodeRepository verificationCodeRepository;

    private VerificationCodeService verificationCodeService;

    @BeforeEach
    void setup() {
        populateVerificationCodeRepository(verificationCodeRepository);

        verificationCodeService = new VerificationCodeService(verificationCodeRepository);
    }

    @Test
    @DisplayName("Generate code - add new code to db")
    void generateCode_addNewCodeToDb() {

        // given
        String email = "dominik@ser.pl";

        List<VerificationCode> verificationCodeListBefore = verificationCodeRepository.findVerificationCodesByUserEmail(email);
        assertEquals(0, verificationCodeListBefore.size());

        // when
        String code = verificationCodeService.generateCode("dominik@ser.pl");


        // done
        assertEquals(6, code.length());

        List<VerificationCode> verificationCodeListAfter = verificationCodeRepository.findVerificationCodesByUserEmail(email);
        assertAll(
                () -> assertEquals(1, verificationCodeListAfter.size()),
                () -> assertEquals(email, verificationCodeListAfter.get(0).getUserEmail()),
                () -> assertEquals(code, verificationCodeListAfter.get(0).getCode())
        );
    }

    @Test
    @DisplayName("Verify code - don't match code")
    void verifyCode_dontMatch() {

        // given
        String email = "kwiatek@ser.pl";
        String code = "999999";

        // when
        Boolean isVerified = verificationCodeService.verifyCode(email, code);

        // done
        assertFalse(isVerified);
    }

    @Test
    @DisplayName("Verify code - match code and delete from db")
    void verifyCode_matchAndDelete() {

        // given
        String email = "rekin@ser.pl";
        String code = "112233";

        List<VerificationCode> verificationCodeListBefore = verificationCodeRepository.findVerificationCodesByUserEmail(email);
        assertEquals(1, verificationCodeListBefore.size());

        // when
        Boolean isVerified = verificationCodeService.verifyCode(email, code);

        // done
        assertTrue(isVerified);

        List<VerificationCode> verificationCodeListAfter = verificationCodeRepository.findVerificationCodesByUserEmail(email);
        assertEquals(0, verificationCodeListAfter.size());

    }

}