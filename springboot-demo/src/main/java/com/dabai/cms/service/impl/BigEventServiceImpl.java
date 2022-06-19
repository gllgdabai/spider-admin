package com.dabai.cms.service.impl;

import com.dabai.cms.entity.BigEvent;
import com.dabai.cms.es.service.BigEventService;
import com.dabai.cms.mapper.BigEventMapper;
import com.dabai.cms.service.IBigEventService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author
 * @create 2022-06-18 13:38
 */
@Service
public class BigEventServiceImpl implements IBigEventService {

    @Autowired
    private BigEventMapper bigEventMapper;

    @Autowired
    @Qualifier("cmsBigEventService")
    private BigEventService bigEventServiceES;


    @Override
    public List<BigEvent> findAllBigEvents() {
        return bigEventMapper.selectBigEvents();
    }

    @Override
    public PageInfo<BigEvent> findBigEventByPage(Integer pageNum, Integer pageSize, String search, String year) {
        PageHelper.startPage(pageNum, pageSize);
        List<BigEvent> events = bigEventMapper.selectBigEventsBySearch(search, year);
        PageInfo<BigEvent> pageInfo = new PageInfo<>(events);
        return pageInfo;
    }

    @Override
    public int insertBigEvent(BigEvent bigEvent) {
        return bigEventMapper.insertOne(bigEvent);
    }

    @Override
    public int updateBigEvent(BigEvent bigEvent) {
        return bigEventMapper.updateById(bigEvent);
    }

    @Override
    public int deleteBigEventById(Long id) {
        return bigEventMapper.deleteById(id);
    }
}
