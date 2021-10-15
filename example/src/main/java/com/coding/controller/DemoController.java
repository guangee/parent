package com.coding.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class DemoController {


    @GetMapping("hello")
    public Mono<String> demo() {
        return Mono.justOrEmpty("hello");
    }
}
