package ru.hogwarts.school.Service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private final Logger logger = LoggerFactory.getLogger(FacultyServiceImpl.class);

    public FacultyServiceImpl(FacultyRepository facultyRepository) {
        this.facultyRepository = facultyRepository;
    }

    @Override
    public Faculty createFaculty(Faculty faculty) {
        logger.info("Faculty createFaculty is running");
        if (facultyRepository.equals(faculty.getId())) {
            throw new AlreadyCreatedException("Already created");
        }
        return facultyRepository.save(faculty);
    }

    @Override
    public Collection<Student> getFacultyStudents(Long facultyId) {
        logger.info("Faculty getFacultyStudents is running");
        Faculty faculty = facultyRepository.findFacultyById(facultyId);
        if (faculty == null) {
            throw new EmptyException("Faculty not found");
        }
        return faculty.getStudents();
    }

    @Override
    public Faculty getFaculty(Long id) {
        logger.info("Faculty getFaculty is running");
        return facultyRepository.findById(id).orElseThrow(() -> new EmptyException("Faculty not found"));
    }

    public Faculty updateFaculty(Faculty faculty) {
        logger.info("Faculty updateFaculty is running");
        Faculty existing = getFaculty(faculty.getId());
        existing.setColor(faculty.getColor());
        existing.setName(faculty.getName());
        return facultyRepository.save(faculty);
    }

    @Override
    public void removeFaculty(Long id) {
        logger.info("Faculty removeFaculty is running");
        if (id != null) {
            facultyRepository.deleteById(id);
        }
        throw new EmptyException("Cell is empty");
    }

    @Override
    public Collection<Faculty> getAll() {
        logger.info("Faculty getAll is running");
        return facultyRepository.findAll();
    }

    @Override
    public Collection<Faculty> getAllByColor(String color) {
        logger.info("Faculty getAllByColor is running");
        return facultyRepository.getAllByColor(color);
    }

    @Override
    public Collection<Faculty> getByColorOrName(String color, String name) {
        logger.info("Faculty getByColorOrName is running");
        return facultyRepository.findByNameIgnoreCaseOrColorIgnoreCase(color, name);
    }
}
