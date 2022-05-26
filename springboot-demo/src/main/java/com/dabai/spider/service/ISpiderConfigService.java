package com.dabai.spider.service;

import com.dabai.spider.entity.SpiderConfig;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * @author
 * @create 2022-04-03
 */
public interface ISpiderConfigService {
    /**
     * 查询爬虫配置
     *
     * @param id 爬虫配置ID
     * @return 爬虫配置
     */
    public SpiderConfig selectSpiderConfigById(Long id);
    /**
     * 查询爬虫配置
     *
     * @param code 爬虫配置编码
     * @return 爬虫配置
     */
    public SpiderConfig selectSpiderConfigByCode(String code);
    /**
     * 查询爬虫配置列表
     *
     * @param spiderConfig 爬虫配置
     * @return 爬虫配置集合
     */
    public List<SpiderConfig> selectSpiderConfigList(SpiderConfig spiderConfig);

    /**
     * (分页)查询爬虫配置列表
     * @param pageNum   当前页码
     * @param pageSize  页码大小
     * @param searchByName  按爬虫名称模糊搜索
     * @param searchByCode  按爬虫编码模糊搜索
     * @return 爬虫配置集合
     */
    PageInfo<SpiderConfig> selectSpiderConfigListByPage(Integer pageNum, Integer pageSize, String searchByName, String searchByCode);

    /**
     * 新增爬虫配置
     *
     * @param spiderConfig 爬虫配置
     * @return 结果
     */
    public int insertSpiderConfig(SpiderConfig spiderConfig);

    /**
     * 修改爬虫配置
     *
     * @param spiderConfig 爬虫配置
     * @return 结果
     */
    public int updateSpiderConfig(SpiderConfig spiderConfig);

    /**
     * 删除爬虫配置信息
     *
     * @param id 爬虫配置ID
     * @return 结果
     */
    public int deleteSpiderConfigById(Long id);

    /**
     * 批量删除爬虫配置
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteSpiderConfigByIds(String ids);

    public List<SpiderConfig> selectSpiderConfigListByName(String name);

}
