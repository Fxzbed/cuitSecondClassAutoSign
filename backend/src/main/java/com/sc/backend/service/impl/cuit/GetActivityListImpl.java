package com.sc.backend.service.impl.cuit;

import com.sc.backend.service.cuit.GetActivityListService;
import com.sc.backend.utils.HttpRequestUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GetActivityListImpl implements GetActivityListService {

    @Autowired
    HttpRequestUtil httpRequestUtil;

    @Override
    public String getActivityList(String access_token, String pageId) {
        return httpRequestUtil.GetActivityList(access_token, pageId);
    }
}
