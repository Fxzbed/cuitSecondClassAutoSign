package com.sc.backend.service.impl.cuit;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.sc.backend.mapper.SignMapper;
import com.sc.backend.pojo.Sign;
import com.sc.backend.pojo.User;
import com.sc.backend.service.cuit.GetActivitySignService;
import com.sc.backend.service.impl.utils.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class GetActivitySignImpl implements GetActivitySignService {

    @Autowired
    private SignMapper signMapper;

    @Override
    public Map<String, String> getActivitySign() {
        UsernamePasswordAuthenticationToken authentication
                = (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();

        UserDetailsImpl loginUser = (UserDetailsImpl) authentication.getPrincipal();
        User user = loginUser.getUser();

        QueryWrapper<Sign> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id", user.getId());
        List<Sign> signList = signMapper.selectList(queryWrapper);

        Map<String, String> res = new HashMap<>();

        if (!signList.isEmpty()) {
            for (Sign sign : signList) {
                res.put(sign.getActivityname(), sign.getActivitystatus());
            }
        }

        res.put("error_message", "success");
        return res;
    }
}
