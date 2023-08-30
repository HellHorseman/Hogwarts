package ru.hogwarts.school.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.hogwarts.school.Model.Student;
import ru.hogwarts.school.Service.StudentService;

import java.util.List;

@RestController
public class StudentRequestController {

    private final StudentService studentService;

    public StudentRequestController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping("/all-count-students")
    public Integer getCountAllStudents() {
        return studentService.getCountAllStudentInSchool();
    }

    @GetMapping("/midl-age-students")
    public Integer getMidlAgeStudents() {
        return studentService.getMidlAgeStudent();
    }

    @GetMapping("/last-five-students")
    public List<Student> getFiveLastStudents() {
        return studentService.getFiveLastStudents();
    }
}
