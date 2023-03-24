package com.code.utils;

import com.code.entity.system.LoginLog;
import com.code.entity.system.SysUser;
import com.code.service.system.ILoginService;
import eu.bitwalker.useragentutils.UserAgent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.concurrent.TimeUnit;

@Component
@Slf4j
public class ThreadService {

    @Resource
    private ILoginService iLoginService;

    @Resource
    private RedisUtil redisUtil;

    /**
     * 添加登录日志
     *
     * @param loginLog 登录日志信息
     */
    @Async("TaskExecutor")
    public void addLoginLog(LoginLog loginLog) {
        log.info("==================== 添加登录日志 start ====================");

        iLoginService.addLoginLog(loginLog);
        redisUtil.set("ONLINE_USER_" + loginLog.getUserId(), loginLog, 30, TimeUnit.MINUTES);

        log.info("==================== 添加登录日志 end ====================");
    }

}
