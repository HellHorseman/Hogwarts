package ru.hogwarts.school.Service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.Exception.AlreadyCreatedException;
import ru.hogwarts.school.Exception.EmptyException;
import ru.hogwarts.school.Model.Faculty;
import ru.hogwarts.school.Model.Student;
import ru.hogwarts.school.Repository.StudentRepository;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class StudentServiceImpl implements StudentService {

    @Autowired
    private final StudentRepository studentRepository;

    private final Object lock = new Object();

    private final Logger logger = LoggerFactory.getLogger(StudentServiceImpl.class);

    public StudentServiceImpl(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Override
    public Student createStudent(Student student) {
        logger.info("Student createStudent is running");
        if (studentRepository.equals(student.getId())) {
            throw new AlreadyCreatedException("Already created");
        }
        return studentRepository.save(student);
    }

    @Override
    public Student getStudent(Long id) {
        logger.info("Student getStudent is running");
        return studentRepository.findById(id).orElseThrow(() -> new EmptyException("Student not found"));
    }

    public Student updateStudent(Student student) {
        logger.info("Student updateStudent is running");
        Student existing = getStudent(student.getId());
        existing.setAge(student.getAge());
        existing.setName(student.getName());
        return studentRepository.save(student);
    }

    @Override
    public void removeStudent(Long id) {
        logger.info("Student removeStudent is running");
        if (id != null) {
            studentRepository.deleteById(id);
        }
        throw new EmptyException("Cell is empty");
    }

    @Override
    public Collection<Student> getAll() {
        logger.info("Student getAll is running");
        return studentRepository.findAll();
    }

    @Override
    public Faculty getStudentFaculty(Long id) {
        logger.info("Student getStudentFaculty is running");
        Student student = studentRepository.findStudentById(id);
        if (student == null) {
            throw new EmptyException("Student not found");
        }
        return getStudent(id).getFaculty();
    }

    @Override
    public Collection<Student> getAllByAge(int age) {
        logger.info("Student getAllByAge is running");
        return studentRepository.getAllByAge(age);
    }

    @Override
    public Collection<Student> findBetweenAge(int min, int max) {
        logger.info("Student findBetweenAge is running");
        return studentRepository.findByAgeBetween(min, max);
    }

    @Override
    public Integer getCountAllStudentInSchool() {
        logger.info("Student getCountAllStudentInSchool is running");
        return studentRepository.getAllStudentInSchool();
    }

    @Override
    public Integer getMidlAgeStudent() {
        logger.info("Student getMidlAgeStudent is running");
        return studentRepository.getMidlAgeStudent();
    }

    @Override
    public List<Student> getFiveLastStudents() {
        logger.info("Student getFiveLastStudents is running");
        List<Student> lastFiveStudent = studentRepository.getFiveLastStudents();
        Collections.reverse(lastFiveStudent);
        return lastFiveStudent;
    }

    @Override
    public void printAllStudents() {
        List<Student> students = studentRepository.findAll();

        List<String> studentName = students.stream()
                .map(Student::getName)
                .collect(Collectors.toList());

        System.out.println("Main thread");
        studentName.stream().limit(2).forEach(System.out::println);

        new Thread(() -> {
            System.out.println("1st parallel thread");
            studentName.stream().skip(2).limit(2).forEach(System.out::println);
        }).start();

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {

        }

        new Thread(() -> {
            System.out.println("2nd parallel thread");
            studentName.stream().skip(4).limit(2).forEach(System.out::println);
        }).start();
    }

    @Override
    public void printNamesSync() {
        List<Student> students = studentRepository.findAll();

        System.out.println("Main thread");
        synchronizePart(students, 0);
        synchronizePart(students, 1);

        new Thread(() -> {
            System.out.println("1st parallel thread");
            synchronizePart(students, 2);
            synchronizePart(students, 3);
        }).start();


        new Thread(() -> {
            System.out.println("2nd parallel thread");
            synchronizePart(students, 4);
            synchronizePart(students, 5);
        }).start();
    }

    private void synchronizePart(List<Student> students, int index) {
        synchronized (lock) {
            if (index >= 0 && index < students.size()) {
                System.out.println(students.get(index).getName());
            }
        }
    }
}



