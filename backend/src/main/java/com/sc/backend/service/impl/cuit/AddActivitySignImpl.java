package com.sc.backend.service.impl.cuit;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.sc.backend.mapper.SignMapper;
import com.sc.backend.pojo.Sign;
import com.sc.backend.pojo.User;
import com.sc.backend.service.cuit.AddActivitySignService;
import com.sc.backend.service.impl.utils.UserDetailsImpl;
import com.sc.backend.utils.FormatTransUtil;
import com.sc.backend.utils.HttpRequestUtil;
import com.sc.backend.utils.TimeStampUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
public class AddActivitySignImpl implements AddActivitySignService {

    @Autowired
    private SignMapper signMapper;

    @Autowired
    private HttpRequestUtil httpRequestUtil;

    @Autowired
    private TimeStampUtil timeStampUtil;

    @Autowired
    private FormatTransUtil formatTransUtil;

    @Override
    public Map<String, String> addActivitySign(String activity_id, String password) {


        UsernamePasswordAuthenticationToken authentication
                = (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();

        UserDetailsImpl loginUser = (UserDetailsImpl) authentication.getPrincipal();
        User user = loginUser.getUser();

        QueryWrapper<Sign> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("activityid", activity_id);
        queryWrapper.eq("id", user.getId());
        List<Sign> signs = signMapper.selectList(queryWrapper);

        Map<String, String> map = new HashMap<>();
        map.put("error_message", "success");

        if (!signs.isEmpty()) {
            map.put("error_message", "fail");
            return map;
        }

        String tokenJson = httpRequestUtil.GetToken(password);
        JSONObject jsonObject = JSON.parseObject(tokenJson);
        String access_token = jsonObject.getString("access_token");

        Map<String ,String> signMsg = httpRequestUtil.GetSignDate(access_token, activity_id);
        if (!signMsg.isEmpty()) {
            if (Objects.equals(signMsg.get("SignWayText"), "扫码签到")) {
                String res = httpRequestUtil.SaveActivityApply(access_token, user.getScUsername(), activity_id, formatTransUtil.UrlEncode(timeStampUtil.getCurrentTime()));
                JSONObject json = JSON.parseObject(res);
                int errcode = json.getIntValue("errcode");
                if (errcode == 0) {
                    Sign sign = new Sign(password, activity_id, signMsg.get("ActivityQDBeginDate"), user.getId(), "等待签到中", signMsg.get("ActivityQDEndDate"), signMsg.get("ActivityName"));
                    signMapper.insert(sign);
                } else {
                    map.put("error_message", "fail");
                }
            } else {
                map.put("error_message", "signTypeError");
            }
        } else {
            map.put("error_message", "fail");
        }

        return map;
    }
}
