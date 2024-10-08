package com.sc.backend.service.impl.user.account;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.sc.backend.mapper.UserMapper;
import com.sc.backend.pojo.User;
import com.sc.backend.service.user.account.RegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class RegisterServiceImpl implements RegisterService {
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public Map<String, String> register(String username, String password, String confirmedPassword) {
        Map<String, String> map = new HashMap<>();
        if (username == null) {
            map.put("error_message", "用户名不能为空");
            return map;
        }

        if (password == null || confirmedPassword == null) {
            map.put("error_message", "密码不能为空");
            return map;
        }

        username = username.trim();
        if (username.isEmpty()) {
            map.put("error_message", "用户名不能为空");
            return map;
        }

        if (password.isEmpty() || confirmedPassword.isEmpty()) {
            map.put("error_message", "密码不能为空");
            return map;
        }

        if (username.length() > 100) {
            map.put("error_message", "用户名长度过长");
            return map;
        }

        if (password.length() > 100 || confirmedPassword.length() > 100) {
            map.put("error_message", "密码长度过长");
            return map;
        }

        if (!password.equals(confirmedPassword)) {
            map.put("error_message", "密码不一致");
            return map;
        }
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", username);
        List<User> userList = userMapper.selectList(queryWrapper);

        if (!userList.isEmpty()) {
            map.put("error_message", "该用户已存在");
            return map;
        }

        String encodedPassword = passwordEncoder.encode(password);
        String photo = "";
        User user = new User(null, username, encodedPassword, photo, null, null);
        userMapper.insert(user);

        map.put("error_message", "success");
        return map;
    }
}
