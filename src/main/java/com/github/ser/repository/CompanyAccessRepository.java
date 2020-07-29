package com.github.ser.repository;

import com.github.ser.model.database.CompanyAccess;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CompanyAccessRepository extends JpaRepository<CompanyAccess, UUID> {

    void deleteAllByCompanyUuid(UUID companyUuid);

}
