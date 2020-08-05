package com.brain.controller;

import com.brain.base.result.PageTableRequest;
import com.brain.base.result.ResponseCode;
import com.brain.base.result.Results;
import com.brain.model.*;
import com.brain.service.ExperimentPreService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author liwenlong
 * @data 2020/8/3
 */
@Controller
@RequestMapping("experimentPre")
public class ExperimentPreController {
    @Autowired
    private ExperimentPreService experimentPreService;

    @GetMapping("/list")
    @ResponseBody
    @PreAuthorize("hasAuthority('exp:pre:query')")
    @ApiOperation(value = "获取发布的预约实验", notes = "获取所有可以进行预约的实验")//描述
    public Results list(PageTableRequest request) {
        request.countOffset();
        return experimentPreService.getExpersByPageRequest(request.getOffset(), request.getLimit());
    }

    @GetMapping("/getSubjects")
    @ResponseBody
    @ApiOperation(value = "获取预约成功的信息", notes = "获取用户预约成功的实验信息")//描述
    public Results getSubjects(PageTableRequest request) {
        request.countOffset();
        return experimentPreService.getSubsByPageRequest(request.getOffset(), request.getLimit());
    }

    //跳转到增加用户的页面
    @GetMapping("/add")
    @ApiOperation(value = "预约实验新增页面", notes = "跳转到新增预约实验页面")//描述
    @PreAuthorize("hasAnyAuthority('exp:pre:add')")
    public String addExperimentPre(Model model) {
        model.addAttribute(new ExperimentPre());
        return "experiment/experimentPre-add";
    }

    //增加用户
    @PostMapping("/add")
    @ResponseBody
    @PreAuthorize("hasAnyAuthority('exp:pre:add')")
    @ApiOperation(value = "保存预约实验信息", notes = "保存新增的预约实验信息")//描述
    public Results saveMoney(ExperimentPre experimentPre) {
        ExperimentPre hasExperimentPre;
        hasExperimentPre = experimentPreService.getExperimentById(experimentPre.getId());
        if (hasExperimentPre != null) {
            return Results.failure(ResponseCode.ID_REPEAT.getCode(),ResponseCode.ID_REPEAT.getMessage());
        }
        experimentPre.setAlready(0);
        return experimentPreService.saveExp(experimentPre);
    }

    //格式转换
    String pattern = "yy-MM-dd";

    @InitBinder
    public void initBinder(WebDataBinder binder, WebRequest request) {
        binder.registerCustomEditor(Date.class, new CustomDateEditor(new SimpleDateFormat(pattern), true));
    }

    @GetMapping("/edit")
    @ApiOperation(value = "实验预约编辑页面", notes = "跳转到对已经发布的预约实验编辑页面")//描述
    @PreAuthorize("hasAnyAuthority('exp:pre:edit')")
    public String editExperimentPre(Model model, ExperimentPre experimentPre) {
        model.addAttribute(experimentPreService.getExpPreById(experimentPre.getId()));
        return "experiment/experimentPre-edit";
    }

    @PostMapping("/edit")
    @ResponseBody
    @ApiOperation(value = "保存预约实验信息", notes = "保存编辑完的预约实验信息")//描述
    @PreAuthorize("hasAuthority('exp:pre:edit')")
    public Results<ExperimentPre> updateExperimentPre(ExperimentPre experimentPre) {
        return experimentPreService.updateExperimentPre(experimentPre);
    }
}
