package com.example.new_hogwarts_school_.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.Setter;



@Getter
@Setter

//avatars of students
@Entity
public class Avatar {

    @Id
    @GeneratedValue
    private Long id;
    private String filePath; // the path to the file on the disk is stored
    private long fileSize; // file size in bytes
    private String mediaType; // file type
    private byte[] data; // information about the file is stored

    @OneToOne
    private Student student;
}
