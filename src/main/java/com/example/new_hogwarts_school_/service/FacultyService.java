package com.example.new_hogwarts_school_.service;

import com.example.new_hogwarts_school_.model.Faculty;
import com.example.new_hogwarts_school_.model.Student;
import com.example.new_hogwarts_school_.repository.FacultyRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
public class FacultyService {

    private final Logger logger = LoggerFactory.getLogger(FacultyService.class);

    private final FacultyRepository facultyRepository;

    public FacultyService(FacultyRepository facultyRepository) {
        this.facultyRepository = facultyRepository;
    }

    // adds a new faculty to the database
    public Faculty createFaculty(Faculty faculty) {
        logger.info("Was invoked method for create faculty");
        return facultyRepository.save(faculty);
    }

    public Faculty readFaculty(Long id) { //find
        logger.info("Was invoked method for read faculty");
        return facultyRepository.findById(id).orElse(null);
    }

    public Faculty updateFaculty(Faculty faculty) { //edit
        logger.info("Was invoked method for update faculty");
        return facultyRepository.save(faculty);
    }

    public void deleteFaculty(Long id) { //delete
        logger.info("Was invoked method for delete faculty");
        facultyRepository.deleteById(id);
    }

    public List<Faculty> filterFaculties(String color) {
        logger.info("Was invoked method for filter faculties");
        return facultyRepository.findByColor(color);
    }

    // Поиск факультета по имени или цвету
    public List<Faculty> findByNameOrColor(String nameOrColor) {
        logger.info("Was invoked method for find by name or color");
        return facultyRepository.findByNameOrColorIgnoreCase(nameOrColor, nameOrColor);
    }

    public Faculty findFaculty(Long id) {
        logger.info("Was invoked method for find faculty");
        return facultyRepository.findById(id).orElse(null); // поиск id фака
    }

    public List<Student> findStudentsByFaculty(Long id) {
        logger.info("Was invoked method for find students by faculty");
        Faculty faculty = findFaculty(id); // найденый фак по id
        List<Student> students = new ArrayList<>();
        if (faculty != null) {
            return faculty.getStudents();
        }
        return students;
    }


    public String longName() {
        logger.info("Was invoked method for longName");
        return facultyRepository.findAll().stream()
                .map(Faculty::getName)
                .max(Comparator.comparingInt(String::length))
                .orElse(null);
    }
}
