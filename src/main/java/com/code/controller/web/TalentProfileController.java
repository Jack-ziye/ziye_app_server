package com.code.controller.web;


import com.code.common.logAop.LogAnnotation;
import com.code.entity.pf.Apply;
import com.code.entity.pf.Category;
import com.code.entity.pf.Project;
import com.code.entity.pf.Talent;
import com.code.service.pf.IApplyService;
import com.code.service.pf.ICategoryService;
import com.code.service.pf.IProjectService;
import com.code.service.pf.ITalentService;
import com.code.utils.Result;
import com.code.utils.ResultCode;
import com.code.utils.TalentThreadLocal;
import com.code.vo.ProjectTalentParam;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
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
@Api(tags = "前台用户管理接口")
@RequestMapping("/talent/current")
public class TalentProfileController {

    @Resource
    private IApplyService iApplyService;

    @Resource
    private ITalentService iTalentService;


    /**
     * 更新人才信息
     *
     * @param talent 人才
     * @return Result
     */
    @ApiOperation(value = "更新人才信息")
    @PostMapping("/update")
    @LogAnnotation(module = "前台用户管理接口", operator = "更新人才信息")
    public Result updateTalent(@RequestBody Talent talent) {
        int status = iTalentService.updateTalent(talent);
        if (status > 0) {
            return Result.ok("更新成功");
        } else {
            return Result.error(ResultCode.UPDATE_ERROR);
        }
    }


    /**
     * 获取当前用户报名申请
     *
     * @return Result
     */
    @ApiOperation(value = "获取当前用户报名申请")
    @PostMapping("/apply/list")
    @LogAnnotation(module = "前台用户管理接口", operator = "获取当前用户报名申请")
    public Result selectCurrentApply(@RequestBody(required = false) Map<String, String> map) {

        int pageNum = 1;//默认从第一页查询
        int pageSize = 10;//默认每页展示10条

        if (map.containsKey("pageNum")) {
            pageNum = Integer.parseInt(map.get("pageNum"));
        }
        if (map.containsKey("pageSize")) {
            pageSize = Integer.parseInt(map.get("pageSize"));
        }

        HashMap<String, Object> params = new HashMap<String, Object>();
        Talent talent = TalentThreadLocal.get();
        params.put("talentId", talent.getTalentId());

        PageInfo<Apply> pages = iApplyService.selectPageList(params, pageNum, pageSize);
        return Result.ok().putPage(pages);
    }


    /**
     * 查看详情
     *
     * @return Result
     */
    @ApiOperation(value = "查看详情")
    @GetMapping("/apply/details")
    @LogAnnotation(module = "前台用户管理接口", operator = "查看详情")
    public Result selectApplyById(@RequestParam(value = "id") Long applyId) {

        Apply apply = iApplyService.selectDetails(applyId);
        return Result.ok().put(apply);

    }

    /**
     * 当前用户根据项目id获取报名申请
     *
     * @return Result
     */
    @ApiOperation(value = "当前用户根据项目id获取报名申请")
    @GetMapping("/apply/info")
    @LogAnnotation(module = "前台用户管理接口", operator = "当前用户根据项目id获取报名申请")
    public Result selectApplyByProjectId(@RequestParam(value = "id") Long projectId) {
        Talent talent = TalentThreadLocal.get();
        Apply apply = iApplyService.selectByProjectIdAndTalentId(projectId, talent.getTalentId());
        return Result.ok().put(apply);

    }

}
