package com.ecamp.repository;

import com.ecamp.model.Camp;
import com.ecamp.model.Registration;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RegistrationRepository extends JpaRepository<Registration, Long> {
    // Count how many registrations exist for a given camp
    long countByCampId(Long campId);
    List<Registration> findByCamp(Camp camp);

}
