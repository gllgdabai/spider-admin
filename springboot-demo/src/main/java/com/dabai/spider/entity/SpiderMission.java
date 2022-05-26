package com.dabai.spider.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.core.domain.BaseEntity;
import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Date;

/**
 * 爬虫任务对象 spider_mission
 * @author
 * @create 2022-04-03
 */
@Data
public class SpiderMission extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /** 爬虫任务ID */
    private Long missionId;

    /** 任务名称 */
    private String missionName;

    /** 爬虫配置ID */
    private Long spiderConfigId;
    /** 爬虫配置ID + 配置名称 */
    private String spiderConfigIdName;

    /** 入口地址 */
    private String entryUrls;

    /** 任务状态 */
    private String status;

    /** 开始时间 */
    private Date startTime;

    /** 结束时间 */
    private Date endTime;

    /** 爬取时间(单位秒) */
    private String timeCost;

    /** 退出方式。DEFAULT，DURATION，URL_COUNT。 */
    private String exitWay;

    /** 退出方式值 */
    private Long exitWayCount;

    /** 爬取数量 */
    private Long successNum;

    /** 部门ID */
    private String deptId;

    /** 用户ID */
    private String userId;

    /** cookieStr */
    private String cookieStr;

    /** headerStr */
    private String headerStr;


    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("missionId", getMissionId())
                .append("missionName", getMissionName())
                .append("spiderConfigId", getSpiderConfigId())
                .append("spiderConfigIdName", getSpiderConfigIdName())
                .append("entryUrls", getEntryUrls())
                .append("status", getStatus())
                .append("startTime", getStartTime())
                .append("endTime", getEndTime())
                .append("timeCost", getTimeCost())
                .append("exitWay", getExitWay())
                .append("exitWayCount", getExitWayCount())
                .append("successNum", getSuccessNum())
                .append("deptId", getDeptId())
                .append("userId", getUserId())
                .append("createBy", getCreateBy())
                .append("createTime", getCreateTime())
                .toString();
    }
}
