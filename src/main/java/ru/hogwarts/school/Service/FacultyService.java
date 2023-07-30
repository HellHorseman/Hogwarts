package ru.hogwarts.school.Service;

import ru.hogwarts.school.Model.Faculty;

import java.util.Collection;

public interface FacultyService {

    Faculty createFaculty(Faculty faculty);

    Faculty getFaculty(Long id);

    Faculty updateFaculty(Long id, Faculty faculty);

    void removeFaculty(Long id);

    Collection<Faculty> getAll();

    Collection<Faculty> getAllByColor(String color);
}
