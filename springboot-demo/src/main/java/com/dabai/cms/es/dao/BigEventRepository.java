package com.dabai.cms.es.dao;

import com.dabai.cms.es.entity.BigEventEntity;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author
 * @create 2022-06-19 12:09
 */
@Repository("cmsBigEventRepository")
public interface BigEventRepository extends ElasticsearchRepository<BigEventEntity, String> {

    int deleteBigEventEntityById(Long id);


    List<BigEventEntity> findByYearAndEventLike(String year, String event);

    List<BigEventEntity> findByEventLike(String event);

}
