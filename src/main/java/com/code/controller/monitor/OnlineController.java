package com.code.controller.monitor;

import com.alibaba.fastjson2.JSON;
import com.code.entity.system.LoginLog;
import com.code.utils.RedisUtil;
import com.code.utils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.core.util.JsonUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@Api(tags = "在线用户管理接口")
@RequestMapping("/monitor/online")
public class OnlineController {

    @Resource
    private RedisUtil redisUtil;

    /**
     * 获取在线用户列表
     *
     * @return Result
     */
    @ApiOperation(value = "获取在线用户列表")
    @PostMapping
    public Result getServer(@RequestBody Map<String, String> map) {
        List<String> keys = redisUtil.scan("ONLINE_USER_*");
        ArrayList<LoginLog> loginLogs = new ArrayList<>();
        for (String key : keys) {
            Object getLoginLog = redisUtil.get(key);
            LoginLog loginLog = JSON.parseObject(JSON.toJSONString(getLoginLog), LoginLog.class);
            if (StringUtils.isNotBlank(map.get("address"))) {
                if (loginLog.getAddress().contains(map.get("address"))) {
                    loginLogs.add(loginLog);
                }
            } else if (StringUtils.isNotBlank(map.get("username"))) {
                if (loginLog.getUsername().contains(map.get("username"))) {
                    loginLogs.add(loginLog);
                }
            } else {
                loginLogs.add(loginLog);
            }
        }
        return Result.ok().put(loginLogs);
    }

}
