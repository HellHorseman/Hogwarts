package ru.hogwarts.school.Service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class GetPortServiceImpl implements GetPortService{

    @Value("${server.port}")
    private String serverPort;

    private final Logger logger = LoggerFactory.getLogger(GetPortServiceImpl.class);

    @Override
    public String getPort() {
        logger.info("GetPort is running");
        return "The application is running on port: " + serverPort;
    }
}
