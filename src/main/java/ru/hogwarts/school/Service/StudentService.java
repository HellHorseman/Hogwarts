package ru.hogwarts.school.Service;

import ru.hogwarts.school.Model.Faculty;
import ru.hogwarts.school.Model.Student;

import java.util.Map;

public interface StudentService {

    Student createStudent(Student student);

    Student getStudent(Long id);

    Student updateStudent(Long id, Student student);

    void removeStudent(Long id);

    Map<Long, Student> getAll();
}
