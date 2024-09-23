package com.sc.backend.service.impl.user.account;

import com.sc.backend.mapper.UserMapper;
import com.sc.backend.pojo.User;
import com.sc.backend.service.impl.utils.UserDetailsImpl;
import com.sc.backend.service.user.account.PhotoUpdateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class PhotoUpdateServiceImpl implements PhotoUpdateService {
    @Autowired
    private UserMapper userMapper;

    @Override
    public Map<String, String> updatePhoto(String url) {
        UsernamePasswordAuthenticationToken authentication
                = (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();

        UserDetailsImpl loginUser = (UserDetailsImpl) authentication.getPrincipal();
        User user = loginUser.getUser();

        Map<String, String> map = new HashMap<>();

        user.setPhoto(url);
        userMapper.updateById(user);

        map.put("error_message", "success");
        return map;
    }
}
