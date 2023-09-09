package ru.hogwarts.school.Service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.Repository.StudentRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class StatisticServiceImpl implements StatisticService {

private final StudentRepository studentRepository;

    public StatisticServiceImpl(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Override
    public List<String> getStudentNameStartsA() {
        return studentRepository.findAll()
                .stream()
                .map(student -> student.getName().toUpperCase())
                .filter(s -> s.startsWith("A"))
                .sorted(String::compareTo)
                .collect(Collectors.toList());
    }
}
