package net.javaguides.sms.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import net.javaguides.sms.entity.ClassSchedule;

public interface ClassScheduleRepository extends JpaRepository<ClassSchedule, Long>{
	List<ClassSchedule> findByCourseIdInAndStartTimeAfter(List<Long> courseIds, LocalDateTime now);
}
