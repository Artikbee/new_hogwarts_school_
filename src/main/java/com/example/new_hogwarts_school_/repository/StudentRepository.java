package com.example.new_hogwarts_school_.repository;

import com.example.new_hogwarts_school_.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StudentRepository extends JpaRepository<Student, Long> {

    List<Student> findByAge(int age);

    //  get a list of students whose age is between min and max
    List<Student> findByAgeBetween(Integer min, Integer max);

    Student getById(Long id);

}
