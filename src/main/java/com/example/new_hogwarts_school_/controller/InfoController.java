package com.example.new_hogwarts_school_.controller;

import com.example.new_hogwarts_school_.service.InfoService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class InfoController {

    private final InfoService infoService;

    public InfoController(InfoService infoService) {
        this.infoService = infoService;
    }

    // outputs the port number
    @GetMapping("getPort")
    public String getPort() {
        return infoService.getPort();
    }

}
