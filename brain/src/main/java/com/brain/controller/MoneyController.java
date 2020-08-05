package com.brain.controller;

import com.brain.base.result.PageTableRequest;
import com.brain.base.result.ResponseCode;
import com.brain.base.result.Results;
import com.brain.model.Money;
import com.brain.model.SysUser;
import com.brain.service.MoneyService;
import com.brain.service.UserService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author liwenlong
 * @data 2020/8/2
 */
@Controller
@RequestMapping("money")
@Slf4j
public class MoneyController {
    @Autowired
    private MoneyService moneyService;

    @GetMapping("/list")
    @ResponseBody
    @PreAuthorize("hasAuthority('money:query')")
    @ApiOperation(value = "获取酬金列表", notes = "获取所有的酬金列表")//描述
    public Results<Money> getUsers(PageTableRequest pageTableRequest) {
        pageTableRequest.countOffset();
        return moneyService.getMoneyByPageRequest(pageTableRequest.getOffset(), pageTableRequest.getLimit());
    }

    //格式转换
    String pattern = "yy-MM-dd";

    @InitBinder
    public void initBinder(WebDataBinder binder, WebRequest request) {
        binder.registerCustomEditor(Date.class, new CustomDateEditor(new SimpleDateFormat(pattern), true));
    }

    //跳转到增加用户的页面
    @GetMapping("/add")
    @ApiOperation(value = "酬金新增页面", notes = "跳转到新增酬金信息页面")//描述
    @PreAuthorize("hasAnyAuthority('money:add')")
    public String addMoney(Model model) {
        model.addAttribute(new Money());
        return "money/money-add";
    }

    //增加用户
    @PostMapping("/add")
    @ResponseBody
    @PreAuthorize("hasAnyAuthority('money:add')")
    @ApiOperation(value = "保存酬金信息", notes = "保存新增的酬金信息")//描述
    public Results saveMoney(Money money) {
        Money moneyhas;
        moneyhas = moneyService.getMoneyById(money.getId());
        if (moneyhas != null) {
            return Results.failure(ResponseCode.ID_REPEAT.getCode(),ResponseCode.ID_REPEAT.getMessage());
        }
        return moneyService.saveMoney(money);
    }

}
