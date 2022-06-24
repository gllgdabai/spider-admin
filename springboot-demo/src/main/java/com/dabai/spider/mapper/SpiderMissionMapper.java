package com.dabai.spider.mapper;

import com.dabai.spider.entity.SpiderMission;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author
 * @create 2022-04-03
 */
@Mapper
public interface SpiderMissionMapper {
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
     * @return 爬虫任务集合
     */
    public List<SpiderMission> selectSpiderMissionList();

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
     * 删除爬虫任务
     *
     * @param missionId 爬虫任务ID
     * @return 结果
     */
    public int deleteSpiderMissionById(Long missionId);

    /**
     * 批量删除爬虫任务
     *
     * @param missionIds 需要删除的数据ID
     * @return 结果
     */
    public int deleteSpiderMissionByIds(String[] missionIds);

    List<SpiderMission> selectSpiderMissionListBySearch(String search);
}
