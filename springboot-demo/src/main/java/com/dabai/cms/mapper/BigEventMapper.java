package com.dabai.cms.mapper;

import com.dabai.cms.entity.BigEvent;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author
 * @create 2022-06-18 13:22
 */
@Mapper
public interface BigEventMapper {

    List<BigEvent> selectBigEvents();

    List<BigEvent> selectBigEventsBySearch(String search, String year);

    int insertOne(BigEvent bigEvent);

    int updateById(BigEvent bigEvent);

    int deleteById(Long id);

}
