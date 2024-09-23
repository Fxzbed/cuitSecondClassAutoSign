package com.sc.backend.service.impl.user.account;

import com.sc.backend.mapper.UserMapper;
import com.sc.backend.pojo.User;
import com.sc.backend.service.impl.utils.UserDetailsImpl;
import com.sc.backend.service.user.account.ScAccountUpdateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class ScAccountUpdateImpl implements ScAccountUpdateService {
    @Autowired
    private UserMapper userMapper;

    @Override
    public Map<String, String> updateAccount(String sc_username, String sc_password) {
        UsernamePasswordAuthenticationToken authentication
                = (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();

        UserDetailsImpl loginUser = (UserDetailsImpl) authentication.getPrincipal();
        User user = loginUser.getUser();

        Map<String, String> map = new HashMap<>();

        user.setScUsername(sc_username);
        user.setScPassword(sc_password);
        userMapper.updateById(user);

        map.put("error_message", "success");
        return map;
    }

}
