package com.dabai.spider.mapper;

import com.dabai.spider.entity.SpiderConfig;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author
 * @create 2022-04-03
 */
@Mapper
public interface SpiderConfigMapper {
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
     * 查询爬虫配置
     *
     * @param name 爬虫配置编码
     * @return 爬虫配置
     */
    public SpiderConfig selectSpiderConfigByName(String name);

    /**
     * 查询爬虫配置列表
     *
     * @param spiderConfig 爬虫配置
     * @return 爬虫配置集合
     */
    public List<SpiderConfig> selectSpiderConfigList(SpiderConfig spiderConfig);

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
     * 删除爬虫配置
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
    public int deleteSpiderConfigByIds(String[] ids);


    List<SpiderConfig> selectSpiderConfigListBySearch(String searchByName, String searchByCode);
}
