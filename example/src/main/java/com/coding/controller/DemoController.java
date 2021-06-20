package com.coding.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class DemoController {

    @GetMapping
    public String demo() {
//        Integer a = null;
//        log.info("demo:{}", a / 10);
        return "hi";
    }
}
