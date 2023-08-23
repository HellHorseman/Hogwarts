package ru.hogwarts.school.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.Exception.AlreadyCreatedException;
import ru.hogwarts.school.Exception.EmptyException;
import ru.hogwarts.school.Model.Faculty;
import ru.hogwarts.school.Model.Student;
import ru.hogwarts.school.Repository.StudentRepository;

import java.util.Collection;

@Service
public class StudentServiceImpl implements StudentService {

    @Autowired
    private final StudentRepository studentRepository;

    public StudentServiceImpl(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Override
    public Student createStudent(Student student) {
        if (studentRepository.equals(student.getId())) {
            throw new AlreadyCreatedException("Already created");
        }
        return studentRepository.save(student);
    }

    @Override
    public Student getStudent(Long id) {
        return studentRepository.findById(id).orElseThrow(() -> new EmptyException("Student not found"));
    }

    public Student updateStudent(Student student) {
        Student existing = getStudent(student.getId());
        existing.setAge(student.getAge());
        existing.setName(student.getName());
        return studentRepository.save(student);
    }

    @Override
    public void removeStudent(Long id) {
        if (id != null) {
            studentRepository.deleteById(id);
        }
        throw new EmptyException("Cell is empty");
    }

    @Override
    public Collection<Student> getAll() {
        return studentRepository.findAll();
    }

    @Override
    public Faculty getStudentFaculty(Long studentId) {
        Student student = studentRepository.findStudentById(studentId);
        if (student == null) {
            throw new EmptyException("Student not found");
        }
        return student.getFaculty();
    }

    @Override
    public Collection<Student> getAllByAge(int age) {
        return studentRepository.getAllByAge(age);
    }

    @Override
    public Collection<Student> findBetweenAge(int min, int max) {
        return studentRepository.findByAgeBetween(min, max);
    }
}
