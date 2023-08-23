package ru.hogwarts.school.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.Exception.AlreadyCreatedException;
import ru.hogwarts.school.Exception.EmptyException;
import ru.hogwarts.school.Model.Faculty;
import ru.hogwarts.school.Model.Student;
import ru.hogwarts.school.Repository.FacultyRepository;

import java.util.Collection;

@Service
public class FacultyServiceImpl implements FacultyService {

    @Autowired
    private final FacultyRepository facultyRepository;

    public FacultyServiceImpl(FacultyRepository facultyRepository) {
        this.facultyRepository = facultyRepository;
    }

    @Override
    public Faculty createFaculty(Faculty faculty) {
        if (facultyRepository.equals(faculty.getId())) {
            throw new AlreadyCreatedException("Already created");
        }
        return facultyRepository.save(faculty);
    }

    @Override
    public Collection<Student> getFacultyStudents(Long facultyId) {
        Faculty faculty = facultyRepository.findFacultyById(facultyId);
        if (faculty == null) {
            throw new EmptyException("Faculty not found");
        }
        return faculty.getStudents();
    }

    @Override
    public Faculty getFaculty(Long id) {
        return facultyRepository.findById(id).orElseThrow(() -> new EmptyException("Faculty not found"));
    }

    public Faculty updateFaculty(Faculty faculty) {
        Faculty existing = getFaculty(faculty.getId());
        existing.setColor(faculty.getColor());
        existing.setName(faculty.getName());
        return facultyRepository.save(faculty);
    }

    @Override
    public void removeFaculty(Long id) {
        if (id != null) {
            facultyRepository.deleteById(id);
        }
        throw new EmptyException("Cell is empty");
    }

    @Override
    public Collection<Faculty> getAll() {
        return facultyRepository.findAll();
    }

    @Override
    public Collection<Faculty> getAllByColor(String color) {
        return facultyRepository.getAllByColor(color);
    }

    @Override
    public Collection<Faculty> getByColorOrName(String color, String name) {
        return facultyRepository.findByNameIgnoreCaseOrColorIgnoreCase(color, name);
    }
}
