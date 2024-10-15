package com.sc.backend.controller.user.account;

import com.sc.backend.service.user.account.PhotoUpdateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@CrossOrigin
public class PhotoUpdateController {
    @Autowired
    private PhotoUpdateService photoUpdateService;

    @PostMapping("/api/user/account/updatephoto/")
    public Map<String, String> getPhotoUpdateService(@RequestParam Map<String, String> map) {
        String url = map.get("url");
        return photoUpdateService.updatePhoto(url);
    }
}
