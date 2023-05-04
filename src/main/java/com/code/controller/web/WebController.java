package com.code.controller.web;


import com.alibaba.fastjson2.JSON;
import com.code.common.logAop.LogAnnotation;
import com.code.entity.pf.Apply;
import com.code.entity.pf.Category;
import com.code.entity.pf.Project;
import com.code.entity.pf.Talent;
import com.code.entity.system.SysUser;
import com.code.service.pf.IApplyService;
import com.code.service.pf.ICategoryService;
import com.code.service.pf.IProjectService;
import com.code.service.pf.ITalentService;
import com.code.utils.*;
import com.code.vo.ProjectTalentParam;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * Controller
 * </p>
 *
 * @author ziye
 * @since 2022-10-30
 */
@RestController
@Api(tags = "前台管理接口")
@RequestMapping("/web")
public class WebController {

    @Resource
    private ICategoryService iCategoryService;

    @Resource
    private IProjectService iProjectService;

    @Resource
    private IApplyService iApplyService;

    /**
     * 获取类别列表
     *
     * @return Result
     */
    @ApiOperation(value = "获取类别列表")
    @GetMapping("/category")
    @LogAnnotation(module = "前台管理接口", operator = "获取类别列表")
    public Result categoryList() {
        List<Category> categoryList = iCategoryService.selectList();
        return Result.ok().put(categoryList);
    }

    /**
     * 获取项目列表
     *
     * @param map map
     * @return Result
     */
    @ApiOperation(value = "查询列表", notes = "{\"pageNum\": 1,\"pageSize\": 10")
    @PostMapping("/project")
    public Result projectList(@RequestBody(required = false) Map<String, String> map) {
        int pageNum = 1;//默认从第一页查询
        int pageSize = 10;//默认每页展示10条

        if (map.containsKey("pageNum")) {
            pageNum = Integer.parseInt(map.get("pageNum"));
        }
        if (map.containsKey("pageSize")) {
            pageSize = Integer.parseInt(map.get("pageSize"));
        }

        HashMap<String, Object> params = new HashMap<String, Object>();

        if (map.containsKey("projectName")) {
            params.put("projectName", map.get("projectName"));
        }

        if (map.containsKey("categoryId")) {
            params.put("categoryId", map.get("categoryId"));
        }

        if (map.containsKey("statusTime")) {
            params.put("statusTime", map.get("statusTime"));
        }

        PageInfo<Project> pages = iProjectService.selectPageList(params, pageNum, pageSize);
        return Result.ok().putPage(pages);

    }

    /**
     * 查看详情
     *
     * @param id id
     * @return Result
     */
    @ApiOperation(value = "查看详情")
    @GetMapping("/project/details")
    public Result details(@RequestParam Long id) {
        Project project = iProjectService.selectProjectById(id);
        return Result.ok().put(project);
    }

    /**
     * 获取项目申请
     *
     * @return Result
     */
    @ApiOperation(value = "获取项目申请")
    @PostMapping("/apply/add")
    @LogAnnotation(module = "前台管理接口", operator = "获取项目申请")
    public Result applyInsert(@RequestBody ProjectTalentParam param) {
        Apply apply = new Apply();
        apply.setProjectId(param.getProjectId());
        apply.setTalentId(param.getTalentId());

        Apply res = iApplyService.selectByProjectIdAndTalentId(param.getProjectId(), param.getTalentId());
        if (res != null) {
            Result.ok("您已提交过申请，可以在个人中心中查看");
        }

        int status = iApplyService.insertApply(apply);
        if (status == 0) {
            Result.error("提交失败");
        }
        return Result.ok();
    }

    /**
     * 获取项目申请
     *
     * @return Result
     */
    @ApiOperation(value = "获取项目申请")
    @PostMapping("/apply/pay")
    @LogAnnotation(module = "前台管理接口", operator = "获取项目申请")
    public Result selectByProjectIdAndTalentId(@RequestBody ProjectTalentParam param) {
        Apply apply = iApplyService.selectByProjectIdAndTalentId(param.getProjectId(), param.getTalentId());
        return Result.ok().put(apply);
    }


}
