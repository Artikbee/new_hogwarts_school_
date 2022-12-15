package com.example.new_hogwarts_school_.service;

import com.example.new_hogwarts_school_.model.Faculty;
import com.example.new_hogwarts_school_.model.Student;
import com.example.new_hogwarts_school_.repository.StudentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentService {

    private final Logger logger = LoggerFactory.getLogger(StudentService.class);

    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public Student createStudent(Student student) { // create
        logger.info("Was invoked method for create student");
        return studentRepository.save(student);
    }

    public Student readStudent(Long id) { //find
        logger.info("Was invoked method for read student");
        return studentRepository.findById(id).orElse(null);
    }

    public Student updateStudent(Student student) { //edit
        logger.info("Was invoked method for update student");
        return studentRepository.save(student);
    }

    public void deleteStudent(Long id) { //delete
        logger.info("Was invoked method for delete student");
        studentRepository.deleteById(id);
    }

    public List<Student> filterStudent(Integer age) { // TODO: надо подумать как его прописать в репозитории
        logger.info("Was invoked method for filter student");
        return studentRepository.findByAge(age);
    }

    // Получаем список студентов, возраст которых находится в промежутке min и max
    public List<Student> findByAgeBetween(Integer min, Integer max) {
        logger.info("Was invoked method for find by age between");
        return studentRepository.findByAgeBetween(min, max);
    }

    //Получить факультет студента
    public Faculty findFacultyByStudent(Long id) {
        logger.info("Was invoked method for find faculty by student");
        Student student = studentRepository.findById(id).orElse(null);
        if (student == null) {
            return null;
        }
        return student.getFaculty();
    }

    //
    public List<Student> findAllStudent() {
        logger.info("Was invoked method for find all student");
        return studentRepository.findAll();
    }


    public double studentAgeAvg() {
        logger.info("Was invoked method for studentAgeAvg");
        return studentRepository.findAll().stream()
                .mapToInt(Student::getAge)
                .average()
                .orElse(0);
    }
}
