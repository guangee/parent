package com.coding;

import com.amazonaws.services.s3.AmazonS3;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
@RestController
@SpringBootApplication
public class App {

    @ApiModelProperty
    private String demo;
    @Autowired
    private AmazonS3 s3;

    public static void main(String[] args) {
        Jackson2ObjectMapperBuilder s;
        SpringApplication.run(App.class, args);
    }
}
