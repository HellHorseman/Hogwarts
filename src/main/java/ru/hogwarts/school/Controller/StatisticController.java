package ru.hogwarts.school.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.hogwarts.school.Service.StatisticService;

import java.util.List;
import java.util.OptionalDouble;

@RestController
@RequestMapping("/statistic")
public class StatisticController {

    private final StatisticService statisticService;

    public StatisticController(StatisticService statisticService) {
        this.statisticService = statisticService;
    }

    @GetMapping("/start-a")
    public List<String> getStudentNameStartsA() {
        return statisticService.getStudentNameStartsA();
    }

    @GetMapping("/ave-age")
    public OptionalDouble getAverageAge() {
        return statisticService.getAverageAge();
    }

    @GetMapping("/longest-name")
    public String getLongestFacultyName() {
        return statisticService.getLongestFacultyName();
    }
}
