package com.sc.backend.service.user.account;

import java.util.Map;

public interface ScAccountUpdateService {
    public Map<String,String> updateAccount(String sc_username, String sc_password);
}
