package com.sc.backend.controller.cuit;


import com.sc.backend.service.cuit.GetActivityListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@CrossOrigin
public class GetActivityListController {

    @Autowired
    GetActivityListService getActivityListService;

    @GetMapping("/cuit/activity/list/")
    public String getActivityList(@RequestParam Map<String, String> params) {
        String access_token = params.get("access_token");
        String pageId = params.get("page_id");

//        System.out.println(pageId + "controller");

        return getActivityListService.getActivityList(access_token, pageId);
    }
}
