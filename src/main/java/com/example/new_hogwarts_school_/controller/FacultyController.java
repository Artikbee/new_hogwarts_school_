package com.example.new_hogwarts_school_.controller;

import com.example.new_hogwarts_school_.model.Faculty;
import com.example.new_hogwarts_school_.model.Student;
import com.example.new_hogwarts_school_.service.FacultyService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("faculty")
public class FacultyController {

    private final FacultyService facultyService;

    public FacultyController(FacultyService facultyService) {
        this.facultyService = facultyService;
    }

    //adds a new faculty to the database
    @PostMapping
    public Faculty createFaculty(@RequestBody Faculty faculty) {
        return facultyService.createFaculty(faculty);
    }


    // output faculty by id
    @GetMapping("{id}")
    public ResponseEntity<Faculty> readFaculty(@PathVariable Long id) {
        Faculty faculty = facultyService.readFaculty(id);
        if(faculty == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(faculty);
    }

    // Search for faculty by name or color
    @GetMapping(params = "nameOrColor")
    public List<Faculty> findByNameOrColor(@RequestParam String nameOrColor){
        return facultyService.findByNameOrColor(nameOrColor);
    }

    // Fix the error
    @PutMapping
    public ResponseEntity<Faculty> updateFaculty(@RequestBody Faculty faculty){
       Faculty faculty1 = facultyService.updateFaculty(faculty);
        if(faculty1 == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(faculty1);
    }

    // delete faculty
    @DeleteMapping("{id}")
    public ResponseEntity<Faculty> deleteFaculty(@PathVariable Long id) {
        facultyService.deleteFaculty(id);
        return ResponseEntity.ok().build();
    }

    // get faculty by color
    @GetMapping("/color")
    public ResponseEntity<Collection<Faculty>> filterFaculty(@RequestParam String color) { //+
        List<Faculty> facultiesColor = new ArrayList<>(facultyService.filterFaculties(color));
        return ResponseEntity.ok(facultiesColor);
    }

    //Get faculty students
    @GetMapping("{id}/students")
    public ResponseEntity<List<Student>> findStudentsByFaculty(@PathVariable Long id) {
        List<Student> students = new ArrayList<>(facultyService.findStudentsByFaculty(id));
        if (students.isEmpty()) {
            ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(students);
    }

    // get the longest faculty name
    @GetMapping("/longNameFaculty")
    public String longName(){
        return facultyService.longName();
    }

}
