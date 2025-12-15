package com.ecamp.repository;

import com.ecamp.model.DailySchedule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface DailyScheduleRepository extends JpaRepository<DailySchedule, Long> {
    List<DailySchedule> findByInstructorIdAndDate(Long instructorId, LocalDate date);
    List<DailySchedule> findByCampId(Long campId);
    List<DailySchedule> findByCampIdOrderByDateAscStartTimeAsc(Long campId);

}
