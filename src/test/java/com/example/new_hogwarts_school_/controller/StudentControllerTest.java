package com.example.new_hogwarts_school_.controller;

import com.example.new_hogwarts_school_.model.Faculty;
import com.example.new_hogwarts_school_.model.Student;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;

import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class StudentControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private StudentController studentController;

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Test
    void contextLoads() throws Exception {
        Assertions
                .assertThat(studentController)
                .isNotNull();
    }

    @Test
    void createAndDeleteStudent() throws Exception {
        Student student = new Student();
        student.setName("Art");
        student.setAge(13);

        Assertions
                .assertThat(this.testRestTemplate.
                        postForObject("http://localhost:" + port + "/student", student, Student.class))
                .isNotNull();

        Student student1 = studentController.findAllStudent().
                stream()
                .filter(s->s.getName().equals("Art") && s.getAge() == 13)
                .findFirst()
                .orElseThrow();
        this.testRestTemplate.delete("http://localhost:" + port + "/student", student1.getId());
        Assertions
                .assertThat(this.testRestTemplate.
                        postForObject("http://localhost:" + port + "/student", student1, Student.class))
                .isNull();


    }

    @Test
    void readStudent() throws Exception {

        Assertions
                .assertThat(this.testRestTemplate.
                        getForObject("http://localhost:" + port + "/student/9", Student.class))
                .isNotNull();
    }

    @Test
    void updateStudent() {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        Student student1 = new Student();
        student1.setName("Artik");
        student1.setAge(22);
        student1.setId(3L);
        Student student2 = new Student();
        student2.setName("Kate");
        student2.setAge(8);
        student2.setId(3L);
        Student student3 = new Student();
        student3.setName("Halal");
        student3.setAge(27);
        student3.setId(100L);

        HttpEntity<Student> httpEntity = new HttpEntity<>(student3, headers);
        this.testRestTemplate.put("http://localhost:" + port + "/student", student1);
        Assertions
                .assertThat(this.testRestTemplate.getForObject("http://localhost:" + port + "/student/3", Student.class)).isEqualTo(student1);
        this.testRestTemplate.put("http://localhost:" + port + "/student", student2);
        ResponseEntity<Student> response = testRestTemplate.exchange("http://localhost:" + port + "/student", HttpMethod.PUT, httpEntity, Student.class);
        Assertions
                .assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);

    }


    @Test
    void filterStudent() {
        Assertions
                .assertThat(this.testRestTemplate.
                        getForObject("http://localhost:" + port + "/student&age=13", List.class))
                .isNotNull();
    }

    @Test
    void findByAgeBetween() {
        Assertions
                .assertThat(this.testRestTemplate.
                        getForObject("http://localhost:" + port + "/student?min=10&max=25", Student.class))
                .isNotNull();
    }

    @Test
    void findFacultyByStudent() {
        Assertions
                .assertThat(this.testRestTemplate.
                        getForObject("http://localhost:" + port + "/student/9/faculty", Faculty.class))
                .isNotNull();
    }
}