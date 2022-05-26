package com.dabai.spider.es.dao;

import com.dabai.spider.es.entity.ArticleEntity;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

/**
 * @author
 * @create 2022-05-21 10:16
 */
@Repository("spiderArticleRepository")
public interface ArticleRepository extends ElasticsearchRepository<ArticleEntity, String> {

}
