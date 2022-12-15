package com.example.new_hogwarts_school_.repository;

import com.example.new_hogwarts_school_.model.Faculty;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FacultyRepository extends JpaRepository<Faculty,Long> {

    // faculty search by color
    List<Faculty> findByColor(String color);

    // Search for faculty by name or color
    List<Faculty> findByNameOrColorIgnoreCase(String name, String color);

}
