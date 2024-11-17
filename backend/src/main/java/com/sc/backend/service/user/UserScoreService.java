package com.sc.backend.service.user;

import java.util.Map;

public interface UserScoreService {
    public Map<String, String> fetchScore(String access_token);
}
