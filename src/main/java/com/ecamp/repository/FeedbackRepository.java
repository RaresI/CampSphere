package com.ecamp.repository;

import com.ecamp.model.Camp;
import com.ecamp.model.Feedback;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FeedbackRepository extends JpaRepository<Feedback, Long> {
    int countByCamp(Camp camp);
}
