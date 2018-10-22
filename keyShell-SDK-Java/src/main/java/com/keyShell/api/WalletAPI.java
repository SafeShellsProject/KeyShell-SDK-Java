package com.keyShell.api;

import com.alibaba.fastjson.JSONObject;
import com.keyShell.api.Utils.HttpUtil;
import com.keyShell.api.Utils.TokenUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WalletAPI {

    public static Map<String, String> generateAccount(String userId, String token, String ip) {
        if (token == null || token.equals("")) {
            return null;
        }
        String url = "http://" + ip + ":19880/wallet/generateAccount";
        String paramToken = TokenUtil.validToken(token, userId);
        Map<String, String> param = new HashMap<>();
        param.put("userId", userId);
        param.put("token", paramToken);
        String result = HttpUtil.sendPost(url, param);
        Map mapTypes = (Map) JSONObject.parse(result);
        int code = (int) mapTypes.get("code");
        if (code != 200) {
            return null;
        }
        Map<String, String> dataMap = (Map<String, String>) mapTypes.get("data");
        Map<String, String> resultMap = new HashMap<>();
        resultMap.put("walletId", dataMap.get("walletId"));
        resultMap.put("accountAddress", dataMap.get("accountAddress"));
        return resultMap;
    }

    public static List<Map> listWallet(String userId, String ip) {
        String url = "http://" + ip + ":19880/wallet/queryByList";
        Map param = new HashMap<>();
        param.put("userId", userId);
        String result = HttpUtil.sendPost(url, param);
        Map mapTypes = (Map) JSONObject.parse(result);
        int code = (int) mapTypes.get("code");
        if (code !=200) {
            return null;
        }
        List<Map> dataMap = (List<Map>) mapTypes.get("data");
        List<Map> resultList = new ArrayList<>();
        for (int i = 0; i < dataMap.size(); i++) {
            Map<String, String> resultMap = new HashMap<>();
            Map mapTemp = dataMap.get(i);
            String walletId = (String) mapTemp.get("walletId");
            String accountAddress = (String) mapTemp.get("accountAddress");
            resultMap.put("walletId", walletId);
            resultMap.put("accountAddress", accountAddress);
            resultList.add(resultMap);
        }
        return resultList;
    }

    public static Map listWalletByPage(String userId, int pageNum, int pageSize, String ip) {
        String url = "http://" + ip + ":19880/wallet/queryByPage";
        Map<String, String> param = new HashMap<>();
        param.put("userId", userId);
        param.put("pageNum", String.format("%d", pageNum));
        param.put("pageSize", String.format("%d", pageSize));
        String result = HttpUtil.sendPost(url, param);
        Map mapTypes = (Map) JSONObject.parse(result);
        int code = (int) mapTypes.get("code");
        if (code !=200) {
            return null;
        }
        return mapTypes;
    }

    public static Map<String, String> selectWallet(String userId, String walletId, String ip) {
        String url = "http://" + ip + ":19880/wallet/selectByUser";
        Map<String, String> param = new HashMap<>();
        param.put("userId", userId);
        param.put("walletId", walletId);
        String result = HttpUtil.sendPost(url, param);
        Map mapTypes = (Map) JSONObject.parse(result);
        int code = (int) mapTypes.get("code");
        if (code != 200) {
            return null;
        }
        Map<String, String> dataMap = (Map<String, String>) mapTypes.get("data");
        Map<String, String> resultMap = new HashMap<>();
        resultMap.put("accountAddress", dataMap.get("accountAddress"));
        return resultMap;
    }

    public static Map<String, String> signature(String userId, String walletId, String toSign, String token, String ip) {
        if (token == null || token.equals("")) {
            return null;
        }
        String url = "http://" + ip + ":19880/wallet/signature";
        String paramToken = TokenUtil.validToken(token, userId + walletId + toSign);
        Map<String, String> param = new HashMap<>();
        param.put("userId", userId);
        param.put("walletId", walletId);
        param.put("toSign", toSign);
        param.put("token", paramToken);
        String result = HttpUtil.sendPost(url, param);
        Map mapTypes = (Map) JSONObject.parse(result);
        int code = (int) mapTypes.get("code");
        if (code != 200) {
            return null;
        }
        Map<String, String> dataMap = (Map<String, String>) mapTypes.get("data");
        Map<String, String> resultMap = new HashMap<>();
        resultMap.put("signature", dataMap.get("signature"));
        resultMap.put("PublicKey", dataMap.get("PublicKey"));
        return resultMap;
    }

    public static boolean deleteWallet(String userId, String walletId, String token, String ip) {
        if (token == null || token.equals("")) {
            return false;
        }
        String url = "http://" + ip + ":19880/wallet/deleteWallet";
        String paramToken = TokenUtil.validToken(token, userId + walletId);
        Map<String, String> param = new HashMap<>();
        param.put("userId", userId);
        param.put("walletId", walletId);
        param.put("token", paramToken);
        String result = HttpUtil.sendPost(url, param);
        Map mapTypes = (Map) JSONObject.parse(result);
        int resultCode = (int) mapTypes.get("code");
        boolean status = false;
        if (resultCode == 200) {
            status = true;
        }
        return status;
    }

    public static boolean deleteByUser(String userId, String token, String ip) {
        if (token == null || token.equals("")) {
            return false;
        }
        String url = "http://" + ip + ":19880/wallet/deleteByUser";
        String paramToken = TokenUtil.validToken(token, userId);
        Map<String, String> param = new HashMap<>();
        param.put("userId", userId);
        param.put("token", paramToken);
        String result = HttpUtil.sendPost(url, param);
        Map mapTypes = (Map) JSONObject.parse(result);
        int resultCode = (int) mapTypes.get("code");
        boolean status = false;
        if (resultCode == 200) {
            status = true;
        }
        return status;
    }

    //创建应用(仅对管理员开放，现阶段预留）
    public static boolean createApp(String appName, String ip) {
        String url = "http://" + ip + ":19880/wallet/createApp";
        Map<String, String> param = new HashMap<>();
        param.put("appName", appName);
        String result = HttpUtil.sendPost(url, param);
        Map mapTypes = (Map) JSONObject.parse(result);
        int resultCode = (int) mapTypes.get("code");
        boolean status = false;
        if (resultCode == 200) {
            status = true;
        }
        return status;
    }

    //删除应用（仅对管理员开放，现阶段预留）
    public static boolean deleteApp(String appName, String ip) {
        String url = "http://" + ip + ":19880/wallet/deleteApp";
        Map<String, String> param = new HashMap<>();
        param.put("appName", appName);
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
