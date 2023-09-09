package ru.hogwarts.school.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.hogwarts.school.Service.StatisticService;

import java.util.List;

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
}
