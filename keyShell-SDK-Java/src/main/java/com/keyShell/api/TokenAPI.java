package com.keyShell.api;

import com.alibaba.fastjson.JSONObject;
import com.keyShell.api.Utils.HttpUtil;

import java.util.HashMap;
import java.util.Map;

public class TokenAPI {

    public static boolean diableToken(String userId, String ip) {
        String url = "http://" + ip + ":19880/userInfo/disableToken";
        Map<String, String> param = new HashMap<>();
        param.put("userId", userId);
        String result = HttpUtil.sendPost(url, param);
        Map mapTypes = (Map) JSONObject.parse(result);
        int resultCode = (int) mapTypes.get("code");
        boolean status = false;
        if (resultCode == 200) {
            status = true;
        }
        return status;
    }

    public static boolean validToken(String userId, String userToken, String ip) {
        String url = "http://" + ip + ":19880/userInfo/validToken";
        Map<String, String> param = new HashMap<>();
        param.put("userId", userId);
        param.put("userToken", userToken);
        String result = HttpUtil.sendPost(url, param);
        Map mapTypes = (Map) JSONObject.parse(result);
        int resultCode = (int) mapTypes.get("code");
        boolean status = false;
        if (resultCode == 200) {
            status = true;
        }
        return status;
    }

}
