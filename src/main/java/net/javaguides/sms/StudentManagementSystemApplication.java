package net.javaguides.sms;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import net.javaguides.sms.entity.Student;
import net.javaguides.sms.repository.StudentRepository;

@SpringBootApplication
public class StudentManagementSystemApplication implements CommandLineRunner{

	public static void main(String[] args) {
		SpringApplication.run(StudentManagementSystemApplication.class, args);
	}
	
	@Autowired
	private StudentRepository studentRepository;

	@Override
	public void run(String... args) throws Exception {
//		Student student1 = new Student("Myat", "Noe 1", "myatnoe1@gmail.com");
//		studentRepository.save(student1);
//		
//		Student student2 = new Student("Myat", "Noe 2", "myatnoe2@gmail.com");
//		studentRepository.save(student2);
//		
//		Student student3 = new Student("Myat", "Noe 3", "myatnoe3@gmail.com");
//		studentRepository.save(student3);
	}

}
