package com.sc.backend.controller.cuit;

import com.sc.backend.service.cuit.AddActivitySignService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@CrossOrigin
public class AddActivitySignController {
    @Autowired
    private AddActivitySignService addActivitySignService;

    @PostMapping("/cuit/sign/add/")
    public Map<String, String> addActivitySign (@RequestParam Map<String, String> params) {
        String activity_id = params.get("activity_id");
        String password = params.get("password");

        return addActivitySignService.addActivitySign(activity_id, password);
    }
}
