package ru.hogwarts.school.Service;

import ru.hogwarts.school.Model.Faculty;
import ru.hogwarts.school.Model.Student;

import java.util.Collection;
import java.util.List;

public interface StudentService {

    Student createStudent(Student student);

    Student getStudent(Long id);

    Student updateStudent(Student student);

    void removeStudent(Long id);

    Collection<Student> getAll();

    Faculty getStudentFaculty(Long studentId);

    Collection<Student> getAllByAge(int age);

    Collection<Student> findBetweenAge(int min, int max);

    Integer getCountAllStudentInSchool();

    Integer getMidlAgeStudent();

    List<Student> getFiveLastStudents();
}
