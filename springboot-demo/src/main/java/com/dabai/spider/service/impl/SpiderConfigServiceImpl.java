package com.dabai.spider.service.impl;

import cn.hutool.core.convert.Convert;
import com.dabai.spider.entity.SpiderConfig;
import com.dabai.spider.mapper.SpiderConfigMapper;
import com.dabai.spider.service.ISpiderConfigService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author
 * @create 2022-04-03
 */
@Service
public class SpiderConfigServiceImpl implements ISpiderConfigService {

    @Autowired
    private SpiderConfigMapper spiderConfigMapper;

    @Override
    public SpiderConfig selectSpiderConfigById(Long id) {
        return spiderConfigMapper.selectSpiderConfigById(id);
    }

    @Override
    public SpiderConfig selectSpiderConfigByCode(String code) {
        return spiderConfigMapper.selectSpiderConfigByCode(code);
    }

    @Override
    public List<SpiderConfig> selectSpiderConfigList(SpiderConfig spiderConfig) {
        return spiderConfigMapper.selectSpiderConfigList(spiderConfig);
    }

    @Override
    public PageInfo<SpiderConfig> selectSpiderConfigListByPage(Integer pageNum, Integer pageSize, String searchByName, String searchCode) {
        PageHelper.startPage(pageNum, pageSize);
        List<SpiderConfig> spiderMissions = spiderConfigMapper.selectSpiderConfigListBySearch(searchByName, searchCode);
        PageInfo<SpiderConfig> pageInfo = new PageInfo<>(spiderMissions);
        return pageInfo;
    }

    @Override
    public int insertSpiderConfig(SpiderConfig spiderConfig) {
        return spiderConfigMapper.insertSpiderConfig(spiderConfig);
    }

    @Override
    public int updateSpiderConfig(SpiderConfig spiderConfig) {
        return spiderConfigMapper.updateSpiderConfig(spiderConfig);
    }

    @Override
    public int deleteSpiderConfigByIds(String ids) {
        return spiderConfigMapper.deleteSpiderConfigByIds(Convert.toStrArray(ids));
    }

    @Override
    public List<SpiderConfig> selectSpiderConfigListByName(String name) {
        return spiderConfigMapper.selectSpiderConfigListBySearch(name, null);
    }

    @Override
    public int deleteSpiderConfigById(Long id) {
        return spiderConfigMapper.deleteSpiderConfigById(id);
    }
}
