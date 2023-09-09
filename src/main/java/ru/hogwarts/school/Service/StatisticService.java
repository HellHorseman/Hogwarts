package ru.hogwarts.school.Service;

import java.util.List;
import java.util.OptionalDouble;

public interface StatisticService {

    List<String> getStudentNameStartsA();

    OptionalDouble getAverageAge();
}
