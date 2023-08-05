package ru.hogwarts.school.Service;

import ru.hogwarts.school.Model.Student;

import java.util.Collection;

public interface StudentService {

    Student createStudent(Student student);

    Student getStudent(Long id);

    Student updateStudent(Long id, Student student);

    void removeStudent(Long id);

    Collection<Student> getAll();

    Collection<Student> getAllByAge(int age);

    Collection<Student> findBetweenAge(int min, int max);
}
