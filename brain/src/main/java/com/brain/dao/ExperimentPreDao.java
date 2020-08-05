package com.brain.dao;

import com.brain.base.result.Results;
import com.brain.model.ExperimentPre;
import com.brain.model.ExperimentSub;
import com.sun.jmx.snmp.SnmpInt;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author liwenlong
 * @data 2020/8/3
 */
@Mapper
public interface ExperimentPreDao {

    @Select("select count(*) from experiment_pre")
    Long countAllExperimentPre();

    @Select("select * from experiment_pre limit #{startPosition}, #{limit}")
    List<ExperimentPre> getAllexperimentPreByPage(@Param("startPosition")Integer startPosition, @Param("limit")Integer limit);

    @Select("select count(*) from experiment_sub")
    Long countAllExperimentSub();

    @Select("select * from experiment_sub limit #{startPosition}, #{limit}")
    List<ExperimentSub> getAllexperimentSubByPage(@Param("startPosition")Integer startPosition, @Param("limit")Integer limit);

    @Select("select * from experiment_pre where id = #{id}")
    ExperimentPre getExperimentById(int id);

    @Insert("insert into experiment_pre (id,startTime,endTime,subjects,money,experience,content) " +
            "values(#{id},#{startTime},#{endTime},#{subjects},#{money},#{experience},#{content}")
    int save(ExperimentPre experimentPre);

    int updateExperimentPre(ExperimentPre experimentPre);
}
