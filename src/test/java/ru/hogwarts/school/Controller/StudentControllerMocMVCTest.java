package ru.hogwarts.school.Controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.hogwarts.school.Model.Student;
import ru.hogwarts.school.Repository.StudentRepository;
import ru.hogwarts.school.Service.StudentServiceImpl;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(StudentController.class)
public class StudentControllerMocMVCTest {


    @Autowired
    private MockMvc mockMvc;

    @SpyBean
    private StudentServiceImpl studentService;

    @MockBean
    private StudentRepository studentRepository;
    @InjectMocks
    private StudentController studentController;


    private static Long id = 1L;
    private static String name = "John";
    private static int age = 18;
    private static JSONObject studentObject;
    private static Student student;
    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() throws JSONException {
        studentObject = new JSONObject();
        studentObject.put("name", name);
        studentObject.put("age", age);

        student = new Student();
        student.setName(name);
        student.setAge(age);

    }

    @Test
    public void testAddStudent() throws Exception {
        Student studentToAdd = new Student();
        studentToAdd.setName(name);
        studentToAdd.setAge(age);

        Student returnedStudent = new Student();
        returnedStudent.setName(name);
        returnedStudent.setAge(age);

        when(studentService.createStudent(studentToAdd)).thenReturn(returnedStudent);

        mockMvc.perform(post("/student")
                        .contentType("application/json")
                        .content(studentObject.toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.name").value(name))
                .andExpect(jsonPath("$.age").value(age));
    }

    @Test
    public void testGetStudentById() throws Exception {

        when(studentRepository.findById(any(Long.class))).thenReturn(Optional.of(student));

        mockMvc.perform(get("/student/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.name").value(name))
                .andExpect(jsonPath("$.age").value(age));
    }

    @Test
    public void testGetStudentByIdNotFound() throws Exception {
        Student nullStudent = new Student();

        when(studentRepository.findById(any(Long.class))).thenReturn(Optional.of(nullStudent));

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/student/" + nullStudent.getId())
                        .content(objectMapper.writeValueAsString(nullStudent))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());

    }

    @Test
    public void testUpdateStudent() throws Exception {

        Student updatedStudent = new Student();
        updatedStudent.setName("Ivan");
        updatedStudent.setAge(25);

        when(studentRepository.save(any(Student.class))).thenReturn(updatedStudent);

        mockMvc.perform(put("/student/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(studentObject.toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.name").value("Carl"))
                .andExpect(jsonPath("$.age").value(25));

        verify(studentService).updateStudent(any(Student.class));
    }

    @Test
    public void testRemoveStudent() throws Exception {
        mockMvc.perform(delete("/student/{id}", id))
                .andExpect(status().isOk());

        verify(studentRepository).deleteById(id);
    }

    @Test
    public void testFindByAgeBetween() throws Exception {
        int minAge = 20;
        int maxAge = 30;

        List<Student> studentsInRange = Collections.singletonList(student);
        when(studentService.findBetweenAge(minAge, maxAge)).thenReturn(studentsInRange);

        mockMvc.perform(get("/student")
                        .param("min", String.valueOf(minAge))
                        .param("max", String.valueOf(maxAge)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(id))
                .andExpect(jsonPath("$[0].name").value(name))
                .andExpect(jsonPath("$[0].age").value(age));

        verify(studentService).findBetweenAge(minAge, maxAge);
    }


}
