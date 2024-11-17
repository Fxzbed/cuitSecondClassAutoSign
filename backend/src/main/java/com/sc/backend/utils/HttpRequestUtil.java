package com.sc.backend.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import okhttp3.*;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
public class HttpRequestUtil {
    private final TimeStampUtil timeStampUtil;
    private OkHttpClient client = new OkHttpClient();

    public HttpRequestUtil(TimeStampUtil timeStampUtil) {
        this.timeStampUtil = timeStampUtil;
    }

    public String GetToken(String password) {
        String url = "https://ywtb.cuit.edu.cn/third_api/cxek/PhoneApi/api/Account/Login?code=123&state=GZState";
        MediaType mediaType = MediaType.get("application/x-www-form-urlencoded");
        String Parmas = "grant_type=password&username=&loginInfo=&password=" + password;
        RequestBody body = RequestBody.create(Parmas, mediaType);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        try {
            Response response = client.newCall(request).execute();

            if (response.body() != null) {
                if (response.code() == 200) {
                    String res = response.body().string();
                    System.out.println(res);
                    return res;
                } else {
                    return null;
                }
            } else {
                return null;
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String GetActivityList (String access_token, String pageId) {
        String url = "https://ywtb.cuit.edu.cn/third_api/cxek/PhoneApi/api/Activity/GetStuActActivityList?pageIndex=" + pageId + "&activityType=&activityName=&activityStatus=&moduleCode=";
        Request request = new Request.Builder()
                .url(url)
                .header("Authorization", "Bearer " + access_token)
                .build();
        try {
            Response response = client.newCall(request).execute();

            if (response.body() != null) {
                return response.body().string();
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return "";
    }

    public Map<String, String> GetSignDate(String access_token, String activityId) {
        String url = "https://ywtb.cuit.edu.cn/third_api/cxek/PhoneApi/api/Activity/GetStuActActivityDetail?id=" +
                activityId + "&isExam=false";
        Request request = new Request.Builder()
                .url(url)
                .header("Authorization", "Bearer " + access_token)
                .build();
        try {
            Map<String, String> map = new HashMap<String, String>();
            Response response = client.newCall(request).execute();

            if (response.body() != null) {
                String res = response.body().string();
                JSONObject jsonObject = JSON.parseObject(res);
                JSONObject activityInfo = (JSONObject) jsonObject.get("ActivityInfo");
                map.put("ActivityQDBeginDate", activityInfo.getString("ActivityQDBeginDate"));
                map.put("ActivityQDEndDate", activityInfo.getString("ActivityQDEndDate"));
                map.put("ActivityName", activityInfo.getString("ActivityName"));
                map.put("SignWayText", activityInfo.getString("SignWayText"));
                map.put("NextStepMsg", activityInfo.getString("NextStepMsg"));
                JSONObject applyInfo = (JSONObject) jsonObject.get("ApplyInfo");
                map.put("NoApplyReaon", applyInfo.getString("NoApplyReaon"));
            }

            return map;

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String SaveActivityApply (String access_token, String sc_username, String activity_id, String date) {
        String url = "https://ywtb.cuit.edu.cn/third_api/cxek/PhoneApi/api/Activity/SaveActActivityApply";
        MediaType mediaType = MediaType.get("application/x-www-form-urlencoded");
        String Parmas = "ApplyInfo%5BNoApplyReaon%5D=" +
                "&" +
                "ApplyInfo%5BId%5D=00000000-0000-0000-0000-000000000000" +
                "&" +
                "ApplyInfo%5BActivityId%5D=" + activity_id  +
                "&" +
                "ApplyInfo%5BStudentId%5D=" + sc_username +
                "&" +
                "ApplyInfo%5BUserType%5D=S" +
                "&" +
                "ApplyInfo%5BActivityRoleId%5D=00000000-0000-0000-0000-000000000000" +
                "&" +
                "ApplyInfo%5BInsertUserId%5D=" + sc_username +
                "&" +
                "ApplyInfo%5BInsertTime%5D=" + date +
                "&" +
                "ApplyInfo%5BAttendanceStatus%5D=&ApplyInfo%5BLeaveReason%5D=&ApplyInfo%5BLeaveStatus%5D=&ApplyInfo%5BLeaveThing%5D=&ApplyInfo%5BLeaveDate%5D=&ApplyInfo%5BLeaveMen%5D=&ApplyInfo%5BSignInTime%5D=&ApplyInfo%5BSignOutTime%5D=&ApplyInfo%5BRemoveBlack%5D=&ApplyInfo%5BDataSoure%5D=&ApplyInfo%5BBigState%5D=&ApplyInfo%5BStatusName%5D=&ApplyInfo%5BIsEdit%5D=1&ApplyInfo%5BNextStepMsg%5D=&ApplyInfo%5BFlowStatus%5D=0&ApplyInfo%5BCollegeAsName%5D=%E5%AD%A6%E9%99%A2";
        RequestBody body = RequestBody.create(Parmas, mediaType);
        Request request = new Request.Builder()
                .url(url)
                .header("Authorization", "Bearer " + access_token)
                .post(body)
                .build();
        try {
            Response response = client.newCall(request).execute();

            if (response.body() != null) {
                if (response.code() == 200) {
                    return response.body().string();
                } else {
                    return null;
                }
            } else {
                return null;
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean SignQD(String access_token, String activity_id) {
        String url = "https://ywtb.cuit.edu.cn/third_api/cxek/PhoneApi/api/Activity/StuSaveQrCode?content=" +
                activity_id +
                "|" + String.valueOf(timeStampUtil.getCurrentTimeStamp() / 1000) + "|QD";
        Request request = new Request.Builder()
                .url(url)
                .header("Authorization", "Bearer " + access_token)
                .build();
        try {
            Response response = client.newCall(request).execute();

            if (response.body() != null) {
                String res = response.body().string();
                System.out.println(res);
                JSONObject jsonObject = JSON.parseObject(res);
                int errcode = jsonObject.getIntValue("errcode");
                if (errcode == 0) {
                    SignQT(access_token, activity_id);
                    return true;
                }
            }
            return false;

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean SignQT(String access_token, String activity_id) {
        String url = "https://ywtb.cuit.edu.cn/third_api/cxek/PhoneApi/api/Activity/StuSaveQrCode?content=" +
                activity_id +
                "|1799999999|QT";
        Request request = new Request.Builder()
                .url(url)
                .header("Authorization", "Bearer " + access_token)
                .build();
        try {
            Response response = client.newCall(request).execute();

            if (response.body() != null) {
                String res = response.body().string();
                System.out.println(res);
                JSONObject jsonObject = JSON.parseObject(res);
                int errcode = jsonObject.getIntValue("errcode");
                return errcode == 0;
            }
            return false;

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String GetStuDetail(String access_token, String sc_username) {
        String url = "https://ywtb.cuit.edu.cn/third_api/cxek/PhoneApi/api/Graduation/GetStuDetail?" +
                "studentId=" + sc_username
                + "&yearValue=";
        Request request = new Request.Builder()
                .url(url)
                .header("Authorization", "Bearer " + access_token)
                .build();

        try {
            Response response = client.newCall(request).execute();
            if (response.body() != null) {
                return response.body().string();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return "";
    }
}
