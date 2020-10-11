package com.github.ser.repository;

import com.github.ser.model.database.CompanyAccess;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.UUID;

@Repository
public interface CompanyAccessRepository extends JpaRepository<CompanyAccess, UUID> {

    @Transactional
    void deleteAllByCompanyId(UUID companyId);

}
