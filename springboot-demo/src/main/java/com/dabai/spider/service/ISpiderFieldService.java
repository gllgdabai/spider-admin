package com.dabai.spider.service;

import com.dabai.spider.entity.SpiderField;

import java.util.List;

/**
 * @author
 * @create 2022-04-03
 */
public interface ISpiderFieldService {
    /**
     * 查询爬虫字段
     *
     * @param fieldId 爬虫字段ID
     * @return 爬虫字段
     */
    public SpiderField selectSpiderFieldById(Long fieldId);

    /**
     * 查询爬虫字段列表
     *
     * @param spiderField 爬虫字段
     * @return 爬虫字段集合
     */
    public List<SpiderField> selectSpiderFieldList(SpiderField spiderField);

    /**
     * 查询爬虫字段列表
     *
     * @param configId 爬虫配置Id
     * @return 爬虫字段集合
     */
    public List<SpiderField> selectSpiderFieldListByConfigId(Long configId);

    /**
     * 新增爬虫字段
     *
     * @param spiderField 爬虫字段
     * @return 结果
     */
    public int insertSpiderField(SpiderField spiderField);

    /**
     * 修改爬虫字段
     *
     * @param spiderField 爬虫字段
     * @return 结果
     */
    public int updateSpiderField(SpiderField spiderField);

    /**
     * 批量删除爬虫字段
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteSpiderFieldByIds(String ids);

    /**
     * 删除爬虫字段信息
     *
     * @param fieldId 爬虫字段ID
     * @return 结果
     */
    public int deleteSpiderFieldById(Long fieldId);
}
