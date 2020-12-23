package com.coding;

import com.amazonaws.services.s3.AmazonS3;
import com.coding.grpc.message.MessageServiceGrpc;
import com.coding.service.PowerService;
import com.github.lianjiatech.retrofit.spring.boot.annotation.RetrofitScan;
import io.swagger.annotations.ApiModelProperty;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.swagger2.annotations.EnableSwagger2WebMvc;

@RetrofitScan("com.coding")
@EnableSwagger2WebMvc
@RestController
@SpringBootApplication
public class App implements CommandLineRunner {

    @ApiModelProperty
    private String demo;
    @Autowired
    private AmazonS3 s3;
    @Autowired
    private PowerService powerService;

    public static void main(String[] args) {
        Jackson2ObjectMapperBuilder s;
        SpringApplication.run(App.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("start");
        Object list = powerService.getList();
        System.out.println(list);
    }
}
