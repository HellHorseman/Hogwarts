package ru.hogwarts.school.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.hogwarts.school.Service.GetPortService;

@RestController
public class InfoController {

    private final GetPortService getPortService;

    public InfoController(GetPortService getPortService) {
        this.getPortService = getPortService;
    }

    @GetMapping("/getPort")
    public String getPort() {
        return getPortService.getPort();
    }
}
