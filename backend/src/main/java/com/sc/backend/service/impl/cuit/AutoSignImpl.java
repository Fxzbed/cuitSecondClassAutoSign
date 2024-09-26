package com.sc.backend.service.impl.cuit;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
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
        AtomicLong count = new AtomicLong();
        QueryWrapper<Signhash> queryWrapper = new QueryWrapper<>();
        queryWrapper.gt("begintime", timeStampUtil.getCurrentTimeStamp()).lt("endtime", timeStampUtil.getCurrentTimeStamp());
        signhashMapper.selectList(queryWrapper).forEach(s -> {
            String activityid = s.getActivityid();
             QueryWrapper<Sign> queryWrapper_ = new QueryWrapper<>();
             queryWrapper_.eq("activityid", activityid);
             queryWrapper_.eq("activitystatus", "等待签到中").or().eq("activitystatus", "签到失败");
             signMapper.selectList(queryWrapper_).forEach(s_ -> {
                 count.getAndIncrement();
                 if (httpRequestUtil.SignQD(httpRequestUtil.GetToken(s_.getScpass()), activityid)) {
                     System.out.println("[debug]:" + "[" + activityid + "]" + " sign success " + timeStampUtil.getCurrentTime());
                     s_.setActivitystatus("已签到");
                 } else {
                     System.out.println("[debug]:" + "[" + activityid + "]" + " sign fail " + timeStampUtil.getCurrentTime());
                     s_.setActivitystatus("签到失败");
                 }
             });
        });

        System.out.println(timeStampUtil.getCurrentTime() + " [info]: " + "任务轮询结束，本次轮询处理的任务有" + count.get() + "个");
    }
}
