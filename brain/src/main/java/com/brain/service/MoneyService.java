package com.brain.service;

import com.brain.base.result.Results;
import com.brain.dao.MoneyDao;
import com.brain.model.Money;
import com.brain.model.SysUser;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author liwenlong
 * @data 2020/8/2
 */
@Service
@Transactional
public class MoneyService {
    @Autowired
    MoneyDao moneyDao;

    public Results<Money> getMoneyByPageRequest(Integer offset, Integer limit) {
        return Results.success(moneyDao.countAllMoneys().intValue(), moneyDao.getAllMoneysByPage(offset, limit));
    }

    public Money getMoneyById(Long id) {
        return moneyDao.getMoneyById(id);
    }

    public Results saveMoney(Money money) {
        moneyDao.save(money);
        return Results.success();
    }
}
