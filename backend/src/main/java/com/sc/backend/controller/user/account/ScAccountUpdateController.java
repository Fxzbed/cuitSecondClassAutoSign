package com.sc.backend.controller.user.account;

import com.sc.backend.service.user.account.ScAccountUpdateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@CrossOrigin
public class ScAccountUpdateController {
    @Autowired
    ScAccountUpdateService scAccountUpdateService;

    @PostMapping("/api/user/account/scupdate/")
    public Map<String, String> ScAccountUpdate(@RequestParam Map<String, String> params) {
        String sc_username = params.get("sc_username");
        String sc_password = params.get("sc_password");
        return scAccountUpdateService.updateAccount(sc_username, sc_password);
    }
}
