package com.example.new_hogwarts_school_.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

// getting the port

@Service
public class InfoService {

    private final Logger logger  = LoggerFactory.getLogger(InfoService.class);

    @Value("${server.port}")
    private int port;

    public String getPort() {
        logger.info("Was invoked method for get port");
        return "Server port= " + port;
    }

}
