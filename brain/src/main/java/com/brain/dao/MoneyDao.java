package com.brain.dao;

import com.brain.base.result.Results;
import com.brain.model.Money;
import com.brain.model.Role;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author liwenlong
 * @data 2020/8/2
 */
@Mapper
public interface MoneyDao {
    @Select("select count(*) from money")
    Long countAllMoneys();

    @Select("select * from money")
    List<Money> getAllMoneys();

    @Select("select * from money t limit #{startPosition}, #{limit}")
    List<Money> getAllMoneysByPage(@Param("startPosition")Integer startPosition, @Param("limit")Integer limit);

    @Select("select * from money t where t.id = #{id}")
    Money getMoneyById(Long id);

    @Insert("insert into money(id,subject,card,money,status,paytype,expTime,payTime) " +
            "values(#{id},#{subject},#{card},#{money},#{status},#{paytype},#{expTime},#{payTime}")
    int save(Money money);
}
