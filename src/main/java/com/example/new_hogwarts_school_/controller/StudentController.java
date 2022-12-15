package com.example.new_hogwarts_school_.controller;

import com.example.new_hogwarts_school_.model.Faculty;
import com.example.new_hogwarts_school_.model.Student;
import com.example.new_hogwarts_school_.service.StudentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("students")
public class StudentController {
    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    // displays a list of all students
    @GetMapping("/all")
    public List<Student> findAllStudent(){
        return studentService.findAllStudent();
    }

    // adds students to the list
    @PostMapping
    public Student createStudent(@RequestBody Student student) { //+
        return studentService.createStudent(student);
    }

    // outputs students by id
    @GetMapping("{id}")
    public ResponseEntity<Student> readStudent(@PathVariable Long id) { //+
        Student student = studentService.readStudent(id);
        if(student == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(student);
    }

    // adds edits
    @PutMapping
    public ResponseEntity<Student> updateStudent(@RequestBody Student student) {
       Student studentUpdate = studentService.updateStudent(student);
        if(studentUpdate == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(studentUpdate);
    }

    // deletes a student by id
    @DeleteMapping
    public ResponseEntity<Student>  deleteStudent(@PathVariable Long id) {//++
      studentService.deleteStudent(id);
      return ResponseEntity.ok().build();
    }

    // displays students of a certain age
    @GetMapping("/age")
    public ResponseEntity<Collection<Student>> filterStudent(@RequestParam Integer age) { //+
       List<Student> studentsAge = new ArrayList<>(studentService.filterStudent(age));
        return ResponseEntity.ok(studentsAge);
    }

    // get a list of students whose age is between min and max
    @GetMapping(params = {"min","max"})
    public List<Student> findByAgeBetween(@RequestParam Integer min,
                                          @RequestParam Integer max){
        return studentService.findByAgeBetween(min,max);
    }

    // Outputs the student's faculty by student id
    @GetMapping("/faculty")
    public Faculty findFacultyByStudent(@RequestParam Long id){
        return studentService.findFacultyByStudent(id);
    }


    // outputs the average age of all students
    @GetMapping("/studentAgeAvg")
    public double studentAgeAvg(){
        return studentService.studentAgeAvg();
    }
}
