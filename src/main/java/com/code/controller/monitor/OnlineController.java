package com.code.controller.monitor;

import com.alibaba.fastjson2.JSON;
import com.code.entity.monitor.OnlineUser;
import com.code.entity.system.LoginLog;
import com.code.entity.system.SysUser;
import com.code.service.system.ILoginService;
import com.code.utils.*;
import eu.bitwalker.useragentutils.UserAgent;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.catalina.User;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.core.util.JsonUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.concurrent.TimeUnit;

@RestController
@Api(tags = "在线用户管理接口")
@RequestMapping("/monitor/online")
public class OnlineController {

    @Resource
    private RedisUtil redisUtil;

    @Resource
    private ILoginService iLoginService;


    /**
     * 获取在线用户列表
     *
     * @return Result
     */
    @ApiOperation(value = "获取在线用户列表")
    @PostMapping("/select")
    public Result getOnlineUser(@RequestBody Map<String, String> map) {
        List<String> keys = redisUtil.scan("ONLINE_USER_*");
        ArrayList<OnlineUser> onlineUsers = new ArrayList<>();
        for (String key : keys) {
            OnlineUser onlineUser = JSON.parseObject(JSON.toJSONString(redisUtil.get(key)), OnlineUser.class);
            onlineUsers.add(onlineUser);
        }

        return Result.ok().put(onlineUsers);
    }

    /**
     * 更新在线用户状态
     *
     * @return Result
     */
    @ApiOperation(value = "更新在线用户状态")
    @GetMapping("/update")
    public Result updateOnlineUser() {
        SysUser sysUser = UserThreadLocal.get();
        LoginLog loginLog = iLoginService.selectLatest(sysUser.getUserId());
        OnlineUser onlineUser = JSON.parseObject(JSON.toJSONString(loginLog), OnlineUser.class);
        redisUtil.set("ONLINE_USER_" + sysUser.getUserId(), onlineUser, 10, TimeUnit.SECONDS);
        return Result.ok();
    }

    /**
     * 强退在线用户
     *
     * @return Result
     */
    @ApiOperation(value = "强退在线用户")
    @GetMapping("/delete")
    public Result deleteOnlineUser(@RequestParam(value = "token") String token) {
        Map<String, Object> map = JwtToken.checkToken(token);
        Integer userId = (Integer) map.get("userId");
        redisUtil.delete("ONLINE_USER_" + userId);
        iLoginService.loginOut(token);
        return Result.ok();
    }

}
