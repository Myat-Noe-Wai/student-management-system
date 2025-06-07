package net.javaguides.sms.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import net.javaguides.sms.entity.Attendance;

import java.time.LocalDate;
import java.util.List;

public interface AttendanceRepository extends JpaRepository<Attendance, Long> {
    List<Attendance> findByCourseIdAndDate(Long courseId, LocalDate date);
    List<Attendance> findByStudentId(Long studentId);
    List<Attendance> findByStudentIdAndDateBetweenAndPresentTrue(Long studentId, LocalDate startDate, LocalDate endDate);
}

