package com.sc.backend.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.*;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
public class HttpRequestUtil {
    private OkHttpClient client = new OkHttpClient();

    public String GetToken(String password) {
        String url = "https://ywtb.cuit.edu.cn/third_api/cxek/PhoneApi/api/Account/Login?code=123&state=GZState";
        MediaType mediaType = MediaType.get("application/x-www-form-urlencoded");
        String Parmas = "grant_type=password&username=&loginInfo=&password=" + password;
        RequestBody body = RequestBody.create(Parmas, mediaType);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        try {
            Map<String, String> map = new HashMap<String, String>();
            ObjectMapper objectMapper = new ObjectMapper();
            System.out.println(Parmas);
            Response response = client.newCall(request).execute();

            if (response.body() != null) {
                if (response.code() == 200) {
                    String res = response.body().string();
                    System.out.println(res);
                    return res;
                } else {
                    return null;
                }
            } else {
                return null;
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String GetActivityList (String access_token, String pageId) {
        String url = "https://ywtb.cuit.edu.cn/third_api/cxek/PhoneApi/api/Activity/GetStuActActivityList?pageIndex=" + pageId + "&activityType=&activityName=&activityStatus=&moduleCode=";
        Request request = new Request.Builder()
                .url(url)
                .header("Authorization", "Bearer " + access_token)
                .build();
        try {
            Map<String, String> map = new HashMap<String, String>();
            ObjectMapper objectMapper = new ObjectMapper();

            Response response = client.newCall(request).execute();

            if (response.body() != null) {
                return response.body().string();
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return "";
    }

    public Map<String, String> getSignDate(String access_token, String activityId) {
        String url = "https://ywtb.cuit.edu.cn/third_api/cxek/PhoneApi/api/Activity/GetStuActActivityDetail?id=" +
                activityId + "&isExam=false";
        Request request = new Request.Builder()
                .url(url)
                .header("Authorization", "Bearer " + access_token)
                .build();
        try {
            Map<String, String> map = new HashMap<String, String>();
            ObjectMapper objectMapper = new ObjectMapper();

            Response response = client.newCall(request).execute();

            if (response.body() != null) {
                String res = response.body().string();
                JSONObject jsonObject = JSON.parseObject(res);
                JSONObject activityInfo = (JSONObject) jsonObject.get("ActivityInfo");
                map.put("ActivityQDBeginDate", activityInfo.getString("ActivityQDBeginDate"));
                map.put("ActivityQDEndDate", activityInfo.getString("ActivityQDEndDate"));
                map.put("ActivityName", activityInfo.getString("ActivityName"));
            }

            return map;

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
