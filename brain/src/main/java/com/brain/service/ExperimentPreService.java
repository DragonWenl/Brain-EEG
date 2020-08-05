package com.brain.service;

import com.brain.base.result.Results;
import com.brain.dao.ExperimentPreDao;
import com.brain.dao.MoneyDao;
import com.brain.model.ExperimentPre;
import com.brain.model.ExperimentSub;
import com.brain.model.Money;
import com.brain.model.SysUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author liwenlong
 * @data 2020/8/3
 */
@Service
@Transactional
public class ExperimentPreService {
    @Autowired
    ExperimentPreDao experimentPreDao;

    public Results<ExperimentPre> getExpersByPageRequest(Integer offset, Integer limit) {
        int count = experimentPreDao.countAllExperimentPre().intValue();
        List<ExperimentPre> experimentPres = experimentPreDao.getAllexperimentPreByPage(offset, limit);
        return Results.success(count,experimentPres);
    }

    public Results<ExperimentSub> getSubsByPageRequest(Integer offset, Integer limit) {
        int count = experimentPreDao.countAllExperimentSub().intValue();
        List<ExperimentSub> experimentSubs = experimentPreDao.getAllexperimentSubByPage(offset, limit);
        return Results.success(count,experimentSubs);
    }

    public ExperimentPre getExperimentById(int id) {
        return experimentPreDao.getExperimentById(id);
    }

    public Results saveExp(ExperimentPre experimentPre) {
        experimentPreDao.save(experimentPre);
        return Results.success();
    }

    public ExperimentPre getExpPreById(Integer id) {
        return experimentPreDao.getExperimentById(id);
    }

    public Results updateExperimentPre(ExperimentPre experimentPre) {
        experimentPreDao.updateExperimentPre(experimentPre);
        return Results.success();
    }
}
