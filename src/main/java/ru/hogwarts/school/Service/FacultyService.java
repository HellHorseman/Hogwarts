package ru.hogwarts.school.Service;

import ru.hogwarts.school.Model.Faculty;

public interface FacultyService {

    Faculty createFaculty(Faculty faculty);

    Faculty getFaculty(Long id);

    Faculty updateFaculty(Long id, Faculty faculty);

    void removeFaculty(Long id);
}
