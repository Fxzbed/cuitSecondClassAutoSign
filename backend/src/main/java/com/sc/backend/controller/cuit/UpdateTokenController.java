package com.sc.backend.controller.cuit;

import com.sc.backend.mapper.UserMapper;
import com.sc.backend.pojo.User;
import com.sc.backend.service.cuit.UpdateTokenService;
import com.sc.backend.service.impl.utils.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@CrossOrigin
public class UpdateTokenController {
    @Autowired
    private UpdateTokenService updateTokenService;

    @Autowired
    private UserMapper userMapper;

    @GetMapping("/api/cuit/token/")
    public String updateToken(@RequestParam Map<String, String> params) {
        UsernamePasswordAuthenticationToken authentication
                = (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();

        UserDetailsImpl loginUser = (UserDetailsImpl) authentication.getPrincipal();
        User user = loginUser.getUser();

        String ScPassword = params.get("http_password");
//        System.out.println(ScPassword);

        return updateTokenService.updateToken(ScPassword);
    }
}
