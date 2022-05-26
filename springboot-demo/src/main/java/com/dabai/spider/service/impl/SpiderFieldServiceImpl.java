package com.dabai.spider.service.impl;

import cn.hutool.core.convert.Convert;
import com.dabai.spider.entity.SpiderField;
import com.dabai.spider.mapper.SpiderFieldMapper;
import com.dabai.spider.service.ISpiderFieldService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author
 * @create 2022-04-03
 */
@Service
public class SpiderFieldServiceImpl implements ISpiderFieldService {

    @Autowired
    private SpiderFieldMapper spiderFieldMapper;

    @Override
    public SpiderField selectSpiderFieldById(Long fieldId) {
        return spiderFieldMapper.selectSpiderFieldById(fieldId);
    }

    @Override
    public List<SpiderField> selectSpiderFieldList(SpiderField spiderField) {
        return spiderFieldMapper.selectSpiderFieldList(spiderField);
    }

    @Override
    public List<SpiderField> selectSpiderFieldListByConfigId(Long configId) {
        return spiderFieldMapper.selectSpiderFieldListByConfigId(configId);
    }

    @Override
    public int insertSpiderField(SpiderField spiderField) {
        return spiderFieldMapper.insertSpiderField(spiderField);
    }

    @Override
    public int updateSpiderField(SpiderField spiderField) {
        return spiderFieldMapper.updateSpiderField(spiderField);
    }

    @Override
    public int deleteSpiderFieldByIds(String ids) {
        return spiderFieldMapper.deleteSpiderFieldByIds(Convert.toStrArray(ids));
    }

    @Override
    public int deleteSpiderFieldById(Long fieldId) {
        return spiderFieldMapper.deleteSpiderFieldById(fieldId);
    }
}
