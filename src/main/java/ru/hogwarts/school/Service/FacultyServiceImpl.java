package ru.hogwarts.school.Service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.Exception.AlreadyCreatedException;
import ru.hogwarts.school.Exception.EmptyException;
import ru.hogwarts.school.Model.Faculty;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class FacultyServiceImpl implements FacultyService {

    private Map<Long, Faculty> facultyMap = new HashMap<>();

    @Override
    public Faculty createFaculty(Faculty faculty) {
        if (facultyMap.containsKey(faculty.getId())) {
            throw new AlreadyCreatedException("Already created");
        }
        facultyMap.put(faculty.getId(), faculty);
        return faculty;
    }

    @Override
    public Faculty getFaculty(Long id) {
        return facultyMap.get(id);
    }

    @Override
    public Faculty updateFaculty(Long id, Faculty faculty) {
        Faculty existing = getFaculty(id);
        existing.setName(faculty.getName());
        existing.setColor(faculty.getColor());
        facultyMap.put(id, existing);
        return existing;
    }

    @Override
    public void removeFaculty(Long id) {
        if (id != null) {
            facultyMap.remove(id);
        }
        throw new EmptyException("Cell is empty");
    }

    @Override
    public Collection<Faculty> getAll() {
        return Collections.unmodifiableCollection(facultyMap.values());
    }

    @Override
    public Collection<Faculty> getAllByColor(String color) {
        return getAll().stream().filter(f -> f.getColor().equals(color)).collect(Collectors.toList());
    }


}
