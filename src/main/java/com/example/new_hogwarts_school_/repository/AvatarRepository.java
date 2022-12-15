package com.example.new_hogwarts_school_.repository;

import com.example.new_hogwarts_school_.model.Avatar;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AvatarRepository extends JpaRepository<Avatar, Long> {

    // avatar search by student id
    Optional<Avatar> findByStudentId(Long id);

}
