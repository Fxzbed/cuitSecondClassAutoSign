package com.sc.backend.controller.user.account;

import com.sc.backend.service.user.account.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;


@RestController
@CrossOrigin
public class LoginController {
    @Autowired
    private LoginService loginService;

    @PostMapping("/api/user/account/token/")
    public Map<String, String> getToken(@RequestParam Map<String, String> params) {
        String username = params.get("username");
        String password = params.get("password");
        return loginService.getToken(username, password);
    }
}
