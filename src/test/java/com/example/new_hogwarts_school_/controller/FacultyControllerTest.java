package com.example.new_hogwarts_school_.controller;

import com.example.new_hogwarts_school_.model.Faculty;
import com.example.new_hogwarts_school_.model.Student;
import com.example.new_hogwarts_school_.repository.AvatarRepository;
import com.example.new_hogwarts_school_.repository.FacultyRepository;
import com.example.new_hogwarts_school_.repository.StudentRepository;
import com.example.new_hogwarts_school_.service.AvatarService;
import com.example.new_hogwarts_school_.service.FacultyService;
import com.example.new_hogwarts_school_.service.StudentService;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
class FacultyControllerTest {

    private final List<Faculty> faculties = new ArrayList<>();

    @Autowired
    private MockMvc mockMvc;


    @MockBean
    private FacultyRepository facultyRepository;

    @MockBean
    private AvatarRepository avatarRepository;

    @MockBean
    private StudentRepository studentRepository;


    @SpyBean
    private FacultyService facultyService;

    @SpyBean
    private StudentService studentService;

    @SpyBean
    private AvatarService avatarService;


    @InjectMocks
    private FacultyController facultyController;



    @ParameterizedTest
    @MethodSource("provideParamsForTest")
    public void findAndCreateFacultyTest(long id, String name, String color) throws Exception {
        JSONObject facultyObject = new JSONObject();
        facultyObject.put("name", name);
        facultyObject.put("color", color);

        Faculty faculty = new Faculty();
        faculty.setId(id);
        faculty.setName(name);
        faculty.setColor(color);

        when(facultyRepository.save(any())).thenReturn(faculty);
        when(facultyRepository.findById(any())).thenReturn(Optional.of(faculty));

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/faculty")
                        .content(facultyObject.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.name").value(name))
                .andExpect(jsonPath("$.color").value(color));

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/faculty/" + id)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.name").value(name))
                .andExpect(jsonPath("$.color").value(color));
    }

    @Test
    public void findFacultyNegativeTest() throws Exception {
        when(facultyRepository.findById(any())).thenReturn(Optional.empty());

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/faculty/" + 10)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void findByColorTest() throws Exception {
        Faculty faculty = new Faculty();
        faculty.setId(2L);
        faculty.setName("??????????????????");
        faculty.setColor("????????????");
        List<Faculty> actual = new ArrayList<>();
        actual.add(faculty);

        when(facultyRepository.findByColor(any())).thenReturn(actual);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/faculty?color=????????????")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(2))
                .andExpect(jsonPath("$[0].name").value("??????????????????"))
                .andExpect(jsonPath("$[0].color").value("????????????"));
    }

    @Test
    public void findStudentsByFaculty() throws Exception {
        Faculty faculty = new Faculty();
        faculty.setId(6L);
        faculty.setName("??????????????????");
        faculty.setColor("??????????");
        Student student1 = new Student();
        student1.setId(1L);
        student1.setName("????????");
        student1.setAge(22);
        student1.setFaculty(faculty);
        Student student2 = new Student();
        student2.setId(2L);
        student2.setName("??????????");
        student2.setAge(30);
        student2.setFaculty(faculty);
        List<Student> students = new ArrayList<>();
        students.add(student1);
        students.add(student2);
        faculty.setStudents(students);

        when(facultyRepository.findById(any())).thenReturn(Optional.of(faculty));


        mockMvc.perform(MockMvcRequestBuilders
                        .get("/faculty/" + faculty.getId() + "/students")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].id").value(1))
                .andExpect(jsonPath("$.[1].id").value(2))
                .andExpect(jsonPath("$.[0].name").value("????????"))
                .andExpect(jsonPath("$.[1].name").value("??????????"))
                .andExpect(jsonPath("$.[0].age").value(22))
                .andExpect(jsonPath("$.[1].age").value(30));
    }

    @ParameterizedTest
    @MethodSource("provideParamsForTest")
    public void findByNameOrColorTest(long id, String name, String color) throws Exception {
        Faculty faculty = new Faculty();
        faculty.setId(id);
        faculty.setName(name);
        faculty.setColor(color);
        List<Faculty> actual = new ArrayList<>();
        actual.add(faculty);

        when(facultyRepository.findByNameOrColorIgnoreCase(any(), any())).thenReturn(actual);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/faculty?nameOrColor=" + color)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(id))
                .andExpect(jsonPath("$[0].name").value(name))
                .andExpect(jsonPath("$[0].color").value(color));
    }

    @ParameterizedTest
    @MethodSource("provideParamsForTest")
    public void editFacultyTest(long id, String name, String color) throws Exception {
        JSONObject facultyObject = new JSONObject();
        facultyObject.put("id", id);
        facultyObject.put("name", name);
        facultyObject.put("color", color);

        Faculty faculty = new Faculty();
        faculty.setId(id);
        faculty.setName(name);
        faculty.setColor(color);

        when(facultyRepository.findById(any())).thenReturn(Optional.of(faculty));
        when(facultyRepository.save(any())).thenReturn(faculty);

        mockMvc.perform(MockMvcRequestBuilders
                        .put("/faculty")
                        .content(facultyObject.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.name").value(name))
                .andExpect(jsonPath("$.color").value(color));
    }

    @ParameterizedTest
    @MethodSource("provideParamsForTest")
    public void editFacultyNegativeTest(long id, String name, String color) throws Exception {
        JSONObject facultyObject = new JSONObject();
        facultyObject.put("id", id);
        facultyObject.put("name", name);
        facultyObject.put("color", color);

        Faculty faculty = new Faculty();
        faculty.setId(id);
        faculty.setName(name);
        faculty.setColor(color);

        when(facultyRepository.findById(any())).thenReturn(Optional.empty());
        when(facultyRepository.save(any())).thenReturn(faculty);

        mockMvc.perform(MockMvcRequestBuilders
                        .put("/faculty")
                        .content(facultyObject.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void deleteFacultyTest() throws Exception {
        doNothing().when(facultyRepository).deleteById(1L);

        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/faculty/" + 1)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    public static Stream<Arguments> provideParamsForTest() {
        return Stream.of(
                Arguments.of(3, "????????????????????", "??????????????"),
                Arguments.of(4, "????????????", "????????????"),
                Arguments.of(5, "??????????????????????", "??????????????"));
    }
}
