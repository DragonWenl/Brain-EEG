package com.brain.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.xml.crypto.Data;
import java.util.Date;

/**
 * @author liwenlong
 * @data 2020/8/3
 */
public class ExperimentPre {
    private Integer id;
    @JsonFormat(pattern = "yyyy-MM-dd  HH:mm:ss")
    private Date startTime;

    @JsonFormat(pattern = "yyyy-MM-dd  HH:mm:ss")
    private Date endTime;
    private int subjects;
    private int already;
    private int money;
    private int experience;
    private String content;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public int getSubjects() {
        return subjects;
    }

    public void setSubjects(int subjects) {
        this.subjects = subjects;
    }

    public int getAlready() {
        return already;
    }

    public void setAlready(int already) {
        this.already = already;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public int getExperience() {
        return experience;
    }

    public void setExperience(int experience) {
        this.experience = experience;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

}
