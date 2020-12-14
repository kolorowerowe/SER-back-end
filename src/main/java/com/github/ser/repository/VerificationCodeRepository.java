package com.github.ser.repository;

import com.github.ser.model.database.VerificationCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface VerificationCodeRepository extends JpaRepository<VerificationCode, UUID> {

    List<VerificationCode> findVerificationCodesByUserEmail(String email);
}
