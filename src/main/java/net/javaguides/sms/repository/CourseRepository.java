package net.javaguides.sms.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import net.javaguides.sms.entity.Course;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {
}

