package com.dabai.cms.service;

import com.dabai.cms.entity.BigEvent;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * @author
 * @create 2022-06-18 13:36
 */
public interface IBigEventService {

    List<BigEvent> findAllBigEvents();


    /**
     *  分页查询，可以按昵称进行模糊查询
     * @param pageNum       页码
     * @param pageSize      页面大小
     * @param search        模糊查询关键字
     * @param year          年份
     */
    PageInfo<BigEvent> findBigEventByPage(Integer pageNum, Integer pageSize, String search, String year);


    public int insertBigEvent(BigEvent bigEvent);

    public int updateBigEvent(BigEvent bigEvent);

    public int deleteBigEventById(Long id);

}
