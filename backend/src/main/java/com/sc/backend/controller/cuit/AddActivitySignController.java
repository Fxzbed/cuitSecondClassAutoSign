package com.sc.backend.controller.cuit;

import com.sc.backend.service.cuit.AddActivitySignService;
import com.sc.backend.utils.FormatTransUtil;
import com.sc.backend.utils.TimeStampUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

@RestController
@CrossOrigin
public class AddActivitySignController {
    @Autowired
    private AddActivitySignService addActivitySignService;

    @Autowired
    private TimeStampUtil timeStampUtil;

    @Autowired
    private FormatTransUtil formatTransUtil;

    @PostMapping("/cuit/sign/add/")
    public Map<String, String> addActivitySign (@RequestParam Map<String, String> params) {
        String activity_id = params.get("activity_id");
        String password = params.get("password");

        System.out.println(formatTransUtil.UrlEncode(timeStampUtil.getCurrentTime()));

        return addActivitySignService.addActivitySign(activity_id, password);
    }
}
