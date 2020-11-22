package com.github.ser.testutils;

import com.github.ser.enums.Role;
import com.github.ser.model.database.*;
import com.github.ser.repository.*;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.*;

@Component
public class PopulateDatabase {


    public static List<User> populateUserDatabase(UserRepository userRepository) {
        userRepository.deleteAll();

        User user1 = User.builder()
                .email("dominik@ser.pl")
                .fullName("Dominik K")
                .isActivated(true)
                .role(Role.SYSTEM_ADMIN)
                .companyAccessList(Collections.emptyList())
                .build();

        User user2 = User.builder()
                .email("rekin@ser.pl")
                .fullName("Rekin rekin")
                .isActivated(false)
                .role(Role.COMPANY_EDITOR)
                .companyAccessList(Collections.emptyList())
                .build();


        User user3 = User.builder()
                .email("lama@ser.pl")
                .fullName("Lama lama")
                .isActivated(true)
                .role(Role.COMPANY_EDITOR)
                .companyAccessList(Collections.emptyList())
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

    public static void populateDeadlineRepository(DeadlineRepository deadlineRepository) {
        deadlineRepository.deleteAll();
//
//        deadlineRepository.save(Deadline.builder()
//                .activity(DeadlineActivity.CHOOSE_ADDITIONAL_EQUIPMENT)
//                .build())
    }

    public static SponsorshipPackage populateSponsorshipPackageRepository(SponsorshipPackageRepository sponsorshipPackageRepository){

        SponsorshipPackage sponsorshipPackage1 = SponsorshipPackage.builder()
                .translations(Collections.singleton(Translation.builder()
                        .languageCode("pl")
                        .name("Sponsor główny")
                        .description("Opis głównego")
                        .build()))
                .prices(Collections.singleton(Price.builder()
                        .currency("PLN")
                        .value(new BigDecimal(1000))
                        .build()))
                .standSize(12.0)
                .isAvailable(true)
                .maxCompanies(2)
                .companies(Set.of())
                .build();

        return sponsorshipPackageRepository.save(sponsorshipPackage1);

    }

    @Transactional
    public List<Company> populateCompanyRepository(CompanyRepository companyRepository, SponsorshipPackage sponsorshipPackage, CompanyAccessRepository companyAccessRepository, User user) {

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



        company1.setSponsorshipPackage(sponsorshipPackage);

        Company savedCompany1 = companyRepository.save(company1);
        Company savedCompany2 = companyRepository.save(company2);


        companyAccessRepository.save(CompanyAccess.builder()
                .companyId(company1.getId())
                .companyName("Company1")
                .user(user)
                .build());

        companyAccessRepository.save(CompanyAccess.builder()
                .companyId(company2.getId())
                .companyName("Company2")
                .user(user)
                .build());


        return Arrays.asList(savedCompany1, savedCompany2);
    }


    @Transactional
    public List<Equipment> populateEquipmentRepository(SPEquipmentRepository spEquipmentRepository, EquipmentRepository equipmentRepository, UUID sponsorshipPackageId) {


        Equipment tv = equipmentRepository.save(Equipment.builder()
                .translations(Set.of(Translation.builder()
                        .languageCode("pl")
                        .name("Telewizor")
                        .description("40 cali")
                        .build()))
                .prices(Set.of(Price.builder()
                        .value(BigDecimal.valueOf(100))
                        .currency("PLN")
                        .build()))
                .maxCountPerCompany(2)
                .build());


        Equipment chair = equipmentRepository.save(Equipment.builder()
                .translations(Set.of(Translation.builder()
                        .languageCode("pl")
                        .name("Krzesło")
                        .description("do siedzenia")
                        .build()))
                .prices(Set.of(Price.builder()
                        .value(BigDecimal.valueOf(25))
                        .currency("PLN")
                        .build()))
                .maxCountPerCompany(4)
                .build());

        spEquipmentRepository.save(SPEquipment.builder()
                .equipment(tv)
                .sponsorshipPackageId(sponsorshipPackageId)
                .count(2)
                .build()
        );

        return Arrays.asList(tv, chair);
    }


}
