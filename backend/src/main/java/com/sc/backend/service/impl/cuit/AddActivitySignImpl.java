package com.sc.backend.service.impl.cuit;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.sc.backend.mapper.SignMapper;
import com.sc.backend.pojo.Sign;
import com.sc.backend.pojo.User;
import com.sc.backend.service.cuit.AddActivitySignService;
import com.sc.backend.service.impl.utils.UserDetailsImpl;
import com.sc.backend.utils.HttpRequestUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AddActivitySignImpl implements AddActivitySignService {

    @Autowired
    private SignMapper signMapper;

    @Autowired
    private HttpRequestUtil httpRequestUtil;

    @Override
    public Map<String, String> addActivitySign(String activity_id, String password) {


        UsernamePasswordAuthenticationToken authentication
                = (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();

        UserDetailsImpl loginUser = (UserDetailsImpl) authentication.getPrincipal();
        User user = loginUser.getUser();

        QueryWrapper<Sign> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("activityid", activity_id);
        List<Sign> signs = signMapper.selectList(queryWrapper);

        Map<String, String> map = new HashMap<>();
        if (!signs.isEmpty()) {
            map.put("error_message", "fail");
            return map;
        }

        String tokenJson = httpRequestUtil.GetToken(password);
        JSONObject jsonObject = JSON.parseObject(tokenJson);
        String access_token = jsonObject.getString("access_token");

        Map<String ,String> signMsg = httpRequestUtil.getSignDate(access_token, activity_id);
        if (!signMsg.isEmpty()) {
            Sign sign = new Sign(password, activity_id, signMsg.get("ActivityQDBeginDate"), user.getId(), "wait", signMsg.get("ActivityQDEndDate"), signMsg.get("ActivityName"));
            signMapper.insert(sign);
        } else {
            map.put("error_message", "fail");
        }

        map.put("error_message", "success");
        return map;
    }
}
