package com.coding;

import com.coding.service.PowerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.client.OkHttp3ClientHttpRequestFactory;
import org.springframework.web.bind.annotation.RestController;

@RestController
@SpringBootApplication
public class App implements CommandLineRunner {
    @Autowired
    private PowerService powerService;

    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("start");
        Object list = powerService.getList();
        System.out.println(list);
    }
}
