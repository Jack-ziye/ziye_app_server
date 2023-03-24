package com.code.controller.system;

import com.alibaba.fastjson2.JSON;
import com.code.common.logAop.LogAnnotation;
import com.code.utils.Result;
import com.code.utils.ResultCode;
import com.code.vo.RoleParam;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.*;
import com.code.entity.system.Role;
import com.code.service.system.IRoleService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import com.github.pagehelper.PageInfo;

/**
 * <p>
 * Controller
 * </p>
 *
 * @author ziye
 * @since 2022-10-30
 */
@RestController
@Api(tags = "角色管理接口")
@RequestMapping("/role")
public class RoleController {

    private static final Logger LOGGER = LoggerFactory.getLogger(RoleController.class);

    @Resource
    private IRoleService iRoleService;

    /**
     * 查询列表
     *
     * @param map map
     * @return Result
     */
    @ApiOperation(value = "查询列表")
    @PostMapping("/list")
    @RequiresPermissions(value = {"system:role:list"})
    public Result pageList(HttpServletResponse response, @RequestBody Map<String, String> map) {
        response.setCharacterEncoding("utf-8");
        int pageNum = 1;//默认从第一页查询
        int pageSize = 10;//默认每页展示100000条
        try {
            if (map.containsKey("pageNum")) {
                pageNum = Integer.parseInt(map.get("pageNum"));
            }
            if (map.containsKey("pageSize")) {
                pageSize = Integer.parseInt(map.get("pageSize"));
            }

            HashMap<String, Object> params = new HashMap<String, Object>();

            if (map.containsKey("roleName")) {
                params.put("roleName", map.get("roleName"));
            }
            if (map.containsKey("creatTimeFrom") && map.containsKey("creatTimeTo")) {
                params.put("creatTimeFrom", map.get("creatTimeFrom"));
                params.put("creatTimeTo", map.get("creatTimeTo"));
            }

            PageInfo<Role> pages = iRoleService.selectPageList(params, pageNum, pageSize);
            return Result.ok().putPage(pages);
        } catch (Exception e) {
            LOGGER.error(e.toString());
            return Result.error(e.getMessage());
        }
    }

    /**
     * 新增数据
     *
     * @param roleParam roleParam
     * @return Result
     */
    @ApiOperation(value = "新增数据")
    @PostMapping("/insert")
    @RequiresPermissions(value = {"system:role:insert"})
    public Result insertRole(@RequestBody RoleParam roleParam) {
        Role role = JSON.parseObject(JSON.toJSONString(roleParam), Role.class);
        int status = iRoleService.addRole(role);
        if (status > 0) {
            return Result.ok("添加成功");
        } else {
            return Result.error(ResultCode.ADD_ERROR);
        }
    }

    /**
     * 修改数据
     *
     * @param role role
     * @return Result
     */
    @ApiOperation(value = "修改数据")
    @PostMapping("/update")
    @RequiresPermissions(value = {"system:role:update"})
    public Result updateRole(@RequestBody Role role) {
        int status = iRoleService.editRole(role);
        if (status > 0) {
            return Result.ok("更新成功");
        } else {
            return Result.error(ResultCode.UPDATE_ERROR);
        }
    }

    /**
     * 用户状态变更
     *
     * @param roleId roleId
     * @return Result
     */
    @ApiOperation(value = "用户状态变更")
    @GetMapping("/update/status")
    @RequiresPermissions(value = {"system:role:update"})
    @LogAnnotation(module = "用户管理接口", operator = "用户状态变更")
    public Result updateStatus(@RequestParam(value = "id") Long roleId) {
        int status = iRoleService.updateStatus(roleId);
        if (status == 0) {
            return Result.error(ResultCode.UPDATE_ERROR);
        }
        return Result.ok();
    }

    /**
     * 查看详情
     *
     * @param id id
     * @return Result
     */
    @ApiOperation(value = "查看详情")
    @GetMapping("/info")
    @RequiresPermissions(value = {"system:role:info"})
    public Result info(@RequestParam Long id) {
        Role role = iRoleService.getDetailsById(id);
        return Result.ok().put(role);
    }

    /**
     * 根据ID删除记录
     *
     * @param id id
     * @return Result
     */
    @ApiOperation(value = "根据ID删除记录")
    @PostMapping("/delete")
    @RequiresPermissions(value = {"system:role:delete"})
    public Result deleteRole(@RequestParam Long id) {
        int status = iRoleService.deleteById(id);
        if (status > 0) {
            return Result.ok("删除成功");
        } else {
            return Result.error(ResultCode.DELETE_ERROR);
        }
    }

    /**
     * 批量删除数据
     *
     * @param ids ID集合
     * @return Result
     */
    @ApiOperation(value = "批量删除数据")
    @PostMapping("/deleteBatch")
    @RequiresPermissions(value = {"system:role:delete"})
    public Result deleteBatch(@RequestParam(value = "ids", required = true) List<Long> ids) {
        int status = iRoleService.deleteBatch(ids);
        if (status > 0) {
            return Result.ok("删除成功");
        } else {
            return Result.error(ResultCode.DELETE_ERROR);
        }
    }

}
