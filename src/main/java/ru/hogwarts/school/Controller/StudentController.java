package ru.hogwarts.school.Controller;

import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.Model.Student;
import ru.hogwarts.school.Service.StudentService;

import java.util.Collection;

@RestController
@RequestMapping("/student")
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping
    public Collection<Student> getAll() {
        return studentService.getAll();
    }

    @GetMapping(params = {"age"})
    public Collection<Student> getAllFilteredByAge(@RequestParam Integer age) {
        return studentService.getAllByAge(age);
    }

    @GetMapping("/{id}")
    public Student getStudentById(@PathVariable Long id) {
        return studentService.getStudent(id);
    }

    @DeleteMapping("/{id}")
    public void removeStudent(@PathVariable Long id) {
        studentService.removeStudent(id);
    }

    @PostMapping
    public Student addStudent(@RequestBody Student student) {
        return studentService.createStudent(student);
    }

    @PutMapping("/{id}")
    public Student updateStudent(@PathVariable Long id, @RequestBody Student student) {
        return studentService.updateStudent(id, student);
    }
}
