package com.coding.service;


import com.github.lianjiatech.retrofit.spring.boot.annotation.RetrofitClient;
import retrofit2.http.GET;

/**
 * @author yunji
 */
@RetrofitClient(baseUrl = "https://power.coding-space.cn/")
public interface PowerService {

    @GET("service/list")
    Object getList();
}
