package com.sc.backend.service.impl.user;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sc.backend.pojo.User;
import com.sc.backend.service.impl.utils.UserDetailsImpl;
import com.sc.backend.service.user.UserScoreService;
import com.sc.backend.utils.HttpRequestUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Service
public class UserScoreServiceImpl implements UserScoreService {
    @Autowired
    HttpRequestUtil httpRequestUtil;

    @Override
    public Map<String, String> fetchScore(String access_token) {
        Map<String, String> map = new HashMap<>();
        UsernamePasswordAuthenticationToken authentication
                = (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();

        UserDetailsImpl loginUser = (UserDetailsImpl) authentication.getPrincipal();
        User user = loginUser.getUser();

        String res = httpRequestUtil.GetStuDetail(access_token, user.getScUsername());
        if (res != null) {
            JSONObject jsonObject = JSON.parseObject(res);
            jsonObject = jsonObject.getJSONObject("Info");
            map.put("error_message", "success");
            map.put("scoreSD", jsonObject.getString("AGetNum"));
            map.put("scoreXS", jsonObject.getString("BGetNum"));
            map.put("scoreYS", jsonObject.getString("CGetNum"));
            map.put("scoreCT", jsonObject.getString("DGetNum"));
            map.put("scoreRJ", jsonObject.getString("EGetNum"));
            map.put("scoreXL", jsonObject.getString("FGetNum"));
            map.put("scoreSH", jsonObject.getString("GGetNum"));
            map.put("scoreSHG", jsonObject.getString("HGetNum"));
            return map;
        }

        map.put("error_message", "fail");
        return map;
    }


}
