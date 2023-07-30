package ru.hogwarts.school.Service;

import ru.hogwarts.school.Model.Student;

public interface StudentService {

    Student creatStudent(Student student);

    Student getStudent(Long id);

    Student updateStudent(Long id, Student student);

    void removeStudent(Long id);
}
