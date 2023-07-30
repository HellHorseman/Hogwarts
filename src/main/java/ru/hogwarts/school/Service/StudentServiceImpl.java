package ru.hogwarts.school.Service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.Exception.AlreadyCreatedException;
import ru.hogwarts.school.Exception.EmptyException;
import ru.hogwarts.school.Model.Student;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class StudentServiceImpl implements StudentService {

    private Map<Long, Student> studentMap = new HashMap<>();

    @Override
    public Student createStudent(Student student) {
        if (studentMap.containsKey(student.getId())) {
            throw new AlreadyCreatedException("Already created");
        }
        studentMap.put(student.getId(), student);
        return student;
    }

    @Override
    public Student getStudent(Long id) {
        return studentMap.get(id);
    }

    @Override
    public Student updateStudent(Long id, Student student) {
        Student existing = getStudent(id);
        existing.setName(student.getName());
        existing.setAge(student.getAge());
        studentMap.put(id, existing);
        return existing;
    }

    @Override
    public void removeStudent(Long id) {
        if (id != null) {
            studentMap.remove(id);
        }
        throw new EmptyException("Cell is empty");
    }

    @Override
    public Collection<Student> getAll() {
        return Collections.unmodifiableCollection(studentMap.values());
    }

    @Override
    public Collection<Student> getAllByAge(int age) {
        return getAll().stream().filter(s -> s.getAge() == age).collect(Collectors.toList());
    }
}
