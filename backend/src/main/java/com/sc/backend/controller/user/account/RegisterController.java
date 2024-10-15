package com.sc.backend.controller.user.account;

import com.sc.backend.service.user.account.RegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@CrossOrigin
public class RegisterController {

    @Autowired
    private RegisterService registerService;

    @PostMapping("/api/user/account/register/")
    public Map<String, String> requestRegister(@RequestParam Map<String, String> params) {
        String username = params.get("username");
        String password = params.get("password");
        String confirmedPassword = params.get("confirmedPassword");
//        System.out.println("register: " + username + ' ' + password + ' ' + confirmedPassword);
//        String email = params.get("invitecode");
        return registerService.register(username, password, confirmedPassword);
    }
}
