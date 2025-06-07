package net.javaguides.sms.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import net.javaguides.sms.entity.Student;

public interface StudentRepository extends JpaRepository<Student, Long>{
	@Query("SELECT s FROM Student s JOIN s.courses c WHERE c.id = :courseId")
    List<Student> findStudentsByCourseId(@Param("courseId") Long courseId);
	Student findByEmail(String email);
	Student findByUsername(String username);
}
