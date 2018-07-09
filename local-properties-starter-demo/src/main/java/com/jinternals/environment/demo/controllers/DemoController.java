package com.jinternals.environment.demo.controllers;


import com.jinternals.logger.annotation.InjectLogger;
import org.slf4j.Logger;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DemoController {

    @InjectLogger
    private Logger logger;

    @GetMapping("/demo")
    public String getDemo(){
        logger.info("/demo api is called");
        return "hello world";
    }
}
