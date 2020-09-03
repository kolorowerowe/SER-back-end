package com.github.ser.repository;

import com.github.ser.model.database.SponsorshipPackage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface SponsorshipPackageRepository extends JpaRepository<SponsorshipPackage, UUID> {


}
