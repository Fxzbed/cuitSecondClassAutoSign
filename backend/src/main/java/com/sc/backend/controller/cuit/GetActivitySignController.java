package com.sc.backend.controller.cuit;

import com.sc.backend.service.cuit.GetActivitySignService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@CrossOrigin
public class GetActivitySignController {
    @Autowired
    GetActivitySignService getActivitySignService;

    @GetMapping("/cuit/sign/list/")
    public Map<String, String> GetActivitySignController() {
        return getActivitySignService.getActivitySign();
    }
}
