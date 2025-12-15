package com.ecamp.repository;

import com.ecamp.model.Camp;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CampRepository extends JpaRepository<Camp, Long> {}
