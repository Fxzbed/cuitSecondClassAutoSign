package com.sc.backend.controller.user;

import com.sc.backend.service.user.UserScoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@CrossOrigin
public class UserScoreController {
    @Autowired
    private UserScoreService userScoreService;

    @PostMapping("/api/user/account/getScore/")
    public Map<String, String> getScore(@RequestParam Map<String, String> map) {
        return userScoreService.fetchScore(map.get("access_token"));
    }
}
