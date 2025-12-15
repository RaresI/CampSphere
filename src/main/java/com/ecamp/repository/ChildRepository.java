package com.ecamp.repository;

import com.ecamp.model.Child;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ChildRepository extends JpaRepository<Child, Long> {
    Optional<Child> findByEmail(String email);
}
