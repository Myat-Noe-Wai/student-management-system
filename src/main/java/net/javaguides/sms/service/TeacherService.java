package net.javaguides.sms.service;

import java.util.List;

import org.springframework.stereotype.Service;

import net.javaguides.sms.entity.Course;
import net.javaguides.sms.entity.Teacher;
import net.javaguides.sms.repository.CourseRepository;
import net.javaguides.sms.repository.TeacherRepository;

@Service
public class TeacherService {
	private TeacherRepository teacherRepository;
	
	public TeacherService(TeacherRepository teacherRepository) {
        this.teacherRepository = teacherRepository;
    }

    public List<Teacher> getAllTeachers() {
        return teacherRepository.findAll();
    }

    public void saveTeacher(Teacher teacher) {
    	teacherRepository.save(teacher);
    }

    public Teacher getTeacherById(Long id) {
        return teacherRepository.findById(id).orElse(null);
    }

    public void deleteTeacher(Long id) {
    	teacherRepository.deleteById(id);
    }
	
}
