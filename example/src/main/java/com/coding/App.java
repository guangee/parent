package com.coding;

import com.coding.cors.ProxyProperties;
import com.coding.service.PowerService;
import com.github.lianjiatech.retrofit.spring.boot.annotation.RetrofitScan;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.swagger2.annotations.EnableSwagger2;


@EnableSwagger2
@RequiredArgsConstructor
@RetrofitScan("com.coding")
@RestController
@SpringBootApplication
public class App implements CommandLineRunner {

    private final PowerService powerService;
    private final ProxyProperties proxyProperties;


    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }

    @Override
    public void run(String... args) {
//        System.out.println("start");
//        Object list = powerService.getList();
//        System.out.println(list);
//        ContainerHelper.start(Arrays.asList(new Container[]{new ProxyClientContainer(proxyProperties)}));
//        System.out.println("proxy start");

        System.out.println("start");
        Object list = powerService.getList();
        System.out.println(list);
//        ContainerHelper.start(Arrays.asList(new Container[]{new ProxyClientContainer(proxyProperties)}));
        System.out.println("proxy start");
    }
}
