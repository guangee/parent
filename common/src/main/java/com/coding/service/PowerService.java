package com.coding.service;


import com.github.lianjiatech.retrofit.spring.boot.annotation.RetrofitClient;
import org.springframework.stereotype.Component;
import org.springframework.test.annotation.Commit;
import retrofit2.http.GET;

/**
 * @author yunji
 */
@Component
@RetrofitClient(baseUrl = "https://power.coding-space.cn/")
public interface PowerService {

    @GET("service/list")
    Object getList();
}
