package ru.hogwarts.school.Service;

import ru.hogwarts.school.Model.Faculty;
import ru.hogwarts.school.Model.Student;

import java.util.Collection;

public interface FacultyService {

    Faculty createFaculty(Faculty faculty);

    Collection<Student> getFacultyStudents(Long facultyId);

    Faculty getFaculty(Long id);

    Faculty updateFaculty(Faculty faculty);

    void removeFaculty(Long id);

    Collection<Faculty> getAll();

    Collection<Faculty> getAllByColor(String color);

    Collection<Faculty> getByColorOrName(String color, String name);
}
