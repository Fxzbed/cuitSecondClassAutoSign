package com.sc.backend.service.impl.cuit;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.sc.backend.mapper.SignMapper;
import com.sc.backend.mapper.SignhashMapper;
import com.sc.backend.pojo.Sign;
import com.sc.backend.pojo.Signhash;
import com.sc.backend.service.cuit.AutoSignService;
import com.sc.backend.utils.HttpRequestUtil;
import com.sc.backend.utils.TimeStampUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.atomic.AtomicLong;

@Service
public class AutoSignImpl implements AutoSignService {

    @Autowired
    SignhashMapper signhashMapper;

    @Autowired
    SignMapper signMapper;

    @Autowired
    private TimeStampUtil timeStampUtil;

    @Autowired
    private HttpRequestUtil httpRequestUtil;

    @Override
    public void taskPolling() {
//        System.out.println(timeStampUtil.getCurrentTimeStamp());
        AtomicLong count = new AtomicLong();
        QueryWrapper<Signhash> queryWrapper = new QueryWrapper<>();
        queryWrapper.gt("endtime", timeStampUtil.getCurrentTimeStamp()).lt("begintime", timeStampUtil.getCurrentTimeStamp());
        signhashMapper.selectList(queryWrapper).forEach(s -> {
            System.out.println("signhash匹配：" + s.getActivityid());
            String activityid = s.getActivityid();
             QueryWrapper<Sign> queryWrapper_ = new QueryWrapper<>();
             queryWrapper_.eq("activityid", activityid).eq("activitystatus", "等待签到中").or()
                     .eq("activityid", activityid).eq("activitystatus", "签到失败");
             signMapper.selectList(queryWrapper_).forEach(s_ -> {
                 count.getAndIncrement();
                 String access_token = httpRequestUtil.GetToken(s_.getScpass());
                 JSONObject json = JSONObject.parseObject(access_token);
                 access_token = json.getString("access_token");
                 if (httpRequestUtil.SignQD(access_token, activityid)) {
                     System.out.println("[debug]:" + "[" + activityid + "]" + "userId: " + s_.getId() + " sign success " + timeStampUtil.getCurrentTime());
                     s_.setActivitystatus("已签到");
                 } else {
                     System.out.println("[debug]:" + "[" + activityid + "]" + " sign fail " + timeStampUtil.getCurrentTime());
                     s_.setActivitystatus("签到失败");
                 }
                 signMapper.update(s_, new UpdateWrapper<Sign>().eq("activityid", activityid).eq("id", s_.getId()));
             });
        });

        System.out.println(timeStampUtil.getCurrentTime() + " [info]: " + "任务轮询结束，本次轮询处理的任务有" + count.get() + "个");
    }
}
