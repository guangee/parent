package com.coding.aliyun.carno;

import com.coding.utils.Result;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

/**
 * @author guanweiming
 */
@Slf4j
public class AliYunCarNoService {

    private final AliYunCarNoProperties aliYunCarNoProperties;

    public AliYunCarNoService(AliYunCarNoProperties aliYunCarNoProperties) {
        this.aliYunCarNoProperties = aliYunCarNoProperties;
    }


    public Result<String> ocr(String base64Str) throws JSONException {
        String url = "https://dm-53.data.aliyun.com/rest/160601/ocr/ocr_vehicle.json";

        String appCode = aliYunCarNoProperties.getAppCode();

        JSONObject configObj = new JSONObject();
        configObj.put("side", "face");

        JSONObject body = new JSONObject();
        body.put("image", base64Str);
        body.put("configure", configObj);


        log.debug(body.toString());
        RequestBody requestBody = FormBody.create(MediaType.parse("application/json; charset=UTF-8"), body.toString());


        Request request = new Request.Builder()
                .url(url)
                .addHeader("Authorization", "APPCODE " + appCode)
                .post(requestBody)
                .build();
        OkHttpClient okHttpClient = new OkHttpClient();

        Call call = okHttpClient.newCall(request);
        String result = null;
        try {
            Response response = call.execute();
            result = response.body().string();
            log.debug(result);
            log.debug("hello");
        } catch (IOException e) {
            e.printStackTrace();
            return Result.createByErrorMessage("解析失败：" + e.getMessage());
        }
        return Result.createBySuccess(result);
    }
}
