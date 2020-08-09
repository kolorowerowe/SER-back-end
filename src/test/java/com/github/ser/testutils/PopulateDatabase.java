package com.github.ser.testutils;

import com.github.ser.enums.Role;
import com.github.ser.model.database.Address;
import com.github.ser.model.database.Company;
import com.github.ser.model.database.User;
import com.github.ser.model.database.VerificationCode;
import com.github.ser.repository.CompanyRepository;
import com.github.ser.repository.UserRepository;
import com.github.ser.repository.VerificationCodeRepository;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class PopulateDatabase {

    public static List<User> populateUserDatabase(UserRepository userRepository) {
        userRepository.deleteAll();

        User user1 = User.builder()
                .id(UUID.fromString("00000000-0000-0000-0000-000000000000"))
                .email("dominik@ser.pl")
                .fullName("Dominik K")
                .isActivated(true)
                .role(Role.SYSTEM_ADMIN)
                .build();

        User user2 = User.builder()
                .id(UUID.fromString("00000000-0000-0000-0000-000000000001"))
                .email("rekin@ser.pl")
                .fullName("Rekin rekin")
                .isActivated(false)
                .role(Role.COMPANY_EDITOR)
                .build();

        User user3 = User.builder()
                .id(UUID.fromString("00000000-0000-0000-0000-000000000002"))
                .email("lama@ser.pl")
                .fullName("Lama lama")
                .isActivated(true)
                .role(Role.COMPANY_EDITOR)
                .build();

        User savedUser1 = userRepository.save(user1);
        User savedUser2 = userRepository.save(user2);
        User savedUser3 = userRepository.save(user3);

        return Arrays.asList(savedUser1, savedUser2, savedUser3);

    }

    public static void populateVerificationCodeRepository(VerificationCodeRepository verificationCodeRepository) {
        verificationCodeRepository.deleteAll();

        VerificationCode verificationCode = VerificationCode.builder()
                .userEmail("rekin@ser.pl")
                .code("112233")
                .build();

        verificationCodeRepository.save(verificationCode);

    }

    public static List<UUID> populateCompanyRepository(CompanyRepository companyRepository, User user) {
        companyRepository.deleteAll();

        Company company1 = Company.builder()
                .name("Galileo")
                .contactPhone("+1234")
                .taxId("999")
                .primaryUserId(user.getId())
                .build();

        company1.setAddress(Address.builder()
                .buildingNumber("1")
                .company(company1)
                .build());

        Company company2 = Company.builder()
                .name("Copernicus")
                .primaryUserId(user.getId())
                .build();

        company2.setAddress(Address.builder()
                .buildingNumber("1")
                .company(company2)
                .build());


        Company savedCompany1 = companyRepository.save(company1);
        Company savedCompany2 = companyRepository.save(company2);

        return Arrays.asList(savedCompany1.getId(), savedCompany2.getId());

    }
}
