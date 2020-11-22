package com.github.ser.repository;

import com.github.ser.model.database.EventConfig;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface EventConfigRepository extends JpaRepository<EventConfig, UUID> {


}
