package com.dabai.spider.service;

import com.dabai.spider.entity.SpiderMission;
import com.github.pagehelper.PageInfo;
import com.ruoyi.common.core.domain.AjaxResult;

import java.util.List;

/**
 * @author
 * @create 2022-04-03
 */
public interface ISpiderMissionService {
    /**
     * 查询爬虫任务
     *
     * @param missionId 爬虫任务ID
     * @return 爬虫任务
     */
    public SpiderMission selectSpiderMissionById(Long missionId);

    /**
     * 查询爬虫任务列表
     *
     * @param spiderMission 爬虫任务
     * @return 爬虫任务集合
     */
    public List<SpiderMission> selectSpiderMissionList(SpiderMission spiderMission);

    /**
     * （分页）查询爬虫任务列表
     * @param pageNum   当前页码
     * @param pageSize  页码大小
     * @param search    用于模糊搜索
     * @return 爬虫任务集合
     */
    public PageInfo<SpiderMission> selectSpiderMissionListByPage(Integer pageNum, Integer pageSize, String search);

    /**
     * 新增爬虫任务
     *
     * @param spiderMission 爬虫任务
     * @return 结果
     */
    public int insertSpiderMission(SpiderMission spiderMission);

    /**
     * 修改爬虫任务
     *
     * @param spiderMission 爬虫任务
     * @return 结果
     */
    public int updateSpiderMission(SpiderMission spiderMission);

    /**
     * 删除爬虫任务信息
     *
     * @param missionId 爬虫任务ID
     * @return 结果
     */
    public int deleteSpiderMissionById(Long missionId);

    /**
     * 批量删除爬虫任务
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteSpiderMissionByIds(String ids);

    /**
     * 运行爬虫任务
     * @param missionId
     */
    public AjaxResult runSpiderMission(String missionId);
}
