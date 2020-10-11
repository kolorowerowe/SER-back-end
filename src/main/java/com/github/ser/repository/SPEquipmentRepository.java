package com.github.ser.repository;

import com.github.ser.model.database.SPEquipment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface SPEquipmentRepository extends JpaRepository<SPEquipment, UUID> {

    List<SPEquipment> findAllBySponsorshipPackageId(UUID sponsorshipPackageId);
}
