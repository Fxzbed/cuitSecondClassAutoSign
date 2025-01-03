package com.sc.backend.controller.user.account;

import com.sc.backend.service.user.account.InfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@CrossOrigin
public class InfoController {
    @Autowired
    private InfoService infoService;

    @GetMapping("/api/user/account/getinfo/")
    public Map<String, String> getInfo() {
        return infoService.getinfo();
    }
}
