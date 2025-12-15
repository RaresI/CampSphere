package com.ecamp.repository;

import com.ecamp.model.Camp;
import com.ecamp.model.Photo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PhotoRepository extends JpaRepository<Photo, Long> {
    int countByCamp(Camp camp);
}
